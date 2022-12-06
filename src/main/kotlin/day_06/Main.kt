package day_06

import java.io.File

fun part1(input: String) {
    val queue = mutableListOf<Char>()
    var count = 0
    for (char in input) {
        count++
        if (queue.size < 4) {
            queue.add(char)
            continue
        } else {
            queue.add(char)
            queue.removeAt(0)
            if (queue.toSet().size == 4) {
                print("The maker is at position $count")
                break
            }
        }
    }
}

fun part2(input: String) {
    val queue = mutableListOf<Char>()
    var count = 0
    for (char in input) {
        count++
        if (queue.size < 14) {
            queue.add(char)
            continue
        } else {
            queue.add(char)
            queue.removeAt(0)
            if (queue.toSet().size == 14) {
                print("The maker is at position $count")
                break
            }
        }
    }
}

fun main(){
    val inputFile = File("src/inputs/Day_06.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines()[0])
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines()[0])
}