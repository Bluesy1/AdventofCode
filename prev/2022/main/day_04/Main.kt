package day_04_2022

import java.io.File

fun part1(input: List<String>) {
    val res = input.stream().mapToInt {
        val range1str = it.split(',')[0]
        val range2str = it.split(',')[1]
        val range1 = (range1str.split('-')[0].toInt()..range1str.split('-')[1].toInt()).toSet()
        val range2 = (range2str.split('-')[0].toInt()..range2str.split('-')[1].toInt()).toSet()
        if (range1.containsAll(range2) || range2.containsAll(range1)) 1 else 0
    }.sum()
    print("The number of contained assignment pairs is $res")
}

fun part2(input: List<String>) {
    val res = input.stream().mapToInt {
        val range1str = it.split(',')[0]
        val range2str = it.split(',')[1]
        val range1 = (range1str.split('-')[0].toInt()..range1str.split('-')[1].toInt()).toSet()
        val range2 = (range2str.split('-')[0].toInt()..range2str.split('-')[1].toInt()).toSet()
        if (range1.intersect(range2).isNotEmpty()) 1 else 0
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