package experiment.views

import experiment.entities.Post
import experiment.entities.User
import experiment.entities.posts
import experiment.utils.*
import experiment.views.components.*
import htmlflow.*
import io.vertx.ext.web.RoutingContext
import org.ktorm.entity.toList


class HomeViewProps(val posts: List<Post>, val user: User?)

val HomeView =
        view<HomeViewProps> {
            BaseLayout {
                h1 { text("Hello, World!") }
                div {
                    attrHxGet("/test-htmx")
                    attrHxTrigger("click")
                    attrHxSwap("outerHTML")
                    attrHxConfirm("Trigger this element?")
                    text("Test HTMX Element")
                }
                dyn { props: HomeViewProps ->
                    props.posts.forEach {
                        PostItem(it)
                    }

                    div {
                        if(props.user != null) text("You are ${props.user.userName}")
                    }
                }
            }
        }
