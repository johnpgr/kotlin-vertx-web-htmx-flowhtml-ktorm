package experiment.entities

import org.komapper.annotation.*
import java.time.LocalDateTime
import java.util.UUID

@KomapperEntity
@KomapperTable("comment")
@KomapperManyToOne(targetEntity = PostEntity::class, navigator = "post")
data class CommentEntity(
  @KomapperId
  val id: UUID,
  @KomapperColumn("post_id")
  val postId: UUID,
  @KomapperColumn("author_id")
  val authorId: UUID,
  val body: String,
  @KomapperCreatedAt
  @KomapperColumn("created_at")
  val createdAt: LocalDateTime,
  @KomapperUpdatedAt
  @KomapperColumn("updated_at")
  val updatedAt: LocalDateTime,
)
