package experiment.views.components

import htmlflow.*
import org.xmlet.htmlapifaster.Html

fun HtmlPage.baseLayout(children: Html<HtmlPage>.() -> Unit): HtmlPage {
    return html {
        head {
            script { attrSrc("https://cdn.tailwindcss.com") }
            script { attrSrc("https://unpkg.com/htmx.org@2.0.2") }
        }
        body {
            attrClass("bg-gray-900 text-white")
            children()
        }
    }
}
