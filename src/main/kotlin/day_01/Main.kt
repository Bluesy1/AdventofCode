package day_01

import java.io.File

fun part1(input: List<String>) {
    var current = 0
    var max = 0
    for (line in input) {
        if (line == ""){
            if (current > max) {
                max = current
            }
            current = 0
            continue
        }
        current += Integer.parseInt(line)
    }
    print(max)
}

fun part2(input: List<String>) {
    print(input)
}

fun main(){
    val inputFile = File("src/inputs/Day_01.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}