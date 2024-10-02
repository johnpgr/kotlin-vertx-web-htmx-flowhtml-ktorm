package experiment.handlers

import experiment.utils.PeriodicDateResolver
import htmlflow.HtmlView
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.MultiMap
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.*

@Suppress("NOTHING_TO_INLINE")
abstract class AbstractHandler : Handler<RoutingContext> {
    protected companion object {
        const val SOMETHING_WENT_WRONG = "Something went wrong"
        const val NOT_FOUND = "Not found"
        val NULL_HANDLER: Handler<AsyncResult<Void>>? = null
        val SERVER: CharSequence = HttpHeaders.createOptimized("Vert.x-Web")
        val APPLICATION_JSON: CharSequence = HttpHeaders.createOptimized("application/json")
        val TEXT_PLAIN: CharSequence = HttpHeaders.createOptimized("text/plain")
        val TEXT_HTML: CharSequence = HttpHeaders.createOptimized("text/html; charset=utf-8")

        inline fun MultiMap.common(): MultiMap = this
            .add(HttpHeaders.SERVER, SERVER)
            .add(HttpHeaders.DATE, PeriodicDateResolver.current)

        inline fun RoutingContext.json(): HttpServerResponse {
            val response = this.response()
            val headers = response.headers()
            headers
                .common()
                .add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
            return response
        }

        inline fun RoutingContext.text(): HttpServerResponse {
            val response = this.response()
            val headers = response.headers()
            headers
                .common()
                .add(HttpHeaders.CONTENT_TYPE, TEXT_PLAIN)
            return response
        }

        inline fun RoutingContext.html(): HttpServerResponse {
            val response = this.response()
            val headers = response.headers()
            headers
                .common()
                .add(HttpHeaders.CONTENT_TYPE, TEXT_HTML)
            return response
        }

        inline fun RoutingContext.error() = this.text().setStatusCode(500).end(SOMETHING_WENT_WRONG, NULL_HANDLER)
        inline fun RoutingContext.notFound() = this.text().setStatusCode(404).end(NOT_FOUND, NULL_HANDLER)
        inline fun <T> RoutingContext.render(view: HtmlView<T>, params: T) =
            html().end(view.render(params), NULL_HANDLER)

        inline fun RoutingContext.render(view: HtmlView<Unit>) = html().end(view.render(), NULL_HANDLER)

    }

    abstract override fun handle(ctx: RoutingContext): Unit
}
