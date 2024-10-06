package experiment

import experiment.handlers.AuthHandler
import experiment.handlers.ViewsHandler
import experiment.repositories.PostsRepository
import experiment.repositories.SessionsRepository
import experiment.repositories.UsersRepository
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.HOST
import io.r2dbc.spi.ConnectionFactoryOptions.DRIVER
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.DATABASE
import io.r2dbc.spi.ConnectionFactoryOptions.PORT
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import io.vertx.kotlin.coroutines.coroutineRouter
import org.komapper.dialect.postgresql.r2dbc.PostgreSqlR2dbcDialect
import org.komapper.r2dbc.R2dbcDatabase

class MainVerticle : CoroutineVerticle() {
  private val connectionFactory = ConnectionFactories.get(
    ConnectionFactoryOptions
      .builder()
      .option(DRIVER, "postgresql")
      .option(USER, "postgres")
      .option(PASSWORD, "postgres")
      .option(HOST, "localhost")
      .option(PORT, 5432)
      .option(DATABASE, "experiment")
      .build()
  )
  private val database = R2dbcDatabase(
    connectionFactory = connectionFactory,
    dialect = PostgreSqlR2dbcDialect(),
  )

  private fun routes(): Router {
    val usersRepository = UsersRepository(database)
    val sessionsRepository = SessionsRepository(database)
    val postsRepository = PostsRepository(database)
    val viewsHandler = ViewsHandler(usersRepository, postsRepository)
    val authHandler = AuthHandler(usersRepository, sessionsRepository)
    val router = Router.router(vertx)

    router.route("/static/*").handler(StaticHandler.create())
    router.route().handler(BodyHandler.create())

    coroutineRouter {
      router.route().coHandler(requestHandler = authHandler::middleware)
      router.get("/").coHandler(requestHandler = viewsHandler::index)
      router.get("/login").coHandler(requestHandler = viewsHandler::login)
      router.get("/register").coHandler(requestHandler = viewsHandler::register)
      router.post("/auth/signin").coHandler(requestHandler = authHandler::signIn)
      router.post("/auth/signup").coHandler(requestHandler = authHandler::signUp)
      router.post("/auth/signout").coHandler(requestHandler = authHandler::signOut)
    }

    return router
  }

  override suspend fun start() {
    vertx.createHttpServer().requestHandler(routes()).listen(3000).onComplete {
      println("HTTP server started on port ${it.result().actualPort()}")
    }.coAwait()
  }
}
