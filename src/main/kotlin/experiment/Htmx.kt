package experiment

import org.xmlet.htmlapifaster.Element

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxGet(value: String): T {
    this.visitor.visitAttribute("hx-get", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxPost(value: String): T {
    this.visitor.visitAttribute("hx-post", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxDelete(value: String): T {
    this.visitor.visitAttribute("hx-delete", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxTrigger(value: String): T {
    this.visitor.visitAttribute("hx-trigger", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxSwap(value: String): T {
    this.visitor.visitAttribute("hx-swap", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxSwapOob(value: String): T {
    this.visitor.visitAttribute("hx-swap-oob", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxOn(event: String, value: String): T {
    this.visitor.visitAttribute("hx-on:$event", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxPushUrl(value: String): T {
    this.visitor.visitAttribute("hx-push-url", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxSelect(value: String): T {
    this.visitor.visitAttribute("hx-select", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxSelectOob(value: String): T {
    this.visitor.visitAttribute("hx-select-oob", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxVals(value: String): T {
    this.visitor.visitAttribute("hx-vals", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxBoost(value: Boolean): T {
    this.visitor.visitAttribute("hx-boost", value.toString());
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxConfirm(value: String): T {
    this.visitor.visitAttribute("hx-confirm", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxDisable(): T {
    this.visitor.visitAttribute("hx-disable", "");
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxDisabledElt(value: String): T {
    this.visitor.visitAttribute("hx-disabled-elt", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxDisinherit(value: String): T {
    this.visitor.visitAttribute("hx-disinherit", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxEncoding(value: String): T {
    this.visitor.visitAttribute("hx-encoding", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxExt(value: String): T {
    this.visitor.visitAttribute("hx-ext", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxHeaders(value: String): T {
    this.visitor.visitAttribute("hx-headers", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxHistory(value: Boolean): T {
    this.visitor.visitAttribute("hx-history", value.toString());
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxHistoryElt(): T {
    this.visitor.visitAttribute("hx-history-elt", "");
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxInclude(value: String): T {
    this.visitor.visitAttribute("hx-include", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxIndicator(value: String): T {
    this.visitor.visitAttribute("hx-indicator", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxInherit(value: String): T {
    this.visitor.visitAttribute("hx-inherit", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxParams(value: String): T {
    this.visitor.visitAttribute("hx-params", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxPatch(value: String): T {
    this.visitor.visitAttribute("hx-patch", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxPreserve(): T {
    this.visitor.visitAttribute("hx-preserve", "");
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxPrompt(value: String): T {
    this.visitor.visitAttribute("hx-prompt", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxPut(value: String): T {
    this.visitor.visitAttribute("hx-put", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxReplaceUrl(value: String): T {
    this.visitor.visitAttribute("hx-replace-url", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxReplaceUrl(value: Boolean): T {
    this.visitor.visitAttribute("hx-replace-url", value.toString());
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxRequest(value: String): T {
    this.visitor.visitAttribute("hx-request", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxSync(value: String): T {
    this.visitor.visitAttribute("hx-sync", value);
    return this.self()
}

fun <T : Element<T, Z>, Z : Element<*, *>> T.attrHxValidate(value: Boolean): T {
    this.visitor.visitAttribute("hx-validate", value.toString());
    return this.self()
}
