package experiment.handlers

import experiment.entities.UserEntity
import experiment.repositories.SessionsRepository
import experiment.repositories.UsersRepository
import experiment.utils.ContentType
import experiment.utils.redirect
import experiment.utils.setContentType
import io.vertx.core.http.Cookie
import io.vertx.ext.auth.User
import io.vertx.ext.web.RoutingContext
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.util.*
import kotlin.time.Duration.Companion.days

class AuthHandler(
  private val usersRepository: UsersRepository,
  private val sessionsRepository: SessionsRepository
) {
  suspend fun signIn(ctx: RoutingContext) {
    val request = ctx.request()
    val response = ctx.response()

    val username = request.getFormAttribute("username")
    val password = request.getFormAttribute("password")

    if (username == null || password == null) {
      ctx.response().setStatusCode(400).end("Missing username or password")
      return
    }

    val user = usersRepository.findByUsername(username)

    if (user == null || !BCrypt.checkpw(password, user.hashedPassword)) {
      response.setContentType(ContentType.TEXT_PLAIN).setStatusCode(401)
        .end("Invalid username or password")
      return
    }
    val session = sessionsRepository.createSession(user.id)
    val cookie = Cookie.cookie("session_token", session.token).setPath("/")
      .setMaxAge(7.days.inWholeSeconds).setSecure(true).setHttpOnly(true)

    response.addCookie(cookie).redirect("/")
  }

  suspend fun signUp(ctx: RoutingContext) {
    val request = ctx.request()
    val response = ctx.response()
    val userName = request.getFormAttribute("username")
    val password = request.getFormAttribute("password")

    if (userName == null || password == null) {
      response.setStatusCode(400).end("Missing required fields")
      return
    }
    val existingUser = usersRepository.findByUsername(userName)
    if (existingUser != null) {
      response.setContentType(ContentType.TEXT_PLAIN).setStatusCode(409)
        .end("User already exists")
      return
    }
    val user = UserEntity(
      id = UUID.randomUUID(),
      name = userName,
      hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()),
      bio = null,
    )
    usersRepository.add(user)
    val session = sessionsRepository.createSession(user.id)
    val cookie =
      Cookie.cookie("session_token", session.token)
        .setPath("/")
        .setMaxAge(3600)
        .setSecure(true)
        .setHttpOnly(true)
    response.addCookie(cookie).redirect("/")
    return
  }

  suspend fun signOut(ctx: RoutingContext) {
    val request = ctx.request()
    val response = ctx.response()
    val sessionToken = request.getCookie("session_token")?.value
    if (sessionToken != null) {
      sessionsRepository.deleteSession(sessionToken)
      response.removeCookie("session_token")
    }
    response.redirect("/")
  }

  suspend fun middleware(ctx: RoutingContext) {
    val request = ctx.request()
    val response = ctx.response()
    val sessionToken = request.getCookie("session_token")?.value
    if (sessionToken == null) {
      ctx.next()
      return
    }
    val session = sessionsRepository.findByToken(sessionToken)
    if (session == null || session.expiresAt.isBefore(LocalDateTime.now())) {
      response.removeCookie("session_token")
      ctx.next()
      return
    }
    val user = usersRepository.findById(session.userId)
    if (user == null) {
      response.removeCookie("session_token")
      ctx.next()
      return
    }
    ctx.setUser(User.fromName(user.name))
    ctx.next()
  }
}
