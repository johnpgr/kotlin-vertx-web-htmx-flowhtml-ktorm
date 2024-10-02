package experiment

import experiment.handlers.DefaultHandler
import experiment.handlers.IndexHandler
import experiment.handlers.TestHTMXHandler
import experiment.services.UserService
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.ktorm.database.Database

class MainVerticle : CoroutineVerticle() {
    private lateinit var router: Router
    private val database =
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/experiment",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )

    private fun initServices() {
        UserService.init(database)
    }

    private fun initRouter() {
        router = Router.router(vertx)
        router.get("/").handler(IndexHandler)
        router.get("/test-htmx").handler(TestHTMXHandler)
        router.get("/test").handler { ctx ->
            ctx.response().putHeader("content-type", "text/html").end("Hello, World")
        }
        router.route().handler(DefaultHandler)
    }

    override suspend fun start() {
        initServices()
        initRouter()

        vertx.createHttpServer().requestHandler(router).listen(3000).onComplete { http ->
            if (http.succeeded()) {
                println("HTTP server started on port 3000")
            } else {
                println("HTTP server failed to start")
            }
        }
    }
}
