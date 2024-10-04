package experiment.entities

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import java.time.LocalDateTime
import java.util.UUID

interface Comment : Entity<Comment> {
    companion object : Entity.Factory<Comment>()
    var id: String
    var postId: UUID
    var authorId: UUID
    var body: String
    var createdAt: LocalDateTime?
    var updatedAt: LocalDateTime?
}

object Comments : Table<Comment>("comment") {
    val id = varchar("id").primaryKey().bindTo { it.id }
    val postId = uuid("post_id").bindTo { it.postId }
    val authorId = uuid("author_id").bindTo { it.authorId }
    val body = varchar("body").bindTo { it.body }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
    val updatedAt = datetime("updated_at").bindTo { it.updatedAt }
}

val Database.comments get() = this.sequenceOf(Comments)
