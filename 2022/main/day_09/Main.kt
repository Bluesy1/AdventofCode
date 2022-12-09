package day_09_2022

import java.io.File
import kotlin.math.abs

fun day9(input: List<String>) {
    var headPos = 0 to 0
    var tailPos= 0 to 0
    val visited = mutableMapOf((0 to 0) to 1)
    val tailVisited = mutableMapOf((0 to 0) to 1)
    val lastTen = ArrayDeque((0..9).map { 0 to 0 }.toList())
    val inputs = input.map { it.split(' ')[0] to it.split(' ')[1].toInt() }
    for ((direction, amount) in inputs) {
        for (i in 1..amount) {
            when (direction) {
                "R" -> {
                    headPos = headPos.first + 1 to headPos.second
                    lastTen[0] = lastTen[0].first + 1 to lastTen[0].second
                }
                "L" -> {
                    headPos = headPos.first - 1 to headPos.second
                    lastTen[0] = lastTen[0].first - 1 to lastTen[0].second
                }
                "U" -> {
                    headPos = headPos.first to headPos.second + 1
                    lastTen[0] = lastTen[0].first to lastTen[0].second + 1
                }
                "D" -> {
                    headPos = headPos.first to headPos.second - 1
                    lastTen[0] = lastTen[0].first to lastTen[0].second - 1
                }
                }
            if (abs(headPos.first - tailPos.first) == 2 && headPos.second == tailPos.second) {
                tailPos =  tailPos.first + (if (headPos.first > tailPos.first) 1 else -1) to tailPos.second
            } else if (abs(headPos.second - tailPos.second) == 2 && headPos.first == tailPos.first) {
                tailPos =  tailPos.first to tailPos.second + (if (headPos.second > tailPos.second) 1 else -1)
            } else if (headPos.first != tailPos.first && headPos.second != tailPos.second && abs(headPos.first - tailPos.first) + abs(headPos.second - tailPos.second) > 2) {
                tailPos =  (tailPos.first + (if (headPos.first > tailPos.first) 1 else -1)) to (tailPos.second + (if (headPos.second > tailPos.second) 1 else -1))
            }
            for (j in 1..9) {
                val head = lastTen[j-1]
                val tail = lastTen[j]
                if (abs(head.first - tail.first) == 2 && head.second == tail.second) {
                    lastTen[j] =  tail.first + (if (head.first > tail.first) 1 else -1) to tail.second
                } else if (abs(head.second - tail.second) == 2 && head.first == tail.first) {
                    lastTen[j] =  tail.first to tail.second + (if (head.second > tail.second) 1 else -1)
                } else if (head.first != tail.first && head.second != tail.second && abs(head.first - tail.first) + abs(head.second - tail.second) > 2) {
                    lastTen[j] =  (tail.first + (if (head.first > tail.first) 1 else -1)) to (tail.second + (if (head.second > tail.second) 1 else -1))
                }
            }
            visited[tailPos] = visited.getOrDefault(tailPos, 0) + 1
            tailVisited[lastTen[9]] = visited.getOrDefault(lastTen[9], 0) + 1
        }
    }
    print("\n----- Part 1 -----\n")
    print("The tail visits ${visited.size} positions at least once")
    print("\n----- Part 2 -----\n")
    print("The end of the tail visits ${tailVisited.size} positions at least once")
}

fun main(){
    day9(File("src/inputs/Day_09.txt").readLines())
}