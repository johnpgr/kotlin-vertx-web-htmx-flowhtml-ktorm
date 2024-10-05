package experiment.utils

import io.netty.util.AsciiString
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpServerResponse

enum class ContentType(val value: CharSequence) {
  APPLICATION_JSON(AsciiString.cached("application/json")),
  APPLICATION_OCTET_STREAM(AsciiString.cached("application/octet-stream")),
  TEXT_PLAIN(AsciiString.cached("text/plain")),
  TEXT_HTML(AsciiString.cached("text/html")),
  TEXT_CSS(AsciiString.cached("text/css")),
  TEXT_JAVASCRIPT(AsciiString.cached("text/javascript")),
  IMAGE_PNG(AsciiString.cached("image/png")),
  IMAGE_JPEG(AsciiString.cached("image/jpeg")),
  IMAGE_WEBP(AsciiString.cached("image/webp")),
  VIDEO_MP4(AsciiString.cached("video/mp4")),
  VIDEO_AVC(AsciiString.cached("video/avc")),
  AUDIO_MPEG(AsciiString.cached("audio/mpeg"))
}

fun HttpServerResponse.setContentType(contentType: ContentType): HttpServerResponse {
  putHeader(HttpHeaders.CONTENT_TYPE, contentType.value)
  return this
}

fun HttpServerResponse.redirect(url: String) {
  this.putHeader(HttpHeaders.LOCATION, url).setStatusCode(301).end()
}

