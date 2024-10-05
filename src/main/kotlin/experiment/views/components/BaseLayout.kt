package experiment.views.components

import experiment.utils.attrHxBoost
import experiment.utils.attrHxExt
import htmlflow.*
import org.xmlet.htmlapifaster.EnumRelType
import org.xmlet.htmlapifaster.Html

fun HtmlPage.BaseLayout(children: Html<HtmlPage>.() -> Unit): HtmlPage {
    return html {
        head {
            script { attrSrc("https://unpkg.com/htmx.org@2.0.2") }
            script { attrSrc("https://unpkg.com/htmx-ext-response-targets@2.0.0/response-targets.js") }
            link {
                attrHref("/static/styles.css")
                attrRel(EnumRelType.STYLESHEET)
            }
        }
        body {
            attrHxBoost(true)
            children()
        }
    }
}
