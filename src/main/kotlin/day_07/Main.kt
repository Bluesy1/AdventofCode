package day_07

import java.io.File

@Suppress("UNCHECKED_CAST")
fun castMapTypes(toCast: Any?): MutableMap<String, Any> = toCast as MutableMap<String, Any>

fun calculateDirSize(dirName: String, dir: Map<String, Any>, dirSizes: MutableMap<String, Int>): Int{
    if (dir.values.any { it is MutableMap<*,*> }){
        for (subdir in dir.entries.filter { it.value is MutableMap<*, *> }) {
            val newDirName = dirName + "/" + subdir.key
            dirSizes[newDirName] = calculateDirSize(newDirName, castMapTypes(subdir.value), dirSizes)
        }
    }
    var size = 0
    for (file in dir.entries.filter { it.value is Int }) {
        size += file.value as Int
    }
    for (subdir in dir.entries.filter { it.value is MutableMap<*, *> }) {
        size += dirSizes[dirName + "/" + subdir.key]!!
    }
    return size
}

fun parseInputToFileStructure(input: List<String>): Map<String, Any> {
    val dir: MutableMap<String, Any> = mutableMapOf()
    var currentDir = dir
    val dirTree = mutableListOf<MutableMap<String, Any>>()
    for (row in input.subList(1, input.size)){
        if (row.startsWith("$")){
            val command = row.split(" ")[1]
            if (command == "cd") {
                val subCommand = row.split(" ")[2]
                currentDir = if (subCommand == "..") {
                    dirTree.removeLast()
                } else {
                    dirTree.add(currentDir)
                    castMapTypes(currentDir[subCommand])
                }
            }
        } else if (row.startsWith("dir")){
            currentDir[row.split(' ')[1]] = mutableMapOf<String, Any>()
        } else {
            val size = row.split(' ')[0].toInt()
            var fileName = row.split(' ')[1]
            fileName = if (fileName.contains('.')) fileName else "${fileName}.noext"
            currentDir[fileName] = size
        }
    }
    return dir
}

fun part1(input: List<String>) {
    val dir = parseInputToFileStructure(input)
    val dirSizes = mutableMapOf<String, Int>()
    dirSizes[""] = calculateDirSize("", dir, dirSizes)
    print("The total size of all dirs under 100000 is ${dirSizes.values.filter { it < 100000 }.sum()}")
}

fun part2(input: List<String>) {
    val dir = parseInputToFileStructure(input)
    val dirSizes = mutableMapOf<String, Int>()
    dirSizes[""] = calculateDirSize("", dir, dirSizes)
    val spaceNeeded = 30000000 - (70000000 - dirSizes[""]!!)
    print("The space of the smallest directory that satisfies the space requirements is ${dirSizes.values.filter { it >= spaceNeeded }.min()}")
}

fun main(){
    val inputFile = File("src/inputs/Day_07.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}