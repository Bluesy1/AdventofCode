package day_22_2022


import java.io.File

private val moveRegex = Regex("(\\d{1,2}|L|R)")

private enum class Facing {
    UP, DOWN, LEFT, RIGHT;

    val left: Facing
        get() = when (this) {
            UP -> LEFT
            DOWN -> RIGHT
            LEFT -> DOWN
            RIGHT -> UP
        }

    val right: Facing
        get() = when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }

    val value: Int
        get() = when (this) {
            RIGHT -> 0
            DOWN -> 1
            LEFT -> 2
            UP -> 3
        }
}

private fun part1(input: List<String>) {
    val mapLines = input.subList(0, input.size - 2)
    val map = mapLines.map { it.toCharArray().toMutableList() }.map {
        while (it.size < input[0].length) {
            it.add(' ')
        }
        it
    }.toMutableList()
    val move = input.last()
    var facing = Facing.RIGHT
    var x = map[0].indexOf('.')
    var y = 0
    moveRegex.findAll(move).map(MatchResult::value).forEach {
        when (it) {
            "L" -> {
                facing = facing.left
            }
            "R" -> {
                facing = facing.right
            }
            else -> {
                var value = it.toInt()
                when (facing) {
                    Facing.UP -> {
                        while (value > 0) {
                            if (y == 0 || map[y - 1][x] == ' ') {
                                val last = map.last { inner -> inner[x] != ' ' }
                                if (last[x] == '#') {
                                    //map[y][x] = '^'
                                    break
                                }
                                //map[y][x] = '^'
                                y = map.lastIndexOf(last)
                                value--
                            } else if (map[y - 1][x] == '#') {
                                //map[y][x] = '^'
                                break
                            } else {
                                //map[y][x] = '^'
                                y--
                                value--
                            }
                        }
                    }
                    Facing.DOWN -> {
                        while (value > 0) {
                            if (y == map.size - 1 || map[y + 1][x] == ' ') {
                                val first = map.first { inner -> inner[x] != ' ' }
                                if (first[x] == '#') {
                                    //map[y][x] = 'v'
                                    break
                                }
                                //map[y][x] = 'v'
                                y = map.indexOf(first)
                                value--
                            } else if (map[y + 1][x] == '#') {
                                //map[y][x] = 'v'
                                break
                            } else {
                                //map[y][x] = 'v'
                                y++
                                value--
                            }
                        }
                    }
                    Facing.LEFT -> {
                        while (value > 0) {
                            if (x == 0 || map[y][x - 1] == ' ') {
                                val last = map[y].last { inner -> inner != ' ' }
                                if (last == '#') {
                                    //map[y][x] = '<'
                                    break
                                }
                                //map[y][x] = '<'
                                x = map[y].lastIndexOf(last)
                                value--
                            } else if (map[y][x - 1] == '#') {
                                //map[y][x] = '<'
                                break
                            } else {
                                //map[y][x] = '<'
                                x--
                                value--
                            }
                        }
                    }
                    Facing.RIGHT -> {
                        while (value > 0) {
                            if (x == map[y].size - 1 || map[y][x + 1] == ' ') {
                                val first = map[y].first { inner -> inner != ' ' }
                                if (first == '#') {
                                    //map[y][x] = '>'
                                    break
                                }
                                //map[y][x] = '>'
                                x = map[y].indexOf(first)
                                value--
                            } else if (map[y][x + 1] == '#') {
                                //map[y][x] = '>'
                                break
                            } else {
                                //map[y][x] = '>'
                                x++
                                value--
                            }
                        }
                    }
                }
            }
        }
    }
    val sum = (1000 * (y+1) ) + (4 * (x+1)) + facing.value
    print("The password is $sum")
}

@Suppress("UNUSED_PARAMETER")
private fun part2(input: List<String>) {
    // I did this one by hand with some repl code to help, I couldn't figure out a way to do it programmatically easily
    print("Part 2: ")
}

fun main(){
    val inputFile = File("2022/inputs/Day_22.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}