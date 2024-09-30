package experiment

import htmlflow.*
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find

class MainVerticle : CoroutineVerticle() {
    override suspend fun start() {
        val database = Database.connect(
            url = "jdbc:postgresql://localhost:5432/experiment",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )
        val router = Router.router(vertx)

        val testView = view<Unit> { div { text("Hello, test_element!") } }
        val homePage =
            view<User?> {
                baseLayout {
                    dyn { user: User? ->
                        pre {
                            text(user.toString())
                        }
                    }
                }
            }
        val userTest = database.users.find { it.userName eq "TestUser" }

        router.get("/").respond {
            it.response().putHeader("Content-Type", "text/html").renderView(homePage, userTest)
        }

        router.get("/test-htmx").respond {
            it.response().putHeader("Content-Type", "text/html").renderView(testView)
        }

        router.route().handler {
            it.response()
                .setStatusCode(404)
                .putHeader("Content-Type", "text/html")
                .end("<html><body>Not Found</body></html>")
        }

        vertx.createHttpServer().requestHandler(router).listen(3000).onComplete { http ->
            if (http.succeeded()) {
                println("HTTP server started on port 3000")
            } else {
                println("HTTP server failed to start")
            }
        }
    }
}
