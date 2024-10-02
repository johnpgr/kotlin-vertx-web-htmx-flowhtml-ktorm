package experiment.handlers

import io.vertx.ext.web.RoutingContext

object DefaultHandler: AbstractHandler() {
    override fun handle(ctx: RoutingContext) {
        return ctx.notFound()
    }
}
