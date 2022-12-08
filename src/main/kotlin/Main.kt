import day01.Day01
import day02.Day02
import day03.Day03
import day04.Day04
import day06.Day06
import day07.Day07

fun main() {
    println("""
                          _   __           __   __  __  __  
     /\  _|   _ _ |_   _ (_  /   _  _| _    _) /  \  _)  _) 
    /--\(_|\/(-| )|_  (_)|   \__(_)(_|(-   /__ \__/ /__ /__ 
    """.trimIndent())
    println("To run Advent of Code solutions choose a day (1-25) or q to quit")
    while (true) {
        val input = readlnOrNull()
        if (!input.isNumber()) {
            if (input == "q") {
                break
            } else {
                println("Please enter a number (1-25)")
            }
        } else {
            val num = input!!.toInt()
            if (num > 25 || num < 1) {
                println("Please enter a number (1-25)")
            } else {
                val adventProblem = adventMap[num]
                if (adventProblem == null) {
                    println("No problem for that value (yet!)")
                } else {
                    println("URL: ${adventProblem.url()}")
                    println("Part 1: ${adventProblem.part1()}")
                    println("Part 2: ${adventProblem.part2()}")
                }
            }
        }
    }
}

private val adventMap: Map<Int, AdventOfCode> = mapOf(
    1 to Day01,
    2 to Day02,
    3 to Day03,
    4 to Day04,
    // 5 to Day05,
    6 to Day06,
    7 to Day07
)

fun String?.isNumber(): Boolean {
    return if (this.isNullOrBlank()) {
        false
    } else {
        this.all { Character.isDigit(it) }
    }
}
