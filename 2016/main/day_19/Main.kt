package day_19_2016

import java.io.File

fun part1(input: Int) {
    val elves = (1..input).toMutableList()
    var counter = false
    val toRemove = mutableSetOf<Int>()
    while (elves.size > 1) {
        for (i in elves){
            if (counter) {
                toRemove += i
            }
            counter = !counter
        }
        elves.removeAll(toRemove)
        toRemove.clear()
    }
    print("The winning elf is ${elves.first()}")
}
fun part2(input: Int) {
    val left = ArrayDeque((1..(input/2)).toList())
    val right = ArrayDeque((((input/2) + 1)..input).toList())

    while (left.size + right.size > 1) {
            if (left.size > right.size) left.removeLast()
            else right.removeFirst()

            right.addLast(left.removeFirst())
            left.addLast(right.removeFirst())
        }

    print("The winning elf is ${left.firstOrNull() ?: right.first()}")
}


fun main(){
    val input = File("2016/inputs/Day_19.txt").readText().toInt()
    print("\n----- Part 1 -----\n")
    part1(input)
    print("\n----- Part 2 -----\n")
    part2(input)
}