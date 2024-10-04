package experiment.entities.seed

import experiment.entities.*
import java.time.LocalDateTime
import java.util.UUID
import org.ktorm.database.Database
import org.ktorm.entity.*

fun seedPosts(database: Database) {
    val posts =
            listOf(
                    Post {
                        id = UUID.randomUUID()
                        authorId = database.users.first().id
                        slug = "first-post"
                        title = "My First Post"
                        description = "This is my first post on the platform"
                        body = "Hello world! This is the content of my first post."
                        createdAt = LocalDateTime.now()
                        updatedAt = LocalDateTime.now()
                    },
                    Post {
                        id = UUID.randomUUID()
                        authorId = database.users.first().id
                        slug = "second-post"
                        title = "Another Great Post"
                        description = "Sharing more thoughts and ideas"
                        body = "Here's another post with some interesting content."
                        createdAt = LocalDateTime.now()
                        updatedAt = LocalDateTime.now()
                    }
            )

    posts.forEach { try {database.posts.add(it)} catch(e: Throwable){}}
}
