package day_02_2015

import java.io.File
fun part1(input: List<String>) {
    input.map { it.split('x').map(String::toInt) }.sumOf {
        val a1 = it[0] * it[1]
        val a2 = it[1] * it[2]
        val a3 = it[2] * it[0]
        val extra = minOf(a1, a2, a3)
        (2 * (a1 + a2 + a3)) + extra
    }
        .let { print("The total needed square feet is $it") }
}
// not 659

fun part2(input: List<String>) {
        input.map { it.split('x').map(String::toInt) }.sumOf {
        val a1 = it.sorted().dropLast(1)
        (2 * a1.sum() )+ (it[0] * it[1] * it[2])
    }
        .let { print("The total needed ribbon is $it") }
}

fun main(){
    val inputFile = File("2015/inputs/Day_02.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}