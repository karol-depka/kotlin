open class A {
    class B : A() {
        val a = 1
    }

    fun foo() {
        if (this is B) eat(a)
    }

    fun eat(x: Int) {}
}

fun box(): String {
    A()
    return "OK"
}
