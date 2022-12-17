package day_16_2022

import java.io.File

val parsed = File("2022/inputs/Day_16.txt").readLines().map(Valve::from)
val valves = parsed.associateBy { it.id }
val shortestPaths =
    floydWarshall(parsed.associate { it.id to it.neighborIds.associateWith { 1 }.toMutableMap() }.toMutableMap())
var score = 0
var totalTime = 30

fun dfs(currScore: Int, currentValve: String, visited: Set<String>, time: Int, part2: Boolean = false) {
    score = maxOf(score, currScore)
    for ((valve, dist) in shortestPaths[currentValve]!!) {
        if (!visited.contains(valve) && time + dist + 1 < totalTime) {
            dfs(
                currScore + (totalTime - time - dist - 1) * valves[valve]?.flowRate!!,
                valve,
                visited.union(listOf(valve)),
                time + dist + 1,
                part2
            )
        }
    }
    if(part2)
        dfs(currScore, "AA", visited, 0, false)
}

fun floydWarshall(shortestPaths: MutableMap<String, MutableMap<String, Int>>): MutableMap<String, MutableMap<String, Int>> {
    for (k in shortestPaths.keys) {
        for (i in shortestPaths.keys) {
            for (j in shortestPaths.keys) {
                val ik = shortestPaths[i]?.get(k) ?: 9999
                val kj = shortestPaths[k]?.get(j) ?: 9999
                val ij = shortestPaths[i]?.get(j) ?: 9999
                if (ik + kj < ij)
                    shortestPaths[i]?.set(j, ik + kj)
            }
        }
    }
    //remove all paths that lead to a valve with rate 0
    shortestPaths.values.forEach {
        it.keys.map { key -> if (valves[key]?.flowRate == 0) key else "" }
            .forEach { toRemove -> if (toRemove != "") it.remove(toRemove) }
    }
    return shortestPaths
}

data class Valve(val id: String, val flowRate: Int, val neighborIds: List<String>) {
    companion object {
        fun from(line: String): Valve {
            val (name, rate) = line.split("; ")[0].split(" ").let { it[1] to it[4].split("=")[1].toInt() }
            val neighbors = line.split(", ").toMutableList()
            neighbors[0] = neighbors[0].takeLast(2)
            return Valve(name, rate, neighbors)
        }
    }
}

fun part1() {
    dfs(0, "AA", emptySet(), 0)
    print("The pressure that is able to be relieved is $score")
}

fun part2() {
    totalTime = 26
    score = 0
    dfs(0, "AA", emptySet(), 0, true)
    print("The pressure that is able to be relieved is $score")
}

fun main(){
    val inputFile =
    print("\n----- Part 1 -----\n")
    part1()
    print("\n----- Part 2 -----\n")
    part2()
}