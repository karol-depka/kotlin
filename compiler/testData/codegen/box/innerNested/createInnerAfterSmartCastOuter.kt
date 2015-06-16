open class A

class C : A() {
    open inner class B() {
        fun box() = "OK"
    }
}

fun A.foo(): String {
    if (this is C) {
        return B().box()
    }
    return "Fail"
}

fun box() = C().foo()
