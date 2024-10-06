package experiment.entities

import org.komapper.annotation.*
import java.util.UUID

@KomapperEntity
@KomapperTable("user")
@KomapperOneToMany(targetEntity = SessionEntity::class, navigator = "sessions")
data class UserEntity(
  @KomapperId
  val id: UUID,
  val name: String,
  val bio: String?,
  @KomapperColumn("hashed_password")
  val hashedPassword: String,
)
