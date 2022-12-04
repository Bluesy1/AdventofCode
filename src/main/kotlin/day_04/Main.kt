package day_04

import java.io.File

fun part1(input: List<String>) {
    val res = input.stream().mapToInt {
        val range1 = it.split(',')[0]
        val range2 = it.split(',')[1]
        val start1 = range1.split('-')[0].toInt()
        val end1 = range1.split('-')[1].toInt()
        val start2 = range2.split('-')[0].toInt()
        val end2 = range2.split('-')[1].toInt()
        if (start1 <= start2 && end1 >= end2) {
            1
        } else if (start2 <= start1 && end2 >= end1) {
            1
        } else {
            0
        }
    }.sum()
    print("The number of contained assignment pairs is $res")
}

fun part2(input: List<String>) {
    val res = input.stream().mapToInt {
        val range1 = it.split(',')[0]
        val range2str = it.split(',')[1]
        val range2 = range2str.split('-')[0].toInt()..range2str.split('-')[1].toInt()
        for (i in range1.split('-')[0].toInt()..range1.split('-')[1].toInt()) {
            if (i in range2) {
                return@mapToInt 1
            }
        }
        0
    }.sum()
    print("The number of overlapping assignment pairs is $res")
}

fun main(){
    val inputFile = File("src/inputs/Day_04.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}