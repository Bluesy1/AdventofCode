package template

import java.io.File

fun part1(input: List<String>) {
    print("Part 1: $input")
}

fun part2(input: List<String>) {
    print("Part 2: $input")
}

fun main(){
    val inputFile = File("2022/inputs/input.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}