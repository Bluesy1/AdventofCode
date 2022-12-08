package day_08

import java.io.File

fun part1(input: List<String>) {
    val intInput = input.map { s -> s.toCharArray().map<Int> { it.toString().toInt() }.toList() }
    var viewable = (input.size - 1) * 4
    for (i in 1 until input.size-1) {
        for (j in 1 until input.size-1) {
            val testing = intInput[i][j]
            var flag = false
            for (x in (0 until i).reversed()) {
                if (intInput[x][j] >= testing) {
                    flag = true
                    break
                }
            }
            if (!flag) {
                viewable++
                continue
            }
            flag = false
            for (x in (i + 1 until input.size)) {
                if (intInput[x][j] >= testing) {
                    flag = true
                    break
                }
            }
             if (!flag) {
                viewable++
                continue
            }
            flag = false
            for (y in (0 until j).reversed()) {
                if (intInput[i][y] >= testing) {
                    flag = true
                    break
                }
            }
            if (!flag) {
                viewable++
                continue
            }
            flag = false
            for (y in (j + 1 until input.size)) {
                if (intInput[i][y] >= testing) {
                    flag = true
                    break
                }
            }
            if (!flag) viewable++
        }
    }
    print("The number of viewable trees is $viewable")
}

fun part2(input: List<String>) {
    val intInput = input.map { s -> s.toCharArray().map<Int> { it.toString().toInt() }.toList() }
    val scenicValues = mutableSetOf<Int>()
    for (i in 1 until input.size-1) {
        for (j in 1 until input.size-1) {
            val testing = intInput[i][j]
            var leftScenicNess = 0
            for (x in (0 until i).reversed()) {
                leftScenicNess++
                if (intInput[x][j] >= testing) {
                    break
                }
            }
            var rightScenicNess = 0
            for (x in (i + 1 until input.size)) {
                rightScenicNess++
                if (intInput[x][j] >= testing) {
                    break
                }
            }
            var upScenicNess = 0
            for (y in (0 until j).reversed()) {
                upScenicNess++
                if (intInput[i][y] >= testing) {
                    break
                }
            }
            var downScenicNess = 0
            for (y in (j + 1 until input.size)) {
                downScenicNess++
                if (intInput[i][y] >= testing) {
                    break
                }
            }
            scenicValues.add(leftScenicNess * rightScenicNess * upScenicNess * downScenicNess)
        }
    }
    print("The highest scenic value is ${scenicValues.max()}")
}

fun main(){
    val inputFile = File("src/inputs/Day_08.txt")
    print("\n----- Part 1 -----\n")
    part1(inputFile.readLines())
    print("\n----- Part 2 -----\n")
    part2(inputFile.readLines())
}