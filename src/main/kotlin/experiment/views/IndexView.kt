package experiment.views

import experiment.entities.User
import experiment.utils.attrHxConfirm
import experiment.utils.attrHxGet
import experiment.utils.attrHxSwap
import experiment.utils.attrHxTrigger
import experiment.views.components.baseLayout
import htmlflow.*

typealias IndexViewProps = List<User>

val IndexView =
    view<IndexViewProps> {
        baseLayout {
            h1 { text("Hello, World!") }
            div {
                attrHxGet("/test-htmx")
                attrHxTrigger("click")
                attrHxSwap("outerHTML")
                attrHxConfirm("Trigger this element?")
                text("Test HTMX Element")
            }
            dyn { users: IndexViewProps ->
                users.map { user ->
                    p {
                        attrClass("text-2xl mt-2 border-t")
                        text(user.toString())
                    }
                }
            }
        }
    }
