package experiment

import experiment.entities.seed.seedPosts
import experiment.entities.seed.seedUsers
import experiment.handlers.AuthHandler
import experiment.handlers.ViewsHandler
import experiment.repositories.PostsRepository
import experiment.repositories.UsersRepository
import experiment.utils.JWTManager
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.coAwait
import io.vertx.kotlin.coroutines.coroutineRouter
import org.ktorm.database.Database

class MainVerticle : CoroutineVerticle() {
    private val database =
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/experiment",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )

    private fun seedDb() {
        seedUsers(database)
        seedPosts(database)
    }

    private fun routes(): Router {
        val usersRepository = UsersRepository(database)
        val postsRepository = PostsRepository(database)
        val viewsHandler = ViewsHandler(usersRepository,postsRepository)
        val authHandler = AuthHandler(usersRepository)
        val router = Router.router(vertx)

        coroutineRouter {
            router.route().coHandler(requestHandler = authHandler::middleware)
        }

        router.get("/").handler(viewsHandler::index)
        router.get("/login").handler(viewsHandler::login)
        router.get("/register").handler(viewsHandler::register)
        router.post("/auth/signin").handler(authHandler::signIn)
        router.post("/auth/signup").handler(authHandler::signUp)
        router.route("/static/*").handler(StaticHandler.create())

        return router
    }

    override suspend fun start() {
        JWTManager.init(vertx)

        vertx.createHttpServer()
            .requestHandler(routes())
            .listen(3000)
            .onComplete {
                println("HTTP server started on port ${it.result().actualPort()}")
            }.coAwait()
    }
}
