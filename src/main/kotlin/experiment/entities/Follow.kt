package experiment.entities

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.datetime
import java.time.LocalDateTime
import java.util.UUID

interface Follow : Entity<Follow> {
    companion object : Entity.Factory<Follow>()
    var id: UUID
    var followerId: UUID
    var followedId: UUID
    var createdAt: LocalDateTime?
}

object Follows : Table<Follow>("follow") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val followerId = uuid("follower_id").bindTo { it.followerId }
    val followedId = uuid("followed_id").bindTo { it.followedId }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
}

val Database.follows get() = this.sequenceOf(Follows)
