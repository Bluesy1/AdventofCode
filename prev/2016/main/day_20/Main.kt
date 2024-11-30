package day_20_2016

import java.io.File

fun part1(input: List<UIntRange>) {
    var inputs = input.sortedBy { it.first }
    var index: UInt = 0u
    while (index < inputs.maxBy { it.last }.last) {
        index = inputs[0].last + 1u
        inputs = inputs.filter { it.last > index }
        if (index < inputs[0].first) {
            println(index)
            break
        }
    }
}
fun part2(input: List<UIntRange>) {
    input.sortedBy { it.first }
        .map { it.first to it.last }
        .fold(emptyList<Pair<UInt, UInt>>()) { ranges, range ->
            if (ranges.isEmpty() || (ranges.last().second < (range.first - 1u))) {
                ranges + range
            } else {
                ranges.last().let { last ->
                    ranges.dropLast(1) + (last.first to maxOf(range.second, last.second))
                }
            }
        }.sumOf { it.second - it.first + 1u }
        .let { print("The number of valid IPs is ${4294967296u - it}") }
    // too high
    // 4279991501
    // 4206688754
    // 102

    // 100 wrong (not too high, so close?)
}

private operator fun UIntRange.component2(): UInt {
    return last
}

private operator fun UIntRange.component1(): UInt {
    return first
}


fun main(){
    val inputFile = File("2016/inputs/Day_20.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines().map { it.split('-')[0].toUInt()..it.split('-')[1].toUInt() })
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines().map { it.split('-')[0].toUInt()..it.split('-')[1].toUInt() })
}