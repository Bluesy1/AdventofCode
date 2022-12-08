package day_12_2016

import java.io.File
fun valFromIntOrRegister(value: String, registers: MutableMap<Char, Int>): Int {
    return value.toIntOrNull() ?: registers[value[0]]!!
}

fun day12(input: List<String>, initialC: Int = 0) {
    val registers = mutableMapOf('a' to 0, 'b' to 0, 'c' to initialC, 'd' to 0)
    var ptr = 0
    while (ptr < input.size){
        val instruction = input[ptr]
        val callable = instruction.slice(0..2)
        val args = instruction.slice(4 until instruction.length).split(" ")
        if (callable == "cpy") {
            registers[args[1][0]] = valFromIntOrRegister(args[0], registers)
            ptr++
            continue
        } else if (callable == "inc") {
            registers[args[0][0]] = registers[args[0][0]]!! + 1
            ptr++
            continue
        } else if (callable == "dec") {
            registers[args[0][0]] = registers[args[0][0]]!! - 1
            ptr++
            continue
        } else if (callable == "jnz") {
            val arg1 = valFromIntOrRegister(args[0], registers)
            if (arg1 == 0) {
                ptr++
                continue
            }
            ptr += args[1].toInt()
        }
    }
    print("The value of register a is ${registers['a']}")
}

fun main(){
    val inputFile = File("2016/inputs/Day_12.txt")
    print("\n----- Part 1 -----\n")
    day12(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    day12(inputFile.readLines(), initialC = 1)
}