package day_10_2016

private val value = Regex("""value (\d+) goes to bot (\d+)""")
private val op = Regex("""bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""")

class BotController {
    val bots = mutableMapOf<Int, Bot>()
    val ops = mutableMapOf<Int, (Bot) -> Unit>()

    val outputs = mutableMapOf<Int, List<Int>>()
    fun parseOp(operation: String) {
        when {
            value.matches(operation) -> {
                val (rawChip, rawBot) = value.find(operation)?.destructured!!
                val botN = rawBot.toInt()
                val chip = rawChip.toInt()
                val bot = bots.getOrDefault(botN, Bot.emptyBot)
                bots[botN] = bot.give(chip)
            }
            op.matches(operation) -> {
                val (rawBotFrom, targetLow, rawLow, targetUp, rawUp) = op.find(operation)?.destructured!!
                val lowId = rawLow.toInt()
                val upId = rawUp.toInt()

                ops[rawBotFrom.toInt()] = {
                    (lower, upper) ->
                    when (targetLow) {
                        "bot" ->  bots[lowId] = bots.getOrDefault(lowId, Bot.emptyBot).give(lower)
                        "output" -> outputs[lowId] = outputs.getOrDefault(lowId, emptyList()) + lower
                    }
                    when (targetUp) {
                        "bot" ->  bots[upId] = bots.getOrDefault(upId, Bot.emptyBot).give(upper)
                        "output" -> outputs[upId] = outputs.getOrDefault(upId, emptyList()) + upper
                    }
                }
            }
        }
    }

    fun trace(lower: Int, upper: Int): Int {
        while (true) {
            val target = bots.filter { it.value.lower == lower && it.value.upper == upper }
            if (target.isNotEmpty())
                return target.keys.first()
            val botsReady = bots.filter { it.value.isReady }
            if (botsReady.isEmpty())
                break
            botsReady.forEach { (id, bot) -> ops[id]?.invoke(bot); bots[id] = Bot.emptyBot }
        }
        return -1
    }

    fun run() {
        while (true) {
            val botsReady = bots.filter { it.value.isReady }
            if (botsReady.isEmpty())
                break
            botsReady.forEach { (id, bot) -> ops[id]?.invoke(bot); bots[id] = Bot.emptyBot }
        }
    }
}