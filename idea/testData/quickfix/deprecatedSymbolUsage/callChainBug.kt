// "Replace with 'newFun()'" "true"

@deprecated("", ReplaceWith("newFun()"))
fun oldFun(): String = ""

fun newFun(): String = ""

fun foo() {
    val value = <caret>oldFun().length()
}
