package day_07_2016

import java.io.File

fun part1(input: List<String>) {
    input.filter {
        val hypernets = it.split('[').drop(1).map { it1 -> it1.split(']')[0] }.any {
            it1 -> it1.windowed(4).any { it2 -> it2[0] == it2[3] && it2[1] == it2[2] && it2[0] != it2[1] }
        }
        if (hypernets) false else {
            it.split('[').map {
                    it1 ->
                val split = it1.split(']')
                if (split.size == 1) split[0] else split[1]
            }.any { it1 -> it1.windowed(4).any { it2 -> it2[0] == it2[3] && it2[1] == it2[2] && it2[0] != it2[1] } }
        }
    }.count().let { print("The number of TLS addresses is $it") }
}

fun part2(input: List<String>) {
    input.filter {
        val BABs = it.split('[').map {
                    it1 ->
                val split = it1.split(']')
                if (split.size == 1) split[0] else split[1]
            }.map { it1 -> it1.windowed(3)
                .filter { it2 -> it2[0] == it2[2] && it2[0] != it2[1] }
            }
            .flatten()
            .map { aba -> "${aba[1]}${aba[0]}${aba[1]}" }
            .toSet()
        it.split('[')
            .drop(1)
            .map { it1 -> it1.split(']')[0] }
            .map { it1 -> it1.windowed(3) }
            .flatten()
            .any { it1 -> it1 in BABs }
    }.count().let { print("The number of SSL addresses is $it") }
}

fun main(){
    val inputFile = File("2016/inputs/Day_07.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}