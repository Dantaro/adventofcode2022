package day06

import AdventOfCode

fun main() {
    println(Day06.part1())
    println(Day06.part2())
}

object Day06 : AdventOfCode {
    override fun part1(): Int {
         return processData(4)
    }

    override fun part2(): Int {
        return processData(14)
    }

    override fun url(): String = "https://adventofcode.com/2022/day/6"

    private fun processData(size: Int): Int {
        FileUtil
            .getFile("day06")
            .readText()
            .let { data ->
                data
                    .forEachIndexed { index, _ ->
                        // Build a string starting at the current letter:
                        val duplicatesFound =  (0 until size).map {
                            data[index + it]
                        }.distinct().size < size
                        if (!duplicatesFound) {
                            return index + size
                        }
                    }
            }.let { return -1 }
    }
}