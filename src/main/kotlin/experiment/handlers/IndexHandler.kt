package experiment.handlers

import experiment.services.UserService
import experiment.views.IndexView
import io.vertx.ext.web.RoutingContext

object IndexHandler : AbstractHandler() {
    override fun handle(ctx: RoutingContext) {
        val users = UserService.getAll()

        return ctx.render(IndexView, users)
    }
}
