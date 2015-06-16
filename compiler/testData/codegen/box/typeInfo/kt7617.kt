open class SuperFoo {
    public fun bar() {
        if (this is Foo) {
            superFoo()
            baz()
        }
    }

    public fun baz() {
    }
}

class Foo : SuperFoo() {
    public fun superFoo() {}
}

fun box(): String {
    Foo().bar()
    return "OK"
}
