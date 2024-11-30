package day_04_2016

import java.io.File

fun part1(input: List<String>) {
    input.stream().mapToInt {
        val chars = it.substringBeforeLast("[")
            .split("-")
            .dropLast(1)
            .joinToString("")
        val sectorId = it.substringBeforeLast("[")
            .split("-")
            .last()
            .toInt()
        val checksum = it.substringAfterLast("[")
            .substringBeforeLast("]")
        val charCount = chars.groupingBy { it1 -> it1 }
            .eachCount()
            .toSortedMap()
        var flag = true
        for (i in 0 until 5){
            val max = charCount.maxBy { it2 -> it2.value }
            if (max.key !in checksum) {
                flag = false
                break
            }
            charCount.remove(max.key)
        }
        if (flag) sectorId else 0
    }.sum().let { print("The sum of valid sector IDs is $it") }

}


fun part2(input: List<String>) {
        input.stream().map {
        val chars = it.substringBeforeLast("[")
            .split("-")
            .dropLast(1)
            .joinToString("")
        val sectorId = it.substringBeforeLast("[")
            .split("-")
            .last()
            .toInt()
        val checksum = it.substringAfterLast("[")
            .substringBeforeLast("]")
        val charCount = chars.groupingBy { it1 -> it1 }
            .eachCount()
            .toSortedMap()
        var flag = true
        for (i in 0 until 5){
            val max = charCount.maxBy { it2 -> it2.value }
            if (max.key !in checksum) {
                flag = false
                break
            }
            charCount.remove(max.key)
        }
        if (flag) {
            val encryptedName = it.substringBeforeLast("[")
                .split("-")
                .dropLast(1)
                .joinToString(" ")
            encryptedName.toCharArray().map {
                    it1 -> if (it1 == ' ') ' ' else (((it1.code + sectorId - 'a'.code) % 26) + 'a'.code).toChar()
            }.joinToString("") to sectorId
        } else Pair("", 0)
    }
        .filter { it.first == "northpole object storage" }
        .findFirst()
        .let { print("The sector ID of the room where North Pole objects are stored is ${it.get().second}") }

}

fun main(){
    val inputFile = File("2016/inputs/Day_04.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}