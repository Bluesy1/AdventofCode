package day_05

import java.io.File

fun extractStacks(input: List<String>): MutableList<MutableList<Char>> {
    val boxes = input.subList(0, 8)
    val stacks = mutableListOf(mutableListOf<Char>(),
        mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf()
    )
    for (row in boxes) {
        for (i in 0..8) {
            if (row[(i*4)+ 1] != ' ') {
                stacks[i].add(row[(i*4)+ 1])
            }
        }
    }
    return stacks
}

fun part1(input: List<String>) {
    val stacks = extractStacks(input)
    val remaining = input.subList(10, input.size)
    for (row in remaining) {
        val instructions = row.splitToSequence(' ')
            .filter { it != " " && it.length in 1..2 && it != "to" }
            .map { it.trim().toInt() }
            .toList()
        for (i in 0..instructions[0]-1){
            stacks[instructions[2]-1].add(0, stacks[instructions[1]-1].removeAt(0))
        }

    }
    val result = stacks.map { it[0] }.joinToString("")
    println("Top row of boxes: $result")
}

fun part2(input: List<String>) {
    val stacks = extractStacks(input)
    val remaining = input.subList(10, input.size)
    for (row in remaining) {
        val instructions = row.splitToSequence(' ')
            .filter { it != " " && it.length in 1..2 && it != "to" }
            .map { it.trim().toInt() }
            .toList()
        val sub = stacks[instructions[1]-1].subList(0, instructions[0])
        for (item in sub.reversed()) {
            stacks[instructions[2] - 1].add(0, item)
            stacks[instructions[1]-1].remove(item)
        }
    }
    val result = stacks.map { it[0] }.joinToString("")
    println("Top row of boxes: $result")
}

fun main(){
    val inputFile = File("src/inputs/Day_05.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}