package day_01

import java.io.File

fun part1(input: List<String>) {
    var current = 0
    var max = 0
    for (line in input) {
        if (line.isEmpty()){
            if (current > max) {
                max = current
            }
            current = 0
            continue
        }
        current += line.toInt()
    }
    print("Most calories: $max")
}

fun part2(input: List<String>) {
    val meals = mutableListOf(0,0,0)
    var current = 0
    for (line in input) {
        if (line.isEmpty()){
            meals.add(current)
            meals.sortDescending()
            meals.removeLast()
            current = 0
            continue
        }
        current += Integer.parseInt(line)
    }
    print("The sum of top 3 elves: ${meals.sum()}")
}

fun main(){
    val inputFile = File("src/inputs/Day_01.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}