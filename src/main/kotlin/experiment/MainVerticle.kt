package experiment

import experiment.datasources.PostgresDataSource
import experiment.handlers.AuthHandler
import experiment.handlers.ViewsHandler
import experiment.repositories.PostsRepository
import experiment.repositories.SessionsRepository
import experiment.repositories.UsersRepository
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import org.ktorm.database.Database

class MainVerticle : CoroutineVerticle() {
    private val database =
        Database.connect(PostgresDataSource())

    private fun routes(): Router {
        val usersRepository = UsersRepository(database)
        val sessionsRepository = SessionsRepository(database)
        val postsRepository = PostsRepository(database)
        val viewsHandler = ViewsHandler(usersRepository,postsRepository)
        val authHandler = AuthHandler(usersRepository, sessionsRepository)
        val router = Router.router(vertx)

        router.route().handler(authHandler::middleware)
        router.get("/").handler(viewsHandler::index)
        router.get("/login").handler(viewsHandler::login)
        router.get("/register").handler(viewsHandler::register)
        router.post("/auth/signin").handler(authHandler::signIn)
        router.post("/auth/signup").handler(authHandler::signUp)
        router.post("/auth/signout").handler(authHandler::signOut)
        router.route("/static/*").handler(StaticHandler.create())

        return router
    }

    override suspend fun start() {
        //seedPosts(database)

        vertx.createHttpServer()
            .requestHandler(routes())
            .listen(3000)
            .onComplete {
                println("HTTP server started on port ${it.result().actualPort()}")
            }.coAwait()
    }
}
