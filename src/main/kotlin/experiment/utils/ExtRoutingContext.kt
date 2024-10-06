package experiment.utils

import experiment.entities.UserEntity
import experiment.repositories.UsersRepository
import io.vertx.ext.web.RoutingContext

suspend fun RoutingContext.currentUser(usersRepository: UsersRepository): UserEntity? {
  val userName = user()?.subject() ?: return null
  return usersRepository.findByUsername(userName)
}

