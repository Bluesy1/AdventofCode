package day_11_2022

import java.math.BigInteger

data class Monkey(
    var items: MutableList<BigInteger>,
    val op: Boolean,
    val amt: Int?,
    val condition: Int,
    val onTrue: Int,
    val onFalse: Int,
    val divThree: Boolean,
    var inspectedTimes: Long = 0L
) {
    fun inspectItems(monkeys: List<Monkey>, superMod: Int = 1){
        for (item in items) {
            var testLevel: BigInteger = (if (op) item * BigInteger(((amt ?: item).toString())) else item + BigInteger(((amt ?: item).toString())))
            testLevel = if (divThree) testLevel / BigInteger("3") else testLevel % BigInteger(superMod.toString())
            val test = testLevel.toInt() % condition == 0
            if (test) {
                monkeys[onTrue].items.add(testLevel)
            } else {
                monkeys[onFalse].items.add(testLevel)
            }
            inspectedTimes++
        }
        items.clear()
    }
}
