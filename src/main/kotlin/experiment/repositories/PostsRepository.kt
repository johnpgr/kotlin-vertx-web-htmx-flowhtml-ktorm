package experiment.repositories

import experiment.entities.PostEntity
import experiment.entities.meta.Post
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import java.util.*

class PostsRepository(private val database: R2dbcDatabase) {
  suspend fun findAll(): List<PostEntity> =
    database.runQuery {
      QueryDsl.from(Post)
    }

  suspend fun findBySlug(slug: String): PostEntity? =
    database.runQuery {
      QueryDsl.from(Post).where { Post.slug eq slug }
    }.firstOrNull()

  suspend fun findById(id: UUID): PostEntity? =
    database.runQuery {
      QueryDsl.from(Post).where { Post.id eq id }
    }.firstOrNull()

  suspend fun add(post: PostEntity): PostEntity? {
    return try {
      database.runQuery {
        QueryDsl.insert(Post).single(post)
      }
    } catch (e: Exception) {
      null
    }
  }
}

