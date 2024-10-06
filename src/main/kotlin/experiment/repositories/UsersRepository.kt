package experiment.repositories

import experiment.entities.UserEntity
import experiment.entities.meta.User
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import java.util.*

class UsersRepository(private val database: R2dbcDatabase) {
  suspend fun findAll(): List<UserEntity> =
    database.runQuery { QueryDsl.from(User) }

  suspend fun findByUsername(username: String): UserEntity? =
    database.runQuery {
      QueryDsl.from(User).where { User.name eq username }
    }.firstOrNull()

  suspend fun findById(id: UUID): UserEntity? =
    database.runQuery {
      QueryDsl.from(User).where { User.id eq id }
    }.firstOrNull()

  suspend fun add(user: UserEntity): UserEntity? {
    return try {
      database.runQuery{
        QueryDsl.insert(User).single(user)
      }
    } catch (e: Exception) {
      null
    }
  }
}

