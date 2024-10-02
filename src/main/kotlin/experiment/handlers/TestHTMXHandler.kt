package experiment.handlers

import experiment.views.partials.TestView
import io.vertx.ext.web.RoutingContext

object TestHTMXHandler : AbstractHandler() {
    override fun handle(ctx: RoutingContext) {
        return ctx.render(TestView)
    }
}
