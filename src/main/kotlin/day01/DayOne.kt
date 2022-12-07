package day01

import AdventOfCode
import java.io.File

fun main() {
    println(DayOne.part1())
    println(DayOne.part2())
}

object DayOne : AdventOfCode {
    override fun part1(): Int {
        File(this::class.java.classLoader.getResource("day01/data.txt")!!.toURI().path)
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