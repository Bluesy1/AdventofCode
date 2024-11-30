package day_10_2022

import java.io.File
import kotlin.math.abs

fun part1(input: List<String>) {
    val signalStrengths = mutableListOf<Int>()
    var signalStrength = 1
    for (signal in input) {
        if (signal.startsWith("addx")) {
            val arg = signal.split(" ")[1].toInt()
            signalStrengths.add(signalStrength)
            signalStrengths.add(signalStrength)
            signalStrength += arg
        } else {
            signalStrengths.add(signalStrength)
        }
    }
    var sum = 0
    sum += (signalStrengths[19] * 20)
    sum += (signalStrengths[59] * 60)
    sum += (signalStrengths[99] * 100)
    sum += (signalStrengths[139] * 140)
    sum += (signalStrengths[179] * 180)
    sum += (signalStrengths[219] * 220)
    print("The sum of the first six signal strengths is $sum")
}

fun part2(input: List<String>) {
    val signalStrengths = mutableListOf<Int>()
    var signalStrength = 1
    for (signal in input) {
        if (signal.startsWith("addx")) {
            val arg = signal.split(" ")[1].toInt()
            signalStrengths.add(signalStrength)
            signalStrengths.add(signalStrength)
            signalStrength += arg
        } else {
            signalStrengths.add(signalStrength)
        }
    }
    for (i in 0..239 step 40) {
        var line = ""
        for (j in i until i+40) {
            line += if (abs((signalStrengths[j] - (j-i))) % 40 < 2) "#" else "."
        }
        println(line)
    }
}

fun main(){
    val inputFile = File("2022/inputs/Day_10.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}