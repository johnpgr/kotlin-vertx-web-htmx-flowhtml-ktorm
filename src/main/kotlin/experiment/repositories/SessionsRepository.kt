package experiment.repositories

import experiment.entities.SessionEntity
import experiment.entities.meta.Session
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import java.time.LocalDateTime
import java.util.*


class SessionsRepository(private val database: R2dbcDatabase) {
  suspend fun createSession(userId: UUID): SessionEntity {
    val token = UUID.randomUUID().toString()
    val now = LocalDateTime.now()
    val expiresAt = now.plusDays(7)

    val session = SessionEntity(
      id = UUID.randomUUID(),
      userId = userId,
      token = token,
      expiresAt = expiresAt,
      createdAt = now,
    )
    val createdSession = database.runQuery {
      QueryDsl.insert(Session).single(session)
    }

    return createdSession
  }

  suspend fun findByToken(token: String): SessionEntity? =
    database.runQuery {
      QueryDsl.from(Session).where { Session.token eq token }
    }.firstOrNull()

  suspend fun deleteSession(token: String): Boolean =
    database.runQuery {
      QueryDsl.delete(Session).where { Session.token eq token }
    } == 1L

  suspend fun cleanExpiredSessions() =
    database.runQuery {
      QueryDsl.delete(Session)
        .where { Session.expiresAt less LocalDateTime.now() }
    }
}

