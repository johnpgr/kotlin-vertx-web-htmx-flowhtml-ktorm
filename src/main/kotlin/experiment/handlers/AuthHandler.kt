package experiment.handlers

import experiment.entities.User
import experiment.repositories.UsersRepository
import experiment.utils.JWTManager
import experiment.utils.hxRedirect
import io.vertx.core.http.Cookie
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import org.mindrot.jbcrypt.BCrypt
import java.util.*

class AuthHandler(private val usersRepository: UsersRepository) {
    fun signIn(ctx: RoutingContext) {
        ctx.request()
            .setExpectMultipart(true)
            .endHandler {
                val username = ctx.request().getFormAttribute("username")
                val password = ctx.request().getFormAttribute("password")

                if (username == null || password == null) {
                    ctx.response().setStatusCode(400).end("Missing username or password")
                    return@endHandler
                }

                val user = usersRepository.findByUsername(username)

                if (user == null || !BCrypt.checkpw(password, user.hashedPassword)) {
                    ctx.response().setStatusCode(401).end("Invalid username or password")
                    return@endHandler
                }

                val token = JWTManager.generateToken(user.id.toString())
                val cookie =
                    Cookie.cookie("auth_token", token).setPath("/").setMaxAge(3600).setSecure(true).setHttpOnly(true)

                ctx.response().addCookie(cookie).hxRedirect("/")
            }
    }

    fun signUp(ctx: RoutingContext) {
        ctx.request()
            .setExpectMultipart(true)
            .endHandler {
                val userName = ctx.request().getFormAttribute("username")
                val password = ctx.request().getFormAttribute("password")

                if (userName == null || password == null) {
                    ctx.response().setStatusCode(400).end("Missing required fields")
                    return@endHandler
                }

                val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

                try {
                    val user = User {
                        this.id = UUID.randomUUID()
                        this.userName = userName
                        this.hashedPassword = hashedPassword
                    }
                    usersRepository.add(user)
                    val token = JWTManager.generateToken(user.id.toString())
                    val cookie =
                        Cookie.cookie("auth_token", token).setPath("/").setMaxAge(3600).setSecure(true)
                            .setHttpOnly(true)
                    ctx.response().addCookie(cookie).hxRedirect("/")
                    return@endHandler
                } catch (e: Exception) {
                    e.printStackTrace()
                    ctx.response().setStatusCode(500).end("Failed to create user.")
                    return@endHandler
                }
            }
    }

    suspend fun middleware(ctx: RoutingContext) {
        val cookie = ctx.request().getCookie("auth_token") ?: return ctx.next()
        val userId = JWTManager.verifyToken(cookie.value) ?: return ctx.next()
        ctx.setUser(io.vertx.ext.auth.User.create(JsonObject().put("id", userId)))
        ctx.next()
    }
}
