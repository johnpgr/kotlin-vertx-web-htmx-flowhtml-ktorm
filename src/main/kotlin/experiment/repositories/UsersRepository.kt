package experiment.repositories

import experiment.entities.User
import experiment.entities.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.toList
import java.util.*

class UsersRepository(private val database: Database) {
  fun findAll(): List<User> {
    return database.users.toList()
  }

  fun findByUsername(username: String): User? {
    return database.users.find {
      it.userName eq username
    }
  }

  fun findById(id: UUID): User? {
    return database.users.find {
      it.id eq id
    }
  }

  fun add(user: User): Boolean {
    return database.users.add(user) == 1
  }
}

