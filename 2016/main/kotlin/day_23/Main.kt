package day_23_2016

import java.io.File

import day_12_2016.valFromIntOrRegister
fun day23(input: MutableList<String>, initialA: Int) {
    val registers = mutableMapOf('a' to initialA, 'b' to 0, 'c' to 0, 'd' to 0)
    var ptr = 0
    val instructions = input
    while (ptr < instructions.size){
        val instruction = instructions[ptr]
        val callable = instruction.slice(0..2)
        val args = instruction.slice(4 until instruction.length).split(" ")
        if (callable == "cpy") {
            try {
                if (args[1][0] !in registers.keys) {
                    ptr++
                    continue
                }
                registers[args[1][0]] = valFromIntOrRegister(args[0], registers)
                ptr++
                continue
            } catch (e: Exception) {
                ptr++
                continue
            }
        } else if (callable == "inc") {
            if (args[0][0] !in registers.keys) {
                ptr++
                continue
            }
            registers[args[0][0]] = registers[args[0][0]]!! + 1
            ptr++
            continue
        } else if (callable == "dec") {
            if (args[0][0] !in registers.keys) {
                ptr++
                continue
            }
            registers[args[0][0]] = registers[args[0][0]]!! - 1
            ptr++
            continue
        } else if (callable == "jnz") {
            try {
                val arg1 = valFromIntOrRegister(args[0], registers)
                if (arg1 == 0) {
                    ptr++
                    continue
                }
                val toLoop = valFromIntOrRegister(args[1], registers)
                if (toLoop == -2) {
                    val prevInst = instructions[ptr - 1]
                    val dec = if (prevInst.substring(0..2) == "dec") prevInst[4] else instructions[ptr - 2][4]
                    val inc = if (prevInst.substring(0..2) == "inc") prevInst[4] else instructions[ptr - 2][4]
                    registers[inc] = registers[inc]!! + registers[dec]!!
                    registers[dec] = 0
                    ptr++
                    continue
                }
                if (toLoop == -5 && args[0] == "d"){
                    val loopAmount = arg1 * registers['b']!!
                    registers['c'] = 0
                    registers['d'] = 0
                    registers['a'] = registers['a']!! + loopAmount
                    ptr++
                    continue
                }
                ptr += toLoop
            } catch (e: Exception) {
                ptr++
                continue
            }
        } else if (callable == "tgl") {
            val arg = valFromIntOrRegister(args[0], registers)
            if (ptr + arg >= instructions.size || ptr + arg < 0) {
                ptr++
                continue
            }
            val toReplace = instructions[ptr + arg]
            when (toReplace.substring(0..2)) {
                "inc" -> instructions[ptr + arg] = toReplace.replace("inc", "dec")
                "dec" -> instructions[ptr + arg] = toReplace.replace("dec", "inc")
                "tgl" -> instructions[ptr + arg] = toReplace.replace("tgl", "inc")
                "jnz" -> instructions[ptr + arg] = toReplace.replace("jnz", "cpy")
                "cpy" -> instructions[ptr + arg] = toReplace.replace("cpy", "jnz")
            }
            ptr++
        }
    }
    print("The value of register a is ${registers['a']}")
}

fun main(){
    val inputFile = File("2016/inputs/Day_23.txt")
    print("\n----- Part 1 -----\n")
    day23(inputFile.readLines().toMutableList(), 7)
    print("\n----- Part 2 -----\n")
    day23(inputFile.readLines().toMutableList(), 12)
}