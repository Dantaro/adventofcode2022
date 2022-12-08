package day05

import AdventOfCode
import FileUtil

fun main() {
    println(Day05.part1())
    println(Day05.part2())
}

object Day05 : AdventOfCode {

    override fun url(): String = "https://adventofcode.com/2022/day/5"
    override fun part1(): Int {
        val data = FileUtil.getFile("day05").readLines()

        /*val crateSetup = data.takeWhile { it.isNotEmpty() }
        val crateMoves = data.subList(crateSetup.size + 1, data.size)
        println("Data:")
        println("Initial State:")
        println(crateSetup.joinToString("\n") { it })
        println("Move list:")
        println(crateMoves.joinToString("\n") { it })

        //val crateStacks = crateSetup.last().split("").chunked(4).map { it.joinToString().trim() }.map { Stack<Int>() }
        val crateStacks = crateSetup
            .last()
            .split("")
            .chunked(4)
            .map { it.mapNotNull { str -> str.toIntOrNull() }.joinToString().trim().toInt() }
            .associateWith { 0 }
            .toMutableMap()
        println(crateStacks)
        crateSetup.subList(0, crateSetup.size - 1).map { it.split("").chunked(4) }
            .forEach { row ->
                row.map { it.joinToString("") }.forEachIndexed { i, crate ->
                    if (crate.isNotEmpty()) {
                        crateStacks[i + 1] = crateStacks[i + 1]!! + 1
                    }
                }
            }
        println(crateStacks)*/
        return 0
    }

    override fun part2(): Int {
        return 0
    }

}