package day09

import AdventOfCode
import FileUtil
import day09.Direction.*
import java.lang.Math.abs

fun main() {
    println(Day09.part1())
    println(Day09.part2())
}

object Day09 : AdventOfCode {
    override fun url(): String = "https://adventofcode.com/2022/day/9"

    override fun part1(): Int {
        val hPosition = Position(0, 0)
        val tPosition = Position(0, 0)

        FileUtil.getFile("day09").useLines { lines ->
            lines.forEach { line ->
                val splitLine = line.split(" ")
                val direction = Direction.valueOf(splitLine[0])
                val distance = splitLine[1].toInt()
                (0 until distance).forEach { move ->
                    when(direction) {
                        R -> {
                            hPosition.x += 1
                            if (!isAdjacent(hPosition, tPosition)) {
                                
                            }
                        }
                        L -> {
                            hPosition.x -= 1
                        }
                        U -> {
                            hPosition.y += 1
                        }
                        D -> {
                            hPosition.y -= 1
                        }
                    }
                    println(hPosition)
                }
            }
        }
        return -1
    }

    override fun part2(): Int {
        return -1
    }

}

fun isAdjacent(p1: Position, p2: Position): Boolean {
    // Check if the coordinates are adjacent diagonally.
    if (abs(p1.x - p2.x) == 1 && abs(p1.y - p2.y) == 1) {
        return true
    }

    // Check if the coordinates are adjacent horizontally.
    if (abs(p1.x - p2.x) == 1 && p1.y == p2.y) {
        return true
    }

    // Check if the coordinates are adjacent vertically.
    if (abs(p1.y - p2.y) == 1 && p1.x == p2.x) {
        return true
    }

    return false
}

fun isDiagonal(p1: Position, p2: Position): Boolean {
    return abs(p1.x - p2.x) == 1 && abs(p1.y - p2.y) == 1
}

data class Position(var x: Int, var y: Int)

enum class Direction {
    R,
    L,
    U,
    D
}