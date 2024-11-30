package day_05_2016

import java.security.MessageDigest


val MD5: MessageDigest = MessageDigest.getInstance("MD5")

fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

fun part1() {
    var key = ""
    var i = 0
    //var i = 2307656 // e after 2307655, ec after 2503852
    while (key.length < 8) {
        MD5.reset()
        val bytes = MD5.digest(("cxdnnyjw$i").toByteArray()).toHex()
        if (bytes.startsWith("00000")) {
            key += bytes[5]
        }
        i++
    }
    print("The password is $key, found after $i iterations")
    // ec8697ee is wrong
}


fun part2() {
    val hashSequence = generateSequence(0, Int::inc).map { MD5.digest("cxdnnyjw$it".toByteArray()).toHex() }
    val p2 = hashSequence.filter { it.startsWith("00000") }
        .filter { it[5] in '0'..'7' }
        .distinctBy { it[5] }.take(8)
        .fold(arrayOfNulls<Char>(8)) {
            acc, it ->
            acc[it[5]-'0'] = it[6]
            acc
        }.joinToString(separator = "")
    print("The password is $p2")
}

fun main(){
    print("\n----- Part 1 -----\n")
    part1()
    print("\n----- Part 2 -----\n")
    part2()
}