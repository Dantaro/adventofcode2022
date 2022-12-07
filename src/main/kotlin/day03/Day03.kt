package day03

import AdventOfCode
import java.io.File
import kotlin.streams.toList

fun main() {
    println(Day03.part1())
    println(Day03.part2())
}

object Day03 : AdventOfCode {

    override fun part1(): Int {
        File(this::class.java.classLoader.getResource("day03/data.txt")!!.toURI().path)
            .useLines { lines ->
                return lines.map { line ->
                    val half = line.length / 2
                    val firstHalf = line.substring(0, half).chars().toList()
                    val secondHalf = line.substring(half).chars().toList().toSet()
                    val union = firstHalf intersect secondHalf
                    val intersectValue = union.first()
                    when {
                        intersectValue >= 97 -> intersectValue - 96
                        intersectValue < 97 -> intersectValue - 38
                        else -> 0
                    }
                }.sum()
            }
    }

    override fun part2(): Int {
        File(this::class.java.classLoader.getResource("day03/data.txt")!!.toURI().path)
            .useLines { lines ->
                return lines.map { s -> s.chars().toList() }.chunked(3)
                    .map { chunk ->
                        val intersect = chunk[0] intersect chunk[1].toSet() intersect chunk[2].toSet()
                        val intersectValue = intersect.first()
                        when {
                            intersectValue >= 97 -> intersectValue - 96
                            intersectValue < 97 -> intersectValue - 38
                            else -> 0
                        }
                    }.sum()
            }
    }
}