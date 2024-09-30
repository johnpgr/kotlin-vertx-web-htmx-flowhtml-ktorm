package experiment

import htmlflow.HtmlView
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse

fun <T> HttpServerResponse.renderView(view: HtmlView<T>, params: T): Future<Void> {
    return this.end(view.render(params))
}

fun HttpServerResponse.renderView(view: HtmlView<Unit>): Future<Void> {
    return this.end(view.render())
}

