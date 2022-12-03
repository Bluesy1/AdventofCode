package day_03

import java.io.File

val map = mapOf(
    'a' to 1, 'b' to 2, 'c' to 3, 'd' to 4, 'e' to 5, 'f' to 6, 'g' to 7, 'h' to 8, 'i' to 9, 'j' to 10, 'k' to 11,
    'l' to 12, 'm' to 13, 'n' to 14, 'o' to 15, 'p' to 16, 'q' to 17, 'r' to 18, 's' to 19, 't' to 20, 'u' to 21,
    'v' to 22, 'w' to 23, 'x' to 24, 'y' to 25, 'z' to 26, 'A' to 27, 'B' to 28, 'C' to 29, 'D' to 30, 'E' to 31,
    'F' to 32, 'G' to 33, 'H' to 34, 'I' to 35, 'J' to 36, 'K' to 37, 'L' to 38, 'M' to 39, 'N' to 40, 'O' to 41,
    'P' to 42, 'Q' to 43, 'R' to 44, 'S' to 45, 'T' to 46, 'U' to 47, 'V' to 48, 'W' to 49, 'X' to 50, 'Y' to 51,
    'Z' to 52
)

fun part1(input: List<String>) {
    val result = mutableListOf<Int>()
    for (line in input){
        val first = line.subSequence(0, line.length / 2)
        val second = line.subSequence(line.length / 2, line.length)
        for (char in first) {
            if (second.contains(char)) {
                result.add(map[char]!!)
                break
            }
        }
    }
    print("The sum of the results is: ${result.sum()}")
}

fun part2(input: MutableList<String>) {
    val result = mutableListOf<Int>()
    while (input.size > 0){
        val first = input.removeFirst().toSet()
        val second = input.removeFirst().toSet()
        val third = input.removeFirst().toSet()
        result.add(map[first.intersect(second).intersect(third).first()]!!)
    }
    print("The sum of the results is: ${result.sum()}")
}

fun main(){
    val inputFile = File("src/inputs/Day_03.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines().toMutableList())
}