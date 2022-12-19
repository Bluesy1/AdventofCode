package day_19_2022

import java.io.File

class Blueprint(val input: String) {
    val blueprintNumber: Int
    val oreRobotCost: Int
    val clayRobotCost: Int
    val obsidianRobotCost: Pair<Int, Int>
    val geodeRobotCost: Pair<Int, Int>
    var value = 0

    init {
        val ints = input.split(" ").map { it.removeSuffix(":") }.mapNotNull(String::toIntOrNull)
        blueprintNumber = ints[0]
        oreRobotCost = ints[1]
        clayRobotCost = ints[2]
        obsidianRobotCost = Pair(ints[3], ints[4])
        geodeRobotCost = Pair(ints[5], ints[6])
    }
}

data class Nonet(val one: Int, val two: Int, val three: Int, val four: Int, val five: Int, val six: Int, val seven: Int, val eight: Int, val nine: Int)

fun solve(
    oreRobotCost: Int,
    clayRobotCost: Int,
    obsidianRobotCostFirst: Int,
    obsidianRobotCostSecond: Int,
    geodeRobotCostFirst: Int,
    geodeRobotCostSecond: Int,
    allottedTime: Int
): Int {
    var best = 0
    val queue = ArrayDeque(listOf(Nonet(0, 0, 0, 0, 1, 0, 0, 0, allottedTime)))
    val seen = mutableSetOf<Nonet>()
    while (queue.isNotEmpty()) {
        var state = queue.removeFirst()
        var (ore, clay, obsidian, geodes, r1, r2, r3, r4, time) = state

        best = maxOf(best, geodes)
        if (time==0)
            continue
        val core = maxOf(oreRobotCost, clayRobotCost, obsidianRobotCostFirst, geodeRobotCostFirst)
        if (r1>=core)
            r1 = core
        if (r2>=obsidianRobotCostSecond)
            r2 = obsidianRobotCostSecond
        if (r3>=geodeRobotCostSecond)
            r3 = geodeRobotCostSecond
        if (ore >= time*core-r1*(time-1))
            ore = time*core-r1*(time-1)
        if (clay>=time*obsidianRobotCostSecond-r2*(time-1))
            clay = time*obsidianRobotCostSecond - r2*(time-1)
        if (obsidian>=time*geodeRobotCostSecond-r3*(time-1))
            obsidian = time*geodeRobotCostSecond-r3*(time-1)

        state = Nonet(ore,clay,obsidian,geodes,r1,r2,r3,r4,time)

        if (state in seen)
            continue
        seen.add(state)
        queue.add(Nonet(ore+r1,clay+r2,obsidian+r3,geodes+r4,r1,r2,r3,r4,time-1))
        if (ore>=oreRobotCost) // buy ore
            queue.add(
                Nonet(ore-oreRobotCost+r1, clay+r2, obsidian+r3, geodes+r4, r1+1,r2,r3,r4,time-1)
            )
        if (ore>=clayRobotCost)
            queue.add(
                Nonet(ore-clayRobotCost+r1, clay+r2, obsidian+r3, geodes+r4, r1,r2+1,r3,r4,time-1)
            )
        if (ore>=obsidianRobotCostFirst && clay>=obsidianRobotCostSecond)
            queue.add(
                Nonet(ore-obsidianRobotCostFirst+r1, clay-obsidianRobotCostSecond+r2, obsidian+r3, geodes+r4, r1,r2,r3+1,r4,time-1)
            )
        if (ore>=geodeRobotCostFirst && obsidian>=geodeRobotCostSecond)
            queue.add(
                Nonet(ore-geodeRobotCostFirst+r1, clay+r2, obsidian-geodeRobotCostSecond+r3, geodes+r4, r1,r2,r3,r4+1,time-1)
            )
    }
    return best
}

fun part1(input: List<String>) {
    var score = 0
    for (line in input) {
        val blueprint = Blueprint(line)
        val subScore = solve(
            blueprint.oreRobotCost,
            blueprint.clayRobotCost,
            blueprint.obsidianRobotCost.first,
            blueprint.obsidianRobotCost.second,
            blueprint.geodeRobotCost.first,
            blueprint.geodeRobotCost.second,
            24
        )
        score += subScore * blueprint.blueprintNumber
    }
    print("The sum of the quality levels is $score")
}

fun part2(input: List<String>) {
    var score = 1
    for (line in input.subList(0,3)) {
        val blueprint = Blueprint(line)
        val subScore = solve(
            blueprint.oreRobotCost,
            blueprint.clayRobotCost,
            blueprint.obsidianRobotCost.first,
            blueprint.obsidianRobotCost.second,
            blueprint.geodeRobotCost.first,
            blueprint.geodeRobotCost.second,
            32
        )
        score *= subScore
    }
    print("The product of the max geodes for blueprints 1 to 3 is $score")
}

fun main(){
    val inputFile = File("2022/inputs/Day_19.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}