package experiment.handlers

import io.vertx.core.Future
import io.vertx.ext.web.RoutingContext

fun defaultHandler(ctx: RoutingContext): Future<Void> {
    return ctx.response()
        .setStatusCode(404)
        .putHeader("Content-Type", "text/html")
        .end("<html><body><p>Not Found</p></body></html>")
}
