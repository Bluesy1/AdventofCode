package day_21_2022

import java.io.File

enum class Operation {
    ADD,
    MULTIPLY,
    SUBTRACT,
    DIVIDE,
}

class Monkey(val name: String, op: String){
    var isOp: Boolean
    private var operation: Operation?
    var value: Long?
    private val initial: String = op
    init {
        val splitInput = op.split(" ")
        isOp = splitInput.size > 1
        operation = if (isOp)
            when(splitInput[1]){
                "+" -> Operation.ADD
                "*" -> Operation.MULTIPLY
                "-" -> Operation.SUBTRACT
                "/" -> Operation.DIVIDE
                else -> null
            }
        else
            null
        value = if(isOp) null else op.toLong()
    }
    fun tryEvalPart1(monkeys: MutableList<Monkey>){
        if(isOp){
            val left = monkeys.firstOrNull { it.name == initial.split(" ")[0] }
            val right = monkeys.firstOrNull { it.name == initial.split(" ")[2] }
            if (left == null || right == null) {
                return
            }
            if(left.value != null && right.value != null){
                value = when(operation){
                    Operation.ADD -> left.value!! + right.value!!
                    Operation.MULTIPLY -> left.value!! * right.value!!
                    Operation.SUBTRACT -> left.value!! - right.value!!
                    Operation.DIVIDE -> left.value!! / right.value!!
                    else -> null
                }
                isOp = false
            }
        }
    }
}

//rejig equation as we know result and one operand, but not the other
@Suppress("SpellCheckingInspection")
private tailrec fun monkeyChainToHuman(
    monkeyToFigureOut: MonkeyWithMaths, knownMonkeys: Map<String, Long>,
    monkeys: List<MonkeyWithMaths>
): Map<String, Long> {
    val selfValue = knownMonkeys.getValue(monkeyToFigureOut.resultMonkey)

    val op1 = monkeyToFigureOut.operandMonkey1
    val op2 = monkeyToFigureOut.operandMonkey2

    val nextMonkey: MonkeyWithResult = if (knownMonkeys.containsKey(op1)) {
        val knownOperand = knownMonkeys.getValue(op1)
        //for divs and subtracts order is important so make sure we get right
        val operand = when (monkeyToFigureOut.operator) {
            Operator.DIVIDE -> knownOperand.div(selfValue)
            Operator.SUBTRACT -> knownOperand - selfValue
            else -> monkeyToFigureOut.operator.inverseFunction(selfValue, knownOperand)
        }
        MonkeyWithResult(op2, operand)
    } else if (knownMonkeys.containsKey(op2)) {
        val knownOperand = knownMonkeys.getValue(op2)
        val operand = monkeyToFigureOut.operator.inverseFunction(selfValue, knownOperand)
        MonkeyWithResult(op1, operand)
    } else {
        throw RuntimeException("Unsolvable, neither monkey is known!")
    }

    val updatedMonkeys = knownMonkeys.toMutableMap()
    updatedMonkeys[nextMonkey.monkeyName] = nextMonkey.monkeyValue
    if (nextMonkey.monkeyName == "humn") {
        return updatedMonkeys
    }
    val nextMonkeyInList = monkeys.find { monkey -> monkey.resultMonkey == nextMonkey.monkeyName }!!

    return monkeyChainToHuman(nextMonkeyInList, updatedMonkeys, monkeys)
}

private fun extractRootMonkey(lines: List<String>): MonkeyWithMaths =  lines.filter {
        line -> line.contains("root") }
        .map { line -> parseMonkey(line.split(" ")) }
        .first()

private tailrec fun findAllPossibleValuesIteratively(
    knownMonkeys: Map<String, Long>,
    unknownMonkeys: List<MonkeyWithMaths>
): Map<String, Long> {
    val newlyDiscoveredValues: MutableMap<String, Long> = unknownMonkeys
        .filter { monkey ->
            knownMonkeys.containsKey(monkey.operandMonkey1)
                    && knownMonkeys.containsKey(monkey.operandMonkey2)
        }
        .map { monkeyMaths ->
            val result = findValueForEquation(knownMonkeys, monkeyMaths)
            MonkeyWithResult(monkeyMaths.resultMonkey, result)
        }
        .associate { monkeyResult -> monkeyResult.monkeyName to monkeyResult.monkeyValue }
        .toMutableMap()

    if (newlyDiscoveredValues.isEmpty()) {
        //can't solve anymore (part 2 thing)
        return knownMonkeys
    }

    newlyDiscoveredValues.putAll(knownMonkeys)

    return findAllPossibleValuesIteratively(
        newlyDiscoveredValues,
        unknownMonkeys.filter { monkey -> !newlyDiscoveredValues.containsKey(monkey.resultMonkey) }
    )
}

private fun findValueForEquation(
    knownMonkeys: Map<String, Long>,
    monkeyWithMaths: MonkeyWithMaths
): Long {
    val operand1Value = knownMonkeys.getValue(monkeyWithMaths.operandMonkey1)
    val operand2Value = knownMonkeys.getValue(monkeyWithMaths.operandMonkey2)
    return monkeyWithMaths.operator.function(operand1Value, operand2Value)
}

private fun parseMonkey(splitLine: List<String>): MonkeyWithMaths {
    val opRaw = splitLine[2]
    val operator: Operator = Operator.values().first { op -> op.rawVal == opRaw }
    return MonkeyWithMaths(splitLine[0].replace(":", ""), splitLine[1], splitLine[3], operator)
}

enum class Operator(val rawVal: String, val function: (Long, Long) -> Long, val inverseFunction: (Long, Long) -> Long) {
    ADD("+", Long::plus, Long::minus),
    SUBTRACT("-", Long::minus, Long::plus),
    MULTIPLY("*", Long::times, Long::div),
    DIVIDE("/", Long::div, Long::times)
}

class MonkeyWithMaths(
    val resultMonkey: String, val operandMonkey1: String,
    val operandMonkey2: String, val operator: Operator
)

class MonkeyWithResult(val monkeyName: String, val monkeyValue: Long)

fun part1(input: List<String>) {
    val monkeys = input.map {
        val postSplit = it.split(": ")
        Monkey(postSplit[0], postSplit[1])
    }.toMutableList()
    while(monkeys.first { it.name == "root" }.isOp){
        monkeys.forEach { it.tryEvalPart1(monkeys) }
    }
    val root = monkeys.first { it.name == "root" }
    print("The value of the root monkey is ${root.value}")
}

@Suppress("SpellCheckingInspection")
fun part2(lines: List<String>) {
    val rootMonkey = extractRootMonkey(lines)

    //map associates the monkeys we know and what numbers they have
    val knownMonkeys = lines.map { line -> line.split(" ") }
        .filter { line -> line.size == 2 }
        .associate { line -> line[0].replace(":", "") to line[1].toLong() }.toMutableMap()

    val monkeys: List<MonkeyWithMaths> = lines.map { line -> line.split(" ") }
        .filter { line -> line.size > 2 }
        .map { line -> parseMonkey(line) }

    knownMonkeys.remove("humn")

    val partiallyCompleteMonkeys: MutableMap<String, Long> =
        findAllPossibleValuesIteratively(knownMonkeys, monkeys)
            .toMutableMap()

    /**
     * e.g.
     * root = pppw = sjmn (we know sjmm is 150) so pppw must be 150 as well
     * pppw = cczh / lfqf (we know lfgf is 4, so it becomes 150 = cczh / 4 so cczh = 600
     * cczh = sllz + lgvd (we know cczh is 600, sllz is 4 so lgvd is 596) ... and so on
     */
    //roots kinda a special case so handle outside of main iteration
    val op1 = rootMonkey.operandMonkey1
    val op2 = rootMonkey.operandMonkey2

    val nextMonkey = if (partiallyCompleteMonkeys.containsKey(op1)) {
        partiallyCompleteMonkeys[op2] = partiallyCompleteMonkeys.getValue(op1)
        monkeys.find { monkey -> monkey.resultMonkey == op2 }!!
    } else if (partiallyCompleteMonkeys[op2] != null) {
        partiallyCompleteMonkeys[op1] = partiallyCompleteMonkeys.getValue(op2)
        monkeys.find { monkey -> monkey.resultMonkey == op1 }!!
    } else {
        throw RuntimeException("Unsolvable, neither monkey is known!")
    }
    val completedMonkeys = monkeyChainToHuman(nextMonkey, partiallyCompleteMonkeys, monkeys)
    print("The value I should say is ${completedMonkeys.getValue("humn")}")
}

fun main(){
    val inputFile = File("2022/inputs/Day_21.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}