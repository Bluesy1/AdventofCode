package day_02_2016

import java.io.File

fun part1(input: List<String>) {
    val keyPad = arrayOf(
        arrayOf(1, 2, 3),
        arrayOf(4, 5, 6),
        arrayOf(7, 8, 9)
    )
    var xPos = 1
    var yPos = 1
    var code = ""
    for (line in input) {
        for (c in line) {
            when (c) {
                'U' -> yPos = Math.max(0, yPos - 1)
                'D' -> yPos = Math.min(2, yPos + 1)
                'L' -> xPos = Math.max(0, xPos - 1)
                'R' -> xPos = Math.min(2, xPos + 1)
            }
        }
        code += keyPad[yPos][xPos]
    }
    print("The bathroom code is $code")
}

fun part2(input: List<String>) {
    val keyPad = arrayOf(
        arrayOf(0, 0, 1, 0, 0),
        arrayOf(0, 2, 3, 4, 0),
        arrayOf(5, 6, 7, 8, 9),
        arrayOf(0, 'A', 'B', 'C', 0),
        arrayOf(0, 0, 'D', 0, 0)
    )
    var xPos = 0
    var yPos = 2
    var code = ""
    for (line in input) {
        for (c in line) {
            when (c) {
                'U' -> yPos = Math.max(0, yPos - 1)
                'D' -> yPos = Math.min(4, yPos + 1)
                'L' -> xPos = Math.max(0, xPos - 1)
                'R' -> xPos = Math.min(4, xPos + 1)
            }
            if (keyPad[yPos][xPos] == 0) {
                when (c) {
                    'U' -> yPos++
                    'D' -> yPos--
                    'L' -> xPos++
                    'R' -> xPos--
                }
            }
        }
        code += keyPad[yPos][xPos]
    }
    print("The bathroom code is $code")
}

fun main(){
    val inputFile = File("2016/inputs/Day_02.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}