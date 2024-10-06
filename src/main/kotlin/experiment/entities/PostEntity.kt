package experiment.entities

import org.komapper.annotation.*
import java.time.LocalDateTime
import java.util.UUID


@KomapperTable("post")
@KomapperEntity
@KomapperOneToMany(targetEntity = CommentEntity::class, navigator = "comments")
data class PostEntity(
  @KomapperId
  val id: UUID,
  @KomapperColumn("author_id")
  val authorId: UUID,
  val slug: String,
  val title: String,
  val description: String,
  val body: String,
  @KomapperColumn("created_at")
  @KomapperCreatedAt
  val createdAt: LocalDateTime,
  @KomapperColumn("updated_at")
  @KomapperUpdatedAt
  val updatedAt: LocalDateTime,
)
