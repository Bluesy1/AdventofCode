package day_18_2022

import java.io.File

fun Triple<Int, Int, Int>.adjacent(): List<Triple<Int, Int, Int>> {
    val (a,b,c) = this
    val res = mutableListOf<Triple<Int, Int, Int>>()
    res.add(Triple(a+1,b,c))
    res.add(Triple(a-1,b,c))
    res.add(Triple(a,b+1,c))
    res.add(Triple(a,b-1,c))
    res.add(Triple(a,b,c+1))
    res.add(Triple(a,b,c-1))
    return res
}

fun part1(input: List<String>) {
    val map = input.map {
        val (x, y, z) = it.split(",")
        Triple(x.toInt(), y.toInt(), z.toInt())
    }
    var size = 0
    val scanned = mutableSetOf<Triple<Int, Int, Int>>()
    for (cube in map) {
        size += 6
        for (adj in cube.adjacent()) {
            if (adj in scanned) {
                size -= 2
            }
        }
        scanned.add(cube)
    }
    print("The surface area is $size")
}

fun part2(input: List<String>) {
    val map = input.map {
        val (x, y, z) = it.split(",")
        Triple(x.toInt(), y.toInt(), z.toInt())
        }
    var size = 0
    val scanned = mutableSetOf<Triple<Int, Int, Int>>()
    for (cube in map) {
        size += 6
        for (adj in cube.adjacent()) {
            if (adj in scanned) {
                size -= 2
            }
        }
        scanned.add(cube)
    }
    val allPossible = mutableSetOf<Triple<Int,Int,Int>>()
    for (i in 0 until 20) {
        for (j in 0 until 20) {
            for (k in 0 until 20) {
                allPossible.add(Triple(i,j,k))
            }
        }
    }
    val empty = (allPossible - scanned).toMutableSet()
    val queue = ArrayDeque(listOf(Triple(0,0,0)))
    while(queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current in empty) {
            empty.remove(current)
            queue.addAll(current.adjacent())
        }
    }
    for (cube in empty) {
        size += 6
        for (adj in cube.adjacent()) {
            if (adj in scanned) {
                size -= 2
            }
        }
        scanned.add(cube)
    }
    print("The exterior surface area is $size")
}

fun main(){
    val inputFile = File("2022/inputs/Day_18.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}