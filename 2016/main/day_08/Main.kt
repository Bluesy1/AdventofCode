package day_08_2016

import java.io.File


fun main(){
    val inputFile = File("2016/inputs/Day_08.txt")
    val input = inputFile.readLines()
    print("\n----- Part 1 -----\n")
    val board = (0..49).map { (0..5).map { false }.toMutableList() }.toMutableList()
    for (instruction in input) {
        if (instruction.startsWith("rect")){
            val (x, y) = instruction.substring(5).split("x").map { it.toInt() }
            for (i in 0 until x) {
                for (j in 0 until y) {
                    board[i][j] = true
                }
            }
        } else {
            val (index, amount) = instruction.substring(instruction.indexOf("=") + 1).split(" by ").map { it.toInt() }
            if (instruction.startsWith("rotate row")) {
                val row = (0..49).map { board[it][index] }.toMutableList()
                val newRow = (0..49).map { row[(it - amount + 50) % 50] }.toMutableList()
                for (i in 0..49) {
                    board[i][index] = newRow[i]
                }
            } else {
                val column = board[index]
                val newColumn = (0..5).map { column[(it - amount + 6) % 6] }.toMutableList()
                board[index] = newColumn
            }
        }
    }
    board.map {it.count { it } }.sum().let { print("$it pixels should be lit") }
    print("\n----- Part 2 -----\n")
    (0..5).map { y ->
        (0..49).map { x ->
            if (board[x][y]) "#" else " "
        }.joinToString("")
    }.forEach { println(it) }
}