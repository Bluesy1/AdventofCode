package day_11_2022

import java.io.File
import java.math.BigInteger

fun part1(input: List<String>) {
    val monkeys = mutableListOf<Monkey>()
    for (monkey in input.chunked(7)) {
        val items = monkey[1].substring(18).split(", ").map{ BigInteger(it) }.toMutableList()
        val op = monkey[2].toCharArray()[23] == '*'
        val opBy = monkey[2].split(" ").last().toIntOrNull()
        val condition = monkey[3].split(" ").last().toInt()
        val toOnFalse = monkey[4].split(" ").last().toInt()
        val toOnTrue = monkey[5].split(" ").last().toInt()
        monkeys.add(Monkey(items, op, opBy, condition, toOnFalse, toOnTrue, true))
    }
    for (i in 0..19) {
        for (monkey in monkeys) {
            monkey.inspectItems(monkeys)
        }
    }
    val sortedMonkeys = monkeys.sortedByDescending { it.inspectedTimes }
    print("The monkey business is ${sortedMonkeys[0].inspectedTimes * sortedMonkeys[1].inspectedTimes}")
}
// 6300 wrong

fun part2(input: List<String>) {
    val monkeys = mutableListOf<Monkey>()
    for (monkey in input.chunked(7)) {
        val items = monkey[1].substring(18).split(", ").map{ BigInteger(it) }.toMutableList()
        val op = monkey[2].toCharArray()[23] == '*'
        val opBy = monkey[2].split(" ").last().toIntOrNull()
        val condition = monkey[3].split(" ").last().toInt()
        val toOnFalse = monkey[4].split(" ").last().toInt()
        val toOnTrue = monkey[5].split(" ").last().toInt()
        monkeys.add(Monkey(items, op, opBy, condition, toOnFalse, toOnTrue, false))
    }
    val superMod = monkeys.map(Monkey::condition).reduce(Int::times)
    for (i in 0 until 10000) {
        for (monkey in monkeys) {
            monkey.inspectItems(monkeys, superMod)
        }
    }
    val sortedMonkeys = monkeys.sortedByDescending { it.inspectedTimes }.subList(0, 2)
    print("The monkey business is ${sortedMonkeys[0].inspectedTimes * sortedMonkeys[1].inspectedTimes}")
}
// 259605321 is wrong
fun main(){
    val inputFile = File("2022/inputs/Day_11.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}