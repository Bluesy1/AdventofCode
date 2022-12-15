package day_15_2022

import java.io.File
import kotlin.math.abs
import kotlin.math.absoluteValue

val inputRe = Regex("[\\w\\s]*x=(?<xPos>-?\\d+), y=(?<yPos>-?\\d+):[\\w\\s]*x=(?<xTarget>-?\\d+), y=(?<yTarget>-?\\d+)")

fun part1(input: List<String>) {
    val parsed = input.mapNotNull(inputRe::find)
        .map { it.destructured.toList().map(String::toInt) }
        .map {
            (xPos, yPos, xTarget, yTarget) -> (xPos to yPos) to (xTarget to yTarget)
        }
    val row2000000 = mutableSetOf<Int>()
    parsed.forEach {
        val (xPos, yPos) = it.first
        val (xTarget, yTarget) = it.second
        val dst = (xPos - xTarget).absoluteValue + (yPos-yTarget).absoluteValue
        val dstToY = (yPos - 2000000).absoluteValue
        if (dstToY <= dst) {
            val width = dst - dstToY
            row2000000.addAll((xPos - width)..(xPos + width))
        }
        if (yTarget == 2000000) {
            row2000000.remove(xTarget)
        }
    }
    print("The number of positions that cannot contain a beacon is ${row2000000.size}")
}

fun part2(input: List<String>) {
    val max = 4000000
    val sensors = input.mapNotNull(inputRe::find)
        .map { it.destructured.toList().map(String::toInt) }
        .map {
            (xPos, yPos, xTarget, yTarget) -> (xPos to yPos) to (xTarget to yTarget)
        }
    val safeRangesPerLine = Array<MutableList<IntRange>>(max + 1) { mutableListOf() }
    sensors.forEach { (sensor, beacon) ->
        for (y in 0..max) {
            val dst = (sensor.first - beacon.first).absoluteValue + (sensor.second-beacon.second).absoluteValue
            val dstToY = abs(sensor.second - y)
            val width = dst - dstToY
            if (width > 0) {
                safeRangesPerLine[y].add(sensor.first - width..sensor.first + width)
            }
        }
    }
    safeRangesPerLine.forEachIndexed { y, ranges ->
        val sortedRanges = ranges.sortedBy { it.first }
        var highest = sortedRanges.first().last
        sortedRanges.drop(1).map {
            if (it.first > highest) {
                print("The tuning frequency is ${(it.first - 1).toBigInteger() * 4000000.toBigInteger() + y.toBigInteger()}")
            }
            if (it.last > highest) {
                highest = it.last
            }
        }
    }
}

fun main(){
    val inputFile = File("2022/inputs/Day_15.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}