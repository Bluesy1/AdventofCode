package day_12_2022

import java.io.File


fun Pair<Int, Int>.directNeighbors(): MutableList<Pair<Int, Int>> {
    val list = mutableListOf<Pair<Int, Int>>()
    for (yOff in -1..1) {
        for (xOff in -1..1) {
            //if not diagonal or self
            if ((xOff == 0) xor (yOff == 0)) {
                list.add(Pair(this.first + xOff, this.second + yOff))
            }
        }
    }
    return list
}
fun part1(input: String) {
        val heights = mutableMapOf<Pair<Int, Int>?, Int>()
        val lines = input.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var start: Pair<Int, Int>? = null
        var end: Pair<Int, Int>? = null
        //go over input, populating heights and noting start and end
        for (y in lines.indices) {
            val line = lines[y]
            for (x in line.indices) {
                if (line[x] == 'S') start = x to y
                else if (line[x] == 'E') end = x to y
                else heights[x to y] = line[x].code
            }
        }
        //start is  one lower than lowest, end is one higher than highest
        heights[start] = 'a'.code - 1
        heights[end] = 'z'.code + 1
        print("The shortest path contains ${pathfinder(start!!, end!!, heights)} moves")
}

fun pathfinder(start: Pair<Int, Int>, end: Pair<Int, Int>, heights: MutableMap<Pair<Int, Int>?, Int>): Int {
        val gCost = mutableMapOf<Pair<Int, Int>?, Int>()
        val parent = mutableMapOf<Pair<Int, Int>?, Pair<Int, Int>?>()
        val queue = mutableListOf<Pair<Int, Int>>()
        gCost[start] = 0
        queue.add(start)
        while (queue.size > 0) {
            var cur: Pair<Int, Int>? = queue.removeAt(0)
            if (cur == end) {
                val path: ArrayList<Pair<Int, Int>?> = ArrayList()
                while (parent.containsKey(cur)) {
                    path.add(cur)
                    cur = parent[cur]
                }
                return path.size
            }
            for (c in cur!!.directNeighbors()) {
                //skip if outside bounds or if height is more than one above current
                if (!heights.containsKey(c) || heights[c]!! > heights[cur]!! + 1) continue
                val tentativeG = gCost[cur]!! + 1
                if (tentativeG < gCost.getOrDefault(c, Int.MAX_VALUE)) {
                    gCost[c] = tentativeG
                    parent[c] = cur
                    queue.add(c)
                }
            }
        }
        return Int.MAX_VALUE
    }

fun part2(input: String) {
    val heights = mutableMapOf<Pair<Int, Int>?, Int>()
    val lines = input.split("\n")
    lateinit var start: Pair<Int, Int>
    lateinit var end: Pair<Int, Int>
    for (y in lines.indices) {
        val line = lines[y]
        for (x in line.indices) {
            if (line[x] == 'S') start = x to y
            else if (line[x] == 'E') end = x to y
            else heights[x to y] = line[x].code
        }
    }
    heights[start] = 'a'.code - 1
    heights[end] = 'z'.code + 1
    //now, go over all possible locations - if location is an 'a',
    // the shortest path is minimum of existing shortest and fastest path from cur to end
    var shortest = Int.MAX_VALUE
    for (c in heights.keys) {
        if (heights[c] == 'a'.code) shortest = shortest.coerceAtMost(pathfinder(c!!, end, heights))
    }
    print("The shortest path contains $shortest moves")
}

fun main(){
    val inputFile = File("2022/inputs/Day_12.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readText())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readText())
}