package experiment.entities

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.varchar
import org.ktorm.schema.uuid
import java.util.UUID

interface User : Entity<User> {
    companion object : Entity.Factory<User>()

    var id: UUID
    var userName: String
    var email: String
    var bio: String?
    var avatarUrl: String?
    var hashedPassword: String?
    var emailVerified: Boolean
}

object Users : Table<User>("user") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val userName = varchar("user_name").bindTo { it.userName }
    val email = varchar("email").bindTo { it.email }
    val bio = varchar("bio").bindTo { it.bio }
    val avatarUrl = varchar("avatar_url").bindTo { it.avatarUrl }
    val hashedPassword = varchar("hashed_password").bindTo { it.hashedPassword }
    val emailVerified = boolean("email_verified").bindTo { it.emailVerified }
}

val Database.users get() = this.sequenceOf(Users)
