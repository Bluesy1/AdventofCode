package day_16_2016

import java.io.File

fun solution(input: String, targetLength: Int) {
    var data = input
    while (data.length < targetLength) {
        val inverted = data.map { if (it == '0') '1' else '0' }.joinToString("")
        data += "0" + inverted.reversed()
    }
    data = data.substring(0, targetLength)
    var checksum = data
    while (checksum.length%2 == 0){
        checksum = checksum.chunked(2).joinToString("") { if (it[0] == it[1]) "1" else "0" }
    }
    print("The checksum is $checksum")
}
// 10110111000111000 is too large
// 00100111000101111

fun main(){
    val input = File("2016/inputs/Day_16.txt").readText()
    print("\n----- Part 1 -----\n")
    solution(input, 272)
    print("\n----- Part 2 -----\n")
    solution(input, 35651584)
}