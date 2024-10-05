package experiment.entities

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.time.LocalDateTime
import java.util.*

interface Favorite : Entity<Favorite> {
  companion object : Entity.Factory<Favorite>() {}

  var id: String
  var userId: UUID
  var postId: UUID
  var createdAt: LocalDateTime?
}

object Favorites : Table<Favorite>("favorite") {
  val id = varchar("id").primaryKey().bindTo {
    it.id
  }
  val userId = uuid("user_id").bindTo {
    it.userId
  }
  val postId = uuid("post_id").bindTo {
    it.postId
  }
  val createdAt = datetime("created_at").bindTo {
    it.createdAt
  }
}

val Database.favorites
  get() = this.sequenceOf(Favorites)




