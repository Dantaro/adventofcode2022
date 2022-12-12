package day08

import AdventOfCode
import FileUtil
import kotlin.streams.toList

fun main() {
    println(Day08.part1())
    println(Day08.part2())
}

object Day08 : AdventOfCode {
    override fun part1(): Int {
        val leftToRight = FileUtil.getFile("day08").readLines().map { it.chars().mapToObj { char -> Character.toString(char) }.toList() }
        val width = leftToRight[0].size
        val height = leftToRight.size
        val topToBottom = (0 until width).map { (0 until height).map { "" }.toMutableList() }
        (0 until width).forEach { x ->
            (0 until height).forEach { y ->
                topToBottom[x][y] = leftToRight[y][x]
            }
        }
        return -1
    }

    override fun part2(): Int {
        FileUtil.getFile("day08")
        return -1
    }

    override fun url(): String = "https://adventofcode.com/2022/day/8"

}