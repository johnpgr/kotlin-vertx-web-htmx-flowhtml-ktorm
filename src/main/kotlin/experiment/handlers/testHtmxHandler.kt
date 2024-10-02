package experiment.handlers

import experiment.utils.renderView
import experiment.views.partials.TestView
import io.vertx.core.Future
import io.vertx.ext.web.RoutingContext

fun testHtmxHandler(ctx: RoutingContext): Future<Void> {
    return ctx.response().renderView(TestView)
}
