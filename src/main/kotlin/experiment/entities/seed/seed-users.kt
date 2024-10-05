package experiment.entities.seed
import experiment.entities.*
import java.util.UUID
import org.ktorm.database.Database
import org.ktorm.entity.*

fun seedUsers(database: Database) {
    val users = listOf(User {
        id = UUID.randomUUID()
        userName = "john_doe"
        bio = "I'm a software developer"
        hashedPassword = "hashed_password_1"
    }, User {
        id = UUID.randomUUID()
        userName = "jane_smith"
        bio = "I love writing and coding"
        hashedPassword = "hashed_password_2"
    })
    users.forEach {
        try {
            database.users.add(it)
        } catch (e: Throwable) {}
    }
}

