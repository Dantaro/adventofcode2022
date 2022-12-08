package day01

import AdventOfCode
import FileUtil
import java.io.File

fun main() {
    println(Day01.part1())
    println(Day01.part2())
}

object Day01 : AdventOfCode {
    override fun url(): String = "https://adventofcode.com/2022/day/1"

    override fun part1(): Int {
        FileUtil.getFile("day01")
            .useLines { lines ->
                var mostCalories = 0
                val currentBackpack = mutableListOf<Int>()
                lines.forEach { line ->
                    if (line == "") {
                        if (mostCalories < currentBackpack.sum()) {
                            mostCalories = currentBackpack.sum()
                        }
                        currentBackpack.clear()
                    } else {
                        currentBackpack.add(line.toInt())
                    }
                }
                return mostCalories
            }
    }
    override fun part2(): Int {
        File(this::class.java.classLoader.getResource("day01/data.txt")!!.toURI().path)
            .useLines { lines ->
                val currentBackpack = mutableListOf<Int>()
                val calories = mutableListOf<Int>()
                lines.forEach { line ->
                    if (line == "") {
                        calories.add(currentBackpack.sum())
                        currentBackpack.clear()
                    } else {
                        currentBackpack.add(line.toInt())
                    }
                }
                return calories.sortedDescending().take(3).sum()
            }
    }
}