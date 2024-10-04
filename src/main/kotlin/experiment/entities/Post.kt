package experiment.entities

import java.time.LocalDateTime
import java.util.UUID
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.varchar
import org.ktorm.schema.uuid

interface Post : Entity<Post> {
    companion object : Entity.Factory<Post>()

    var id: UUID
    var authorId: UUID
    var slug: String
    var title: String
    var description: String
    var body: String
    var createdAt: LocalDateTime?
    var updatedAt: LocalDateTime?

    var author: User
}

object Posts : Table<Post>("post") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val authorId = uuid("author_id").bindTo { it.authorId }.references(Users) { it.author }
    val slug = varchar("slug").bindTo { it.slug }
    val title = varchar("title").bindTo { it.title }
    val description = varchar("description").bindTo { it.description }
    val body = varchar("body").bindTo { it.body }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
    val updatedAt = datetime("updated_at").bindTo { it.updatedAt }
}

val Database.posts
    get() = this.sequenceOf(Posts)
