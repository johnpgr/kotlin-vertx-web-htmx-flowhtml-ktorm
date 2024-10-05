package experiment.views.components

import experiment.entities.Post
import htmlflow.*
import org.xmlet.htmlapifaster.Element
import org.xmlet.htmlapifaster.Li
import org.xmlet.htmlapifaster.Ul

fun Ul<*>.PostItem(post: Post): Ul<*> {
    return li {
        attrClass("bg-white shadow rounded-md p-4")
        div {
            attrClass("flex items-center")
            button {
                attrClass("flex flex-col items-center mr-4 text-gray-500 hover:text-orange-500")
                span {
                    attrClass("h-6 w-6")
                    text("↑") // Placeholder for ArrowBigUp icon
                }
                span {
                    attrClass("text-xs font-semibold")
                    // text(post.score.toString())
                }
            }
            div {
                attrClass("flex-grow")
                h2 {
                    attrClass("text-lg font-semibold mb-1")
                    a {
                        // attrHref(post.url)
                        attrClass("text-blue-600 hover:underline")
                        attrTarget("_blank")
                        text(post.title)
                    }
                }
                p {
                    attrClass("text-sm text-gray-500")
                    //                                text("by ${post.author} | ${post.time}")
                }
            }
        }
        div {
            attrClass("mt-2 flex items-center text-sm text-gray-500")
            span {
                attrClass("h-4 w-4 mr-1")
                text("💬") // Placeholder for MessageSquare icon
            }
            //                        span { text("${post.commentCount} comments") }
        }
    }
}
