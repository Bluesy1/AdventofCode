package day_01_2016

import java.io.File
import kotlin.math.abs

enum class Facing {
    NORTH, EAST, SOUTH, WEST
}

fun part1(input: List<String>) {
    var horizontalPos =0
    var verticalPos = 0
    var facing = Facing.NORTH
    for ((direction, distance) in input.map { Pair(it[0], it.substring(1).toInt()) }) {
        when (direction) {
            'R' -> facing = when (facing) {
                Facing.NORTH -> Facing.EAST
                Facing.EAST -> Facing.SOUTH
                Facing.SOUTH -> Facing.WEST
                Facing.WEST -> Facing.NORTH
            }
            'L' -> facing = when (facing) {
                Facing.NORTH -> Facing.WEST
                Facing.WEST -> Facing.SOUTH
                Facing.SOUTH -> Facing.EAST
                Facing.EAST -> Facing.NORTH
            }
        }
        when (facing) {
            Facing.NORTH -> verticalPos += distance
            Facing.SOUTH -> verticalPos -= distance
            Facing.EAST -> horizontalPos += distance
            Facing.WEST -> horizontalPos -= distance
        }
    }
    print("Distance: ${Math.abs(horizontalPos) + Math.abs(verticalPos)}")
}


fun part2(input: List<String>) {
    var horizontalPos = 0
    var verticalPos = 0
    var facing = Facing.NORTH
    val visited = mutableMapOf<Pair<Int,Int>, Byte>()
    for ((direction, distance) in input.map { Pair(it[0], it.substring(1).toInt()) }) {
        when (direction) {
            'R' -> facing = when (facing) {
                Facing.NORTH -> Facing.EAST
                Facing.EAST -> Facing.SOUTH
                Facing.SOUTH -> Facing.WEST
                Facing.WEST -> Facing.NORTH
            }
            'L' -> facing = when (facing) {
                Facing.NORTH -> Facing.WEST
                Facing.WEST -> Facing.SOUTH
                Facing.SOUTH -> Facing.EAST
                Facing.EAST -> Facing.NORTH
            }
        }
        when (facing) {
            Facing.NORTH -> {
                for (j in 1..distance) {
                    verticalPos++
                    if (visited.containsKey(Pair(horizontalPos, verticalPos))) {
                        print("Distance: ${abs(horizontalPos) + abs(verticalPos)}")
                        return
                    }
                    visited[Pair(horizontalPos, verticalPos)] = 1
                }
            }
            Facing.SOUTH -> {
                for (j in 1..distance) {
                    verticalPos--
                    if (visited.containsKey(Pair(horizontalPos, verticalPos))) {
                        print("Distance: ${abs(horizontalPos) + abs(verticalPos)}")
                        return
                    }
                    visited[Pair(horizontalPos, verticalPos)] = 1
                }
            }
            Facing.EAST -> {
                for (j in 1..distance) {
                    horizontalPos++
                    if (visited.containsKey(Pair(horizontalPos, verticalPos))) {
                        print("Distance: ${abs(horizontalPos) + abs(verticalPos)}")
                        return
                    }
                    visited[Pair(horizontalPos, verticalPos)] = 1
                }
            }
            Facing.WEST -> {
                for (j in 1..distance) {
                    horizontalPos--
                    if (visited.containsKey(Pair(horizontalPos, verticalPos))) {
                        print("Distance: ${abs(horizontalPos) + abs(verticalPos)}")
                        return
                    }
                    visited[Pair(horizontalPos, verticalPos)] = 1
                }
            }
        }
    }
}

fun main(){
    val inputFile = File("2016/inputs/Day_01.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines()[0].split(", "))
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines()[0].split(", "))
}