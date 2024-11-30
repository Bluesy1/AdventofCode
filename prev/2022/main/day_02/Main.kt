package day_02_2022

import java.io.File

fun part1(input: List<String>) {
    print("The score is ${input.stream().mapToInt { 
        when (it) {
            "A X" -> 3 +1
            "A Y" -> 6+2
            "A Z" -> 0+3
            "B X" -> 0+1
            "B Y" -> 3+2
            "B Z" -> 6+3
            "C X" -> 6+1
            "C Y" -> 0+2
            "C Z" -> 3+3
            else -> {
                throw IllegalArgumentException("Invalid input: $it")
            }
        }
    }.sum()}")
}

fun part2(input: List<String>) {
        print("The score is ${input.stream().mapToInt { 
        when (it) {
            "A X" -> 0+3
            "A Y" -> 3+1
            "A Z" -> 6+2
            "B X" -> 0+1
            "B Y" -> 3+2
            "B Z" -> 6+3
            "C X" -> 0+2
            "C Y" -> 3+3
            "C Z" -> 6+1
            else -> {
                throw IllegalArgumentException("Invalid input: $it")
            }
        }
    }.sum()}")
}

fun main(){
    val inputFile = File("src/inputs/Day_02.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}