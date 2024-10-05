package experiment.repositories

import experiment.entities.Post
import experiment.entities.posts
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.toList
import java.util.*

class PostsRepository(private val database: Database) {
    fun findAll(): List<Post> {
        return database.posts.toList()
    }

    fun findBySlug(slug: String): Post? {
        return database.posts.find { it.slug eq slug }
    }

    fun findById(id: UUID): Post? {
        return database.posts.find { it.id eq id }
    }

    fun add(post: Post): Boolean {
        return database.posts.add(post) == 1
    }
}
