package day02

import AdventOfCode
import FileUtil
import java.io.File

fun main() {
    println(Day02.part1())
    println(Day02.part2())
}

object Day02 : AdventOfCode {
    override fun url(): String = "https://adventofcode.com/2022/day/2"

    override fun part1(): Int {
        // A X Rock
        // B Y Paper
        // C Z Scissors
        File(this::class.java.classLoader.getResource("day02/data.txt")!!.toURI().path)
            .useLines { lines ->
                return lines.map { line ->
                    val splits = line.split(" ")
                    return@map scoreFinder(RPS.fromString(splits[0]), RPS.fromString(splits[1]))
                }.sum()
            }
    }

    override fun part2(): Int {
        // A Rock
        // B Paper
        // C Scissors
        // X Lose
        // Y Win
        // Z Tie
        FileUtil.getFile("day02")
            .useLines { lines ->
                return lines.map {line ->
                    val splits = line.split(" ")
                    val elf = RPS.fromString(splits[0])
                    val you = when (splits[1]) {
                        "X" -> {
                            when (elf) {
                                RPS.ROCK -> RPS.SCISSORS
                                RPS.PAPER -> RPS.ROCK
                                RPS.SCISSORS -> RPS.PAPER
                            }
                        }
                        "Y" -> elf
                        else -> {
                            when (elf) {
                                RPS.ROCK -> RPS.PAPER
                                RPS.PAPER -> RPS.SCISSORS
                                RPS.SCISSORS -> RPS.ROCK
                            }
                        }
                    }
                    return@map scoreFinder(elf, you)
                }.sum()
            }
    }

    private fun scoreFinder(first: RPS, second: RPS): Int {
        return when (second) {
            RPS.ROCK -> 1
            RPS.PAPER -> 2
            RPS.SCISSORS -> 3
        } +
        when (second) {
            RPS.ROCK -> {
                when (first) {
                    RPS.ROCK -> 3
                    RPS.PAPER -> 0
                    RPS.SCISSORS -> 6
                }
            }
            RPS.PAPER -> {
                when (first) {
                    RPS.ROCK -> 6
                    RPS.PAPER -> 3
                    RPS.SCISSORS -> 0
                }
            }
            RPS.SCISSORS -> {
                when (first) {
                    RPS.ROCK -> 0
                    RPS.PAPER -> 6
                    RPS.SCISSORS -> 3
                }
            }
        }
    }

    enum class RPS(val values: Set<String>) {
        ROCK(setOf("A", "X")),
        PAPER(setOf("B", "Y")),
        SCISSORS(setOf("C", "Z"));

        companion object {
            fun fromString(value: String): RPS {
                return RPS.values().filter { it.values.contains(value) }.first()
            }
        }
    }
}