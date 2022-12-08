package day_03_2016

import java.io.File

fun part1(input: List<String>) {
    input.map { 
        val strings = it.split(" ").filter { it1 -> it1.isBlank() }
        strings.map { it1 -> it1.toInt() }
    }.stream()
        .filter {
        (it[0] + it[1] > it[2]) && (it[0] + it[2] > it[1]) && (it[1] + it[2] > it[0])
    }.count().let { print("The number of valid triangles is $it") }

}


fun part2(input: List<String>) {
        input.map {
        val strings = it.split(" ").filter { it1 -> it1.isBlank() }
        strings.map { it1 -> it1.toInt() }
    }.chunked(3).map {
        var count = 0
        for (i in 0 until 3)
        {
            if ((it[0][i] + it[1][i] > it[2][i]) && (it[0][i] + it[2][i] > it[1][i]) && (it[1][i] + it[2][i] > it[0][i]))
                count++
        }
        count
    }.sum().let { print("The number of valid triangles is $it") }
}

fun main(){
    val inputFile = File("2016/inputs/Day_03.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}