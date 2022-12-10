package day_10_2016

data class Bot(val lower: Int, val upper: Int) {
    companion object {
        val emptyBot = Bot(-1, -1)
    }
    val isReady: Boolean
        get() = lower != -1
    fun give(chip: Int): Bot {
        return if (chip > upper)
            Bot(upper, chip)
        else
            Bot(chip, upper)
    }
}
