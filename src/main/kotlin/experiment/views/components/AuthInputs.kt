package experiment.views.components

import htmlflow.div
import htmlflow.input
import htmlflow.label
import org.xmlet.htmlapifaster.EnumTypeInputType
import org.xmlet.htmlapifaster.Form

fun Form<*>.AuthInputs() {
    div {
        label {
            attrClass("input input-bordered flex items-center gap-2")
            UserIcon()
            input {
                attrPlaceholder("Username")
                attrClass("grow")
                attrId("username")
                attrName("username")
                attrRequired(true)
                attrType(EnumTypeInputType.TEXT)
            }
        }
    }

    div {
        label {
            attrClass("input input-bordered flex items-center gap-2")
            PasswordIcon()
            input {
                attrPlaceholder("Password")
                attrClass("grow")
                attrName("password")
                attrRequired(true)
                attrType(EnumTypeInputType.PASSWORD)
            }
        }
    }
}

