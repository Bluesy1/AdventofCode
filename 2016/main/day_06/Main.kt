package day_06_2016

import java.io.File

fun part1(input: List<String>) {
    val length = input[0].length
    val maps = mutableListOf<HashMap<Char, Int>>()
    for (i in 0 until length) {
        maps.add(HashMap())
    }
    for (line in input) {
        for (i in 0 until length){
            maps[i][line[i]] = maps[i].getOrDefault(line[i], 0) + 1
        }
    }
    print("The message is ${maps.map<Map<Char,Int>,Char>{ map -> map.maxBy { it.value }.key}.joinToString("")}")
}


fun part2(input: List<String>) {
    val length = input[0].length
    val maps = mutableListOf<HashMap<Char, Int>>()
    for (i in 0 until length) {
        maps.add(HashMap())
    }
    for (line in input) {
        for (i in 0 until length){
            maps[i][line[i]] = maps[i].getOrDefault(line[i], 0) + 1
        }
    }
    print("The message is ${maps.map<Map<Char,Int>,Char>{ map -> map.minBy { it.value }.key}.joinToString("")}")
}

fun main(){
    val inputFile = File("2016/inputs/Day_06.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}