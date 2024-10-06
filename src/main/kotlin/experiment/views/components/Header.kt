package experiment.views.components

import experiment.entities.UserEntity
import experiment.utils.attrHxPost
import experiment.utils.attrHxTarget
import htmlflow.*
import org.xmlet.htmlapifaster.Element

fun <T : Element<T, Z>, Z : Element<*, *>> T.Header(user: UserEntity?) {
  header {
    attrClass("flex items-center justify-end p-8 py-4 w-full bg-primary-content")

    if (user != null) div {
      attrClass("flex items-center gap-4")
      p {
        text("You are ${user.name}")
      }
      button {
        attrClass("btn btn-neutral")
        attrHxPost("/auth/signout")
        attrHxTarget("body")
        text("Logoff")
      }
    } else div {
      attrClass("flex items-center gap-4")
      a {
        attrClass("btn btn-neutral")
        attrHref("/login")
        text("Login")
      }
      a {
        attrClass("btn btn-neutral")
        attrHref("/register")
        text("Register")
      }
    }
  }
}


