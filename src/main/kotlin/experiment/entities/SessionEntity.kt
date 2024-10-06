package experiment.entities

import org.komapper.annotation.*
import java.time.LocalDateTime
import java.util.UUID


@KomapperEntity
@KomapperTable("session")
@KomapperManyToOne(targetEntity = UserEntity::class, navigator = "user")
data class SessionEntity(
  @KomapperId
  val id: UUID,
  @KomapperColumn("user_id")
  val userId: UUID,
  val token: String,
  @KomapperCreatedAt()
  @KomapperColumn("created_at")
  val createdAt: LocalDateTime,
  @KomapperColumn("expires_at")
  val expiresAt: LocalDateTime
)
