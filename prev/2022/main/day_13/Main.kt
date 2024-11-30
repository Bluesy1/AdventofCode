package day_13_2022

import java.io.File

@Suppress("NAME_SHADOWING")
internal class Packet(packet: String) : Comparable<Packet?> {
    private var children: MutableList<Packet>
    var value = 0
    private var integer = true
    var str: String

    init {
        var packet = packet
        str = packet
        children = mutableListOf()
        if (packet == "[]") {
            value = -1
        }
        if (!packet.startsWith("[")) {
            value = packet.toInt()
        } else {
            packet = packet.substring(1, packet.length - 1)
            var level = 0
            var tmp = ""
            for (c in packet) {
                if (c == ',' && level == 0) {
                    children.add(Packet(tmp))
                    tmp = ""
                } else {
                    level += if (c == '[') 1 else if (c == ']') -1 else 0
                    tmp += c
                }
            }
            if (tmp != "") {
                children.add(Packet(tmp))
            }
            integer = false
        }
    }

    override operator fun compareTo(other: Packet?): Int {
        if (other == null) {
            return 0
        }
        if (integer && other.integer) {
            return other.value - value
        }
        if (!integer && !other.integer) {
            for (i in 0 until children.size.coerceAtMost(other.children.size)) {
                val value = children[i].compareTo(other.children[i])
                if (value != 0) {
                    return value
                }
            }
            return other.children.size - children.size
        }
        val lst1 = if (integer) Packet("[$value]") else this
        val lst2 = if (other.integer) Packet("[" + other.value + "]") else other
        return lst1.compareTo(lst2)
    }
}

fun part1(input: List<String>) {
    input.chunked(3)
        .map{ Packet(it[0]) to Packet(it[1]) }
        .mapIndexed{ index, pair -> if ((pair.first > pair.second)) index + 1 else 0}
        .sum()
        .let { print("The sum of the indices is $it.") }
}

fun part2(input: List<String>) {
    val packets = input.filterNot(String::isEmpty).map(::Packet).toMutableList()
    packets.add(Packet("[[2]]"))
    packets.add(Packet("[[6]]"))
    packets.sortDescending()
    packets.mapIndexed{ index, packet -> if (packet.str == "[[2]]" || packet.str == "[[6]]" ) index + 1 else 0}
        .filter { it != 0 }
        .reduce(Int::times)
        .let { print("The decoder key is $it.") }
}

fun main(){
    val inputFile = File("2022/inputs/Day_13.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}