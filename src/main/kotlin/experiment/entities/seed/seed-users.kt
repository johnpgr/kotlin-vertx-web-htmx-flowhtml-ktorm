package experiment.entities.seed

import experiment.entities.*
import java.util.UUID
import org.ktorm.database.Database
import org.ktorm.entity.*

fun seedUsers(database: Database) {
    val users =
            listOf(
                    User {
                        id = UUID.randomUUID()
                        userName = "john_doe"
                        email = "john@example.com"
                        bio = "I'm a software developer"
                        avatarUrl = "https://example.com/avatar1.jpg"
                        hashedPassword = "hashed_password_1"
                        emailVerified = true
                    },
                    User {
                        id = UUID.randomUUID()
                        userName = "jane_smith"
                        email = "jane@example.com"
                        bio = "I love writing and coding"
                        avatarUrl = "https://example.com/avatar2.jpg"
                        hashedPassword = "hashed_password_2"
                        emailVerified = true
                    }
            )
    users.forEach { try { database.users.add(it) } catch(e: Throwable){} }
}
