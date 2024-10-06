package experiment.entities

import org.komapper.annotation.*
import java.time.LocalDateTime
import java.util.UUID

@KomapperEntity
@KomapperTable("table")
data class FollowEntity(
  @KomapperId
  val id: UUID,
  @KomapperColumn("follower_id")
  val followerId: UUID,
  @KomapperColumn("followed_id")
  val followedId: UUID,
  @KomapperCreatedAt
  @KomapperColumn("created_at")
  val createdAt: LocalDateTime,
)
