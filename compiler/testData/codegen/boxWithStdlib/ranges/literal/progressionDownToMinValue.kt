// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
import java.util.ArrayList
import java.lang as j

import java.lang.Integer.MAX_VALUE as MaxI
import java.lang.Integer.MIN_VALUE as MinI
import java.lang.Byte.MAX_VALUE as MaxB
import java.lang.Byte.MIN_VALUE as MinB
import java.lang.Short.MAX_VALUE as MaxS
import java.lang.Short.MIN_VALUE as MinS
import java.lang.Long.MAX_VALUE as MaxL
import java.lang.Long.MIN_VALUE as MinL
import java.lang.Character.MAX_VALUE as MaxC
import java.lang.Character.MIN_VALUE as MinC

fun box(): String {
    val list1 = ArrayList<Int>()
    for (i in (MinI + 2) downTo MinI step 1) {
        list1.add(i)
        if (list1.size() > 23) break
    }
    if (list1 != listOf<Int>(MinI + 2, MinI + 1, MinI)) {
        return "Wrong elements for (MinI + 2) downTo MinI step 1: $list1"
    }

    val list2 = ArrayList<Byte>()
    for (i in (MinB + 2).toByte() downTo MinB step 1) {
        list2.add(i)
        if (list2.size() > 23) break
    }
    if (list2 != listOf<Byte>((MinB + 2).toByte(), (MinB + 1).toByte(), MinB)) {
        return "Wrong elements for (MinB + 2).toByte() downTo MinB step 1: $list2"
    }

    val list3 = ArrayList<Short>()
    for (i in (MinS + 2).toShort() downTo MinS step 1) {
        list3.add(i)
        if (list3.size() > 23) break
    }
    if (list3 != listOf<Short>((MinS + 2).toShort(), (MinS + 1).toShort(), MinS)) {
        return "Wrong elements for (MinS + 2).toShort() downTo MinS step 1: $list3"
    }

    val list4 = ArrayList<Long>()
    for (i in (MinL + 2).toLong() downTo MinL step 1) {
        list4.add(i)
        if (list4.size() > 23) break
    }
    if (list4 != listOf<Long>((MinL + 2).toLong(), (MinL + 1).toLong(), MinL)) {
        return "Wrong elements for (MinL + 2).toLong() downTo MinL step 1: $list4"
    }

    val list5 = ArrayList<Char>()
    for (i in (MinC + 2).toChar() downTo MinC step 1) {
        list5.add(i)
        if (list5.size() > 23) break
    }
    if (list5 != listOf<Char>((MinC + 2).toChar(), (MinC + 1).toChar(), MinC)) {
        return "Wrong elements for (MinC + 2).toChar() downTo MinC step 1: $list5"
    }

    return "OK"
}
