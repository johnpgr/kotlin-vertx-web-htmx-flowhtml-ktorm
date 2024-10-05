package experiment.views

import experiment.utils.attrHxExt
import experiment.utils.attrHxPost
import experiment.utils.attrHxTargetCustom
import experiment.views.components.AuthInputs
import experiment.views.components.BaseLayout
import htmlflow.*
import org.xmlet.htmlapifaster.*

val RegisterView = view<Unit> {
    BaseLayout {
        main {
            attrClass("min-h-screen w-full flex justify-center items-center")
            form {
                attrClass("border border-border rounded-lg p-16 max-w-sm flex flex-col gap-4")
                attrHxPost("/auth/signup")
                attrHxExt("response-targets")
                attrHxTargetCustom("error", "#any-errors")

                AuthInputs()

                button {
                    attrType(EnumTypeButtonType.SUBMIT)
                    attrClass("btn btn-neutral")
                    text("Register")
                }

                div {
                    attrClass("text-error text-center font-medium")
                    attrId("any-errors")
                }
            }
        }
    }
}
