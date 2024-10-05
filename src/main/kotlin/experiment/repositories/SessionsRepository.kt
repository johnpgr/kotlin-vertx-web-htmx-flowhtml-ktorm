package experiment.repositories

import experiment.entities.Session
import experiment.entities.Sessions
import experiment.entities.sessions
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.add
import org.ktorm.entity.find
import java.time.LocalDateTime
import java.util.UUID

class SessionsRepository(private val database: Database) {
    fun createSession(userId: UUID): Session {
        val token = UUID.randomUUID().toString()
        val now = LocalDateTime.now()
        val expiresAt = now.plusDays(7)

        val session = Session {
            this.id = UUID.randomUUID()
            this.userId = userId
            this.token = token
            this.createdAt = now
            this.expiresAt = expiresAt
        }
        database.sessions.add(session)

        return session
    }

    fun findByToken(token: String): Session? {
        return database.sessions.find { it.token eq token }
    }

    fun deleteSession(token: String): Boolean {
        return database.delete(Sessions) { it.token eq token } == 1
    }

    fun cleanExpiredSessions(): Boolean {
        return database.delete(Sessions) { it.expiresAt less LocalDateTime.now() } == 1
    }
}
