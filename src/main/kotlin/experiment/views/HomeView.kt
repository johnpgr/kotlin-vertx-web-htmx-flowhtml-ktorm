package experiment.views

import experiment.entities.PostEntity
import experiment.entities.UserEntity
import experiment.views.components.BaseLayout
import experiment.views.components.Header
import experiment.views.components.PostItem
import htmlflow.*


class HomeViewProps(val posts: List<PostEntity>, val user: UserEntity?)

val HomeView = view<HomeViewProps> {
  BaseLayout {
    dyn { props: HomeViewProps ->
      Header(props.user)
      main {
        attrClass("p-4")
        h1 {
          attrClass("text-3xl font-bold")
          text("Hello, World!")
        }
      }
      ul {
        props.posts.forEach {
          PostItem(it)
        }
      }
    }
  }
}

