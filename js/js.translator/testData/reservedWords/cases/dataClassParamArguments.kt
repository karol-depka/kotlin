package foo

// NOTE THIS FILE IS AUTO-GENERATED by the generateTestDataForReservedWords.kt. DO NOT EDIT!

data class DataClass(arguments: String) {
    init {
        testRenamed("arguments", { arguments })
    }
}

fun box(): String {
    DataClass("123")

    return "OK"
}