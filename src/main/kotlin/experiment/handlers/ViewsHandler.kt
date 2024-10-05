package experiment.handlers

import experiment.repositories.PostsRepository
import experiment.repositories.UsersRepository
import experiment.utils.currentUser
import experiment.utils.render
import experiment.views.HomeView
import experiment.views.HomeViewProps
import experiment.views.LoginView
import experiment.views.RegisterView
import io.vertx.ext.web.RoutingContext

class ViewsHandler(
    private val usersRepository: UsersRepository,
    private val postsRepository: PostsRepository
) {
    fun index(ctx: RoutingContext) {
        val posts = postsRepository.findAll()
        val user = ctx.currentUser(usersRepository)
        ctx.render(HomeView, HomeViewProps(posts, user))
    }

    fun login(ctx: RoutingContext) {
        val user = ctx.currentUser(usersRepository)
        ctx.render(LoginView, user)
    }

    fun register(ctx: RoutingContext) {
        val user = ctx.currentUser(usersRepository)
        ctx.render(RegisterView, user)
    }
}
