package day_14_2022

import java.io.File

tailrec fun placeSandPart1(map: HashMap<Pair<Int, Int>, Boolean>, from: Pair<Int, Int>, xBounds: Pair<Int, Int>, yBound: Int): Boolean {
    return if ((from.second > yBound) || (from.first !in xBounds.first..xBounds.second)) {
        false
    } else if (map[from.first to from.second + 1] != true) {
        placeSandPart1(map, from.first to from.second + 1, xBounds, yBound)
    } else if (map[from.first - 1 to from.second + 1] != true) {
        placeSandPart1(map, from.first - 1 to from.second + 1, xBounds, yBound)
    } else if (map[from.first + 1 to from.second + 1] != true) {
        placeSandPart1(map, from.first + 1 to from.second + 1, xBounds, yBound)
    } else {
        map[from.first to from.second] = true
        true
    }
}

fun part1(input: List<String>) {
    val cave = hashMapOf<Pair<Int, Int>, Boolean>()
    for (line in input) {
        val points = line.split(" -> ").map { it.split(",")[0] to it.split(",")[1] }
        for ((start, finish) in points.zipWithNext()) {
            val (x1, y1) = start
            val (x2, y2) = finish
            if (x1 == x2) {
                for (y in y1.toInt().coerceAtMost(y2.toInt())..y2.toInt().coerceAtLeast(y1.toInt())) {
                    cave[x1.toInt() to y] = true
                }
            } else {
                for (x in x1.toInt().coerceAtMost(x2.toInt())..x2.toInt().coerceAtLeast(x1.toInt())) {
                    cave[x to y1.toInt()] = true
                }
            }
        }
    }
    val xBounds = cave.keys.minOfOrNull { it.first }!! to cave.keys.maxOfOrNull { it.first }!!
    val yBound = cave.keys.maxOfOrNull { it.second }!!
    val sandSource = 500 to 0
    var sand = 0
    while (placeSandPart1(cave, sandSource, xBounds, yBound)) {
        sand++
    }
    print("The number of units of sand that rest before sand falls into the abyss is $sand")
}

tailrec fun placeSandPart2(map: HashMap<Pair<Int, Int>, Boolean>, from: Pair<Int, Int>, yBound: Int, startLocation: Pair<Int, Int> = 500 to 0): Boolean {
    return if ((from.second == yBound - 1)) {
        map[from.first to from.second] = true
        true
    } else if (map[from.first to from.second + 1] != true) {
        placeSandPart2(map, from.first to from.second + 1, yBound, startLocation)
    } else if (map[from.first - 1 to from.second + 1] != true) {
        placeSandPart2(map, from.first - 1 to from.second + 1, yBound, startLocation)
    } else if (map[from.first + 1 to from.second + 1] != true) {
        placeSandPart2(map, from.first + 1 to from.second + 1, yBound, startLocation)
    } else if (map[from.first to from.second] == true) {
        false
    } else {
        map[from.first to from.second] = true
        true
    }
}

fun part2(input: List<String>) {
    val cave = hashMapOf<Pair<Int, Int>, Boolean>()
    for (line in input) {
        val points = line.split(" -> ").map { it.split(",")[0] to it.split(",")[1] }
        for ((start, finish) in points.zipWithNext()) {
            val (x1, y1) = start
            val (x2, y2) = finish
            if (x1 == x2) {
                for (y in y1.toInt().coerceAtMost(y2.toInt())..y2.toInt().coerceAtLeast(y1.toInt())) {
                    cave[x1.toInt() to y] = true
                }
            } else {
                for (x in x1.toInt().coerceAtMost(x2.toInt())..x2.toInt().coerceAtLeast(x1.toInt())) {
                    cave[x to y1.toInt()] = true
                }
            }
        }
    }
    val yBound = cave.keys.maxOfOrNull { it.second }!! + 2
    val sandSource = 500 to 0
    var sand = 0
    while (placeSandPart2(cave, sandSource, yBound, 500 to 0)) {
        sand++
    }
    print("The number of units of sand that rest before sand can't leave the source is $sand")
}

fun main(){
    val inputFile = File("2022/inputs/Day_14.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}