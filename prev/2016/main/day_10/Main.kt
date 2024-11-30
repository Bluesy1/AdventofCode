package day_10_2016

import java.io.File

fun part1(input: List<String>) {
    val controller = BotController()
    input.forEach { controller.parseOp(it) }
    print("The bot the compares 17 and 61 is ${controller.trace(17, 61)}")
}

fun part2(input: List<String>) {
    val controller = BotController()
    input.forEach { controller.parseOp(it) }
    controller.run()
    (0..2).map { controller.outputs[it]?.get(0) ?: 1 }
        .reduce(Int::times)
        .let { print("The product of the first three outputs is $it") }
}

fun main(){
    val inputFile = File("2016/inputs/Day_10.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}