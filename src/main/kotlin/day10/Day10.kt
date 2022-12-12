package day10

import AdventOfCode
import FileUtil

fun main() {
    println(Day10.part1())
    println(Day10.part2())
}

object Day10 : AdventOfCode {
    override fun url(): String = "https://adventofcode.com/2022/day/10"

    override fun part1(): Int {
        var clockCount = 1
        var xReg = 1
        val reportClockCounts = setOf(20, 60, 100, 140, 180, 220)
        val signalStrengths = mutableListOf<Int>()

        fun incrementClockCount() {
            clockCount += 1
            if (reportClockCounts.contains(clockCount)) {
                signalStrengths.add(clockCount * xReg)
            }
        }

        fun addX(v: Int) {
            incrementClockCount()
            xReg += v
            incrementClockCount()
        }

        fun noop() {
            incrementClockCount()
        }

        FileUtil.getFile("day10").useLines { lines ->
            lines.forEach { line ->
                when {
                    line.startsWith("addx") -> {
                        addX(line.split(" ")[1].toInt())
                    }
                    line.startsWith("noop") -> {
                        noop()
                    }
                }
            }
        }

        return signalStrengths.sum()
    }

    override fun part2(): Int {
        var clockCount = 1
        var xReg = 1
        val crtDisplay = (0 until 6).map { (0 until 40).map { "." }.toMutableList() }
        var crtPixelPosition = 0
        var crtPixelLine = 0

        infix fun Int.between(range: Pair<Int, Int>): Boolean {
            return this >= range.first && this <= range.second
        }

        fun incrementClockCount() {
            clockCount += 1
            if (crtPixelPosition between (xReg - 1 to xReg + 1)) {
                crtDisplay[crtPixelLine][crtPixelPosition] = "#"
            }
            crtPixelPosition += 1
            if (crtPixelPosition >= 40) {
                crtPixelLine += 1
                crtPixelPosition = 0
            }
        }

        fun addX(v: Int) {
            incrementClockCount()
            incrementClockCount()
            xReg += v
        }

        fun noop() {
            incrementClockCount()
        }

        FileUtil.getFile("day10").useLines { lines ->
            lines.forEach { line ->
                when {
                    line.startsWith("addx") -> {
                        addX(line.split(" ")[1].toInt())
                    }
                    line.startsWith("noop") -> {
                        noop()
                    }
                }
            }
        }

        println(crtDisplay.joinToString("\n") { it.joinToString("") })

        return -1
    }

}