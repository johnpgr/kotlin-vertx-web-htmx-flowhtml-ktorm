package experiment.handlers

import experiment.entities.User
import experiment.repositories.SessionsRepository
import experiment.repositories.UsersRepository
import experiment.utils.ContentType
import experiment.utils.hxRedirect
import experiment.utils.redirect
import experiment.utils.setContentType
import io.vertx.core.http.Cookie
import io.vertx.ext.web.RoutingContext
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.util.*
import kotlin.time.Duration.Companion.days

class AuthHandler(
    private val usersRepository: UsersRepository,
    private val sessionsRepository: SessionsRepository
) {
    fun signIn(ctx: RoutingContext) {
        ctx.request()
            .setExpectMultipart(true)
            .endHandler {
                val username = ctx.request().getFormAttribute("username")
                val password = ctx.request().getFormAttribute("password")

                if (username == null || password == null) {
                    ctx.response().setStatusCode(400)
                        .end("Missing username or password")
                    return@endHandler
                }

                val user = usersRepository.findByUsername(username)

                if (user == null || !BCrypt.checkpw(
                        password,
                        user.hashedPassword
                    )
                ) {
                    ctx.response()
                        .setContentType(ContentType.TEXT_PLAIN)
                        .setStatusCode(401)
                        .end("Invalid username or password")
                    return@endHandler
                }
                val session = sessionsRepository.createSession(user.id)
                val cookie =
                    Cookie.cookie("session_token", session.token)
                        .setPath("/")
                        .setMaxAge(7.days.inWholeSeconds)
                        .setSecure(true)
                        .setHttpOnly(true)

                ctx.response().addCookie(cookie).redirect("/")
            }
    }

    fun signUp(ctx: RoutingContext) {
        ctx.request()
            .setExpectMultipart(true)
            .endHandler {
                val userName = ctx.request().getFormAttribute("username")
                val password = ctx.request().getFormAttribute("password")

                if (userName == null || password == null) {
                    ctx.response().setStatusCode(400)
                        .end("Missing required fields")
                    return@endHandler
                }
                val existingUser = usersRepository.findByUsername(userName)
                if (existingUser != null) {
                    ctx.response()
                        .setContentType(ContentType.TEXT_PLAIN)
                        .setStatusCode(409).end("User already exists")
                    return@endHandler
                }
                val user = User {
                    this.id = UUID.randomUUID()
                    this.userName = userName
                    this.hashedPassword =
                        BCrypt.hashpw(password, BCrypt.gensalt())
                }
                usersRepository.add(user)
                val session = sessionsRepository.createSession(user.id)
                val cookie =
                    Cookie.cookie("session_token", session.token)
                        .setPath("/")
                        .setMaxAge(3600)
                        .setSecure(true)
                        .setHttpOnly(true)
                ctx.response().addCookie(cookie).redirect("/")
                return@endHandler
            }
    }

    fun signOut(ctx: RoutingContext) {
        val response = ctx.response()
        val sessionToken = ctx.request().getCookie("session_token")?.value
        if (sessionToken != null) {
            sessionsRepository.deleteSession(sessionToken)
            response.removeCookie("session_token")
        }
        response.redirect("/")
    }

    fun middleware(ctx: RoutingContext) {
        val sessionToken = ctx.request().getCookie("session_token")?.value
        if (sessionToken == null) {
            ctx.next()
            return
        }
        val session = sessionsRepository.findByToken(sessionToken)
        if (session == null || session.expiresAt.isBefore(LocalDateTime.now())) {
            ctx.response().removeCookie("session_token")
            ctx.next()
            return
        }
        val user = usersRepository.findById(session.userId)
        if (user == null) {
            ctx.response().removeCookie("session_token")
            ctx.next()
            return
        }
        ctx.setUser(io.vertx.ext.auth.User.fromName(user.userName))
        ctx.next()
    }
}
