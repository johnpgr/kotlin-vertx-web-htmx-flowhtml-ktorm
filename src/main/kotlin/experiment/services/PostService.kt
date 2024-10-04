package experiment.services

import experiment.entities.*
import java.time.LocalDateTime
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*

object PostService {
    private lateinit var database: Database

    fun init(database: Database) {
        this.database = database
    }

    fun getAll(): List<Post> {
        return database.posts.toList()
    }

    fun getBySlug(slug: String): Post? {
        return database.posts.find { it.slug eq slug }
    }
}
