package experiment.handlers

import experiment.services.UserService
import experiment.services.PostService
import experiment.views.IndexView
import io.vertx.ext.web.RoutingContext

object IndexHandler : AbstractHandler() {
    override fun handle(ctx: RoutingContext) {
        val posts = PostService.getAll()

        return ctx.render(IndexView, posts)
    }
}
