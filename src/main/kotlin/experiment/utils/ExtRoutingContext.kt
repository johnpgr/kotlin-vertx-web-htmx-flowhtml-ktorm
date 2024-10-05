package experiment.utils

import experiment.entities.User
import experiment.repositories.UsersRepository
import io.vertx.ext.web.RoutingContext

fun RoutingContext.currentUser(usersRepository: UsersRepository): User? {
    val userName = user()?.subject() ?: return null
    return usersRepository.findByUsername(userName)
}
