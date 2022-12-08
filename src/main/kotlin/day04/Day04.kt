package day04

import AdventOfCode

fun main() {
    println(Day04.part1())
    println(Day04.part2())
}

object Day04 : AdventOfCode {
    override fun url(): String = "https://adventofcode.com/2022/day/4"

    override fun part1(): Int {
        FileUtil.getFile("day04").useLines { lines ->
            return lines.map { line ->
                line.split(",")
                    .map {
                        val assignment = it.split("-")
                        (assignment[0].toInt()..assignment[1].toInt())
                    }
            }.filter { sectors ->
                val sector0 = sectors[0].toSet()
                val sector1 = sectors[1].toSet()
                sector0.containsAll(sector1) || sector1.containsAll(sector0)
            }.count()
        }
    }

    override fun part2(): Int {
        FileUtil.getFile("day04").useLines { lines ->
            return lines.map { line ->
                line.split(",")
                    .map {
                        val assignment = it.split("-")
                        (assignment[0].toInt()..assignment[1].toInt())
                    }
            }.filter { sectors ->
                val sector0 = sectors[0].toSet()
                val sector1 = sectors[1].toSet()
                (sector0 intersect sector1).isNotEmpty()
            }.count()
        }
    }
}