package experiment.views

import experiment.entities.User
import experiment.utils.attrHxExt
import experiment.utils.attrHxTargetCustom
import experiment.views.components.AuthInputs
import experiment.views.components.BaseLayout
import experiment.views.components.Header
import htmlflow.*
import org.xmlet.htmlapifaster.EnumMethodType
import org.xmlet.htmlapifaster.EnumTypeButtonType

val LoginView = view<User?> {
    BaseLayout {
        dyn { user: User? ->
            Header(user)
            main {
                attrClass("min-h-[calc(100vh-80px)] w-full flex justify-center items-center")
                form {
                    attrClass("border border-border rounded-lg p-16 max-w-sm flex flex-col gap-4")
                    attrAction("/auth/signin")
                    attrMethod(EnumMethodType.POST)
                    attrHxExt("response-targets")
                    attrHxTargetCustom("error", "#any-errors")

                    AuthInputs()

                    button {
                        attrType(EnumTypeButtonType.SUBMIT)
                        attrClass("btn btn-neutral")
                        text("Login")
                    }

                    div {
                        attrClass("text-error text-center font-medium")
                        attrId("any-errors")
                    }
                }
            }
        }
    }
}
