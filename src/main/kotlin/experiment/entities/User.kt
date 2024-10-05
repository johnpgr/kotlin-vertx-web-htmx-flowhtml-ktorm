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
    var bio: String?
    var hashedPassword: String?
}

object Users : Table<User>("user") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val userName = varchar("user_name").bindTo { it.userName }
    val bio = varchar("bio").bindTo { it.bio }
    val hashedPassword = varchar("hashed_password").bindTo { it.hashedPassword }
}

val Database.users get() = this.sequenceOf(Users)
