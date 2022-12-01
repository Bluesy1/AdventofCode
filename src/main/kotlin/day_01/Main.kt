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
        var current = 0
    var max = 0
    var second = 0
    var third = 0
    for (line in input) {
        if (line == ""){
            if (current > max) {
                third = second
                second = max
                max = current
            } else if (current > second) {
                third = second
                second = current
            } else if (current > third) {
                third = current
            }
            current = 0
            continue
        }
        current += Integer.parseInt(line)
    }
    print(max + second + third)
}

fun main(){
    val inputFile = File("src/inputs/Day_01.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}