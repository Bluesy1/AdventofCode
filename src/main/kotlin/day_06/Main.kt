package day_06

import java.io.File

fun part1(input: String) {
    print("The marker is at position ${input.windowed(4).indexOfFirst { it.toSet().size == 4 } + 4}")
}

fun part2(input: String) {
    print("The marker is at position ${input.windowed(14).indexOfFirst { it.toSet().size == 14 } + 14}")
}

fun main(){
    val inputFile = File("src/inputs/Day_06.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines()[0])
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines()[0])
}