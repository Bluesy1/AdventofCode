package day_09_2016

import java.io.File

fun part1(input: String) {
    var res = ""
    var ptr = 0
    val len = input.length
    while (ptr < len) {
        val c = input[ptr]
        if (c == '(') {
            val end = input.indexOf(')', ptr)
            val (count, repeat) = input.substring(ptr + 1, end).split('x').map { it.toInt() }
            res += input.substring(end + 1, end + 1 + count).repeat(repeat)
            ptr = end + 1 + count
        } else {
            res += c
            ptr++
        }
    }
    println("The length of the decompiled file is ${res.length}")
}

fun part2(input: String) {
    println("The length of the decompiled file is ${part2(input.toCharArray().toList())}")
}

private tailrec fun part2(content: List<Char>, count: Long = 0L): Long {
    if (content.isEmpty())
        return count
    if (content[0] == '(') {
        val markerIndex = content.indexOf(')')
        val marker = content.subList(1, markerIndex)
        val index = marker.indexOf('x')
        val len = marker.subList(0, index).joinToString(separator = "").toInt()
        val times = marker.subList(index+1, marker.size).joinToString(separator = "").toInt()
        return part2(
            content.subList(markerIndex+len+1, content.size),
            count + (times * part2(content.subList(markerIndex+1, markerIndex+len+1)))
        )
    } else {
        return part2(content.subList(1, content.size), count+1)
    }
}


fun main(){
    val inputFile = File("2016/inputs/Day_09.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readText())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readText())
}