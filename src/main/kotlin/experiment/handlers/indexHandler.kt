package experiment.handlers

import experiment.services.UserService
import experiment.utils.renderView
import experiment.views.IndexView
import io.vertx.core.Future
import io.vertx.ext.web.RoutingContext

fun indexHandler(ctx: RoutingContext): Future<Void> {
    val users = UserService.getAll()

    return ctx.response().renderView(IndexView, users)
}
