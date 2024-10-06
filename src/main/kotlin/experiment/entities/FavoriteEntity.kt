package experiment.entities

import org.komapper.annotation.*
import java.time.LocalDateTime
import java.util.UUID

@KomapperEntity
@KomapperTable("favorite")
@KomapperManyToOne(targetEntity = PostEntity::class, navigator = "post")
data class FavoriteEntity(
  @KomapperId
  val id: UUID,
  @KomapperColumn("user_id")
  val userId: UUID,
  @KomapperColumn("post_id")
  val postId: UUID,
  @KomapperColumn("created_at")
  @KomapperCreatedAt
  val createdAt: LocalDateTime,
)
