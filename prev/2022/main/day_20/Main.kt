package day_20_2022

import java.io.File

data class Mover(var uuid: Int, var value: Long)

fun solve(part1: Boolean, input: List<Long>): String {
    val movers: MutableList<Mover> = ArrayList()
    for ((counter, num) in input.withIndex()) {
        val value: Long = num
        movers.add(Mover(counter, if (part1) value else value * 811589153))
    }
    for (k in 0 until if (part1) 1 else 10) {
        for (i in movers.indices) {
            lateinit var mover: Mover
            var loc = 0
            for (j in movers.indices) {
                if (movers[j].uuid == i + 10000 * k) {
                    mover = movers[j]
                    loc = j
                }
            }
            movers.removeAt(loc)
            val rotate = (mover.value % movers.size).toInt()
            movers.add(((loc + rotate) % movers.size + movers.size) % movers.size, mover)
        }
        for (i in movers.indices) movers[i].uuid = movers[i].uuid + 10000
    }
    var offset = 0
    for (j in movers.indices) {
        if (movers[j].value == 0L) offset = j
    }
    return "" + (movers[(offset + 1000) % movers.size].value + movers[(offset + 2000) % movers.size].value + movers[(offset + 3000) % movers.size].value)
}

fun part1(input: List<Long>) {
    print("The sum of the numbers in the 1000th, 2000th, and 3000th positions after 0 is ${solve(true, input)}")
}

fun part2(input: List<Long>) {
    print("The sum of the three real numbers in the 1000th, 2000th, and 3000th positions after 0 is ${solve(false, input)}")
}

fun main(){
    val inputFile = File("2022/inputs/Day_20.txt")
    val input = inputFile.readLines().map(String::toLong)
    print("\n----- Part 1 -----\n")
    part1(input)
    print("\n----- Part 2 -----\n")
    part2(input)
}