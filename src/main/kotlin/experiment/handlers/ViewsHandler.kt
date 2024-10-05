package experiment.handlers

import experiment.repositories.PostsRepository
import experiment.repositories.UsersRepository
import experiment.utils.render
import experiment.views.HomeView
import experiment.views.HomeViewProps
import experiment.views.LoginView
import experiment.views.RegisterView
import io.vertx.ext.web.RoutingContext
import java.util.*

class ViewsHandler(private val usersRepository: UsersRepository, private val postsRepository: PostsRepository) {
    fun index(ctx: RoutingContext) {
        val posts = postsRepository.findAll()
        val idToken: String? = ctx.user()?.get<String?>("id")
        if(idToken == null) {
            ctx.render(HomeView, HomeViewProps(posts, null))
            return
        }
        val currentUser = usersRepository.findById(UUID.fromString(idToken))
        ctx.render(HomeView, HomeViewProps(posts, currentUser))
    }

    fun login(ctx: RoutingContext) {
        ctx.render(LoginView)
    }

    fun register(ctx: RoutingContext) {
        ctx.render(RegisterView)
    }
}
