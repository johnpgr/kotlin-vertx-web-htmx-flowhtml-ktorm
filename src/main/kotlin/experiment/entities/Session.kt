package experiment.entities

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import org.ktorm.schema.datetime
import java.time.LocalDateTime
import java.util.UUID

interface Session : Entity<Session> {
    companion object : Entity.Factory<Session>()
    var id: UUID
    var userId: UUID
    var token: String
    var createdAt: LocalDateTime
    var expiresAt: LocalDateTime
}

object Sessions : Table<Session>("session") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val userId = uuid("user_id").bindTo { it.userId }
    val token = varchar("token").bindTo { it.token }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
    val expiresAt = datetime("expires_at").bindTo { it.expiresAt }
}

val Database.sessions get() = this.sequenceOf(Sessions)
