package experiment.services

import experiment.entities.User
import experiment.entities.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.ktorm.entity.toList
import java.util.UUID

object UserService {
    private lateinit var database: Database

    fun init(database: Database) {
        this.database = database
    }

    fun getById(userId: UUID): User? {
        return database.users.find { it.id eq userId }
    }

    fun getAll(): List<User> {
        return database.users.toList()
    }

    fun getByUsername(username: String): User? {
        return database.users.find { it.userName eq username }
    }
}
