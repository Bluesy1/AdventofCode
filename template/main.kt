import java.io.File

fun part1(input: List<String>) {
    print(input)
}

fun part2(input: List<String>) {
    print(input)
}

fun main(){
    val inputFile = File("../input.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}