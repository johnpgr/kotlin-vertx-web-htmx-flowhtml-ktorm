package experiment.views

import experiment.entities.Post
import experiment.utils.*
import experiment.views.components.*
import htmlflow.*
import org.xmlet.htmlapifaster.Div

typealias IndexViewProps = List<Post>

val IndexView =
        view<IndexViewProps> {
            BaseLayout {
                h1 { text("Hello, World!") }
                div {
                    attrHxGet("/test-htmx")
                    attrHxTrigger("click")
                    attrHxSwap("outerHTML")
                    attrHxConfirm("Trigger this element?")
                    text("Test HTMX Element")
                }
                dyn { posts: IndexViewProps ->
                    posts.forEach {
                        postItem(it)
                    }
                }
            }
        }
