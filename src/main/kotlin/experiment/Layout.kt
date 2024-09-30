package experiment

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
            h1 { text("Hello, World!") }
            div {
                attrHxGet("/test-htmx")
                attrHxTrigger("click")
                attrHxSwap("outerHTML")
                attrHxConfirm("Trigger this element?")
                text("test_element")
            }
            children()
        }
    }
}
