package day07

import AdventOfCode
import FileUtil

fun main() {
    println(Day07.part1())
    println(Day07.part2())
}

object Day07 : AdventOfCode {
    override fun part1(): Int {
        val fileSystem = buildFilesystem()
        val calculatedNodes = calculateDirectorySizes(fileSystem)
        return calculatedNodes.values.filter { it < 100000 }.sum().toInt()
    }

    override fun part2(): Int {
        val fileSystem = buildFilesystem()
        val calculatedNodes = calculateDirectorySizes(fileSystem)
        val usedSpace = 70_000_000 - calculatedNodes[fileSystem]!!
        val neededSpace = 30_000_000 - usedSpace
        return calculatedNodes.values.filter { it >= neededSpace }.minOf { it }.toInt()
    }

    private fun calculateDirectorySizes(fileSystem: DirectoryNode): MutableMap<DirectoryNode, Long> {
        val calculatedNodes = mutableMapOf<DirectoryNode, Long>()
        val discovered = mutableListOf<DirectoryNode>()
        discovered.add(fileSystem)
        while (discovered.isNotEmpty()) {
            val currentNode = discovered.removeLast()
            if (calculatedNodes[currentNode] == null) {
                val subdirs = currentNode
                    .contents
                    .values
                    .filterIsInstance<DirectoryNode>()
                if ((subdirs intersect calculatedNodes.keys).size == subdirs.size) {
                    // If we've calculated the size of all the subdirectories for this directory
                    // Mark is completed and add it to the map
                    calculatedNodes[currentNode] = subdirs
                        .mapNotNull { calculatedNodes[it] }
                        .sum() + currentNode
                        .contents
                        .values
                        .filterIsInstance<FileNode>()
                        .sumOf { it.size }
                } else {
                    discovered.add(currentNode)
                    discovered.addAll(subdirs)
                }
            }
        }
        return calculatedNodes
    }

    private fun buildFilesystem(): DirectoryNode {
        val rootDirectory = DirectoryNode()
        val path = mutableListOf<DirectoryNode>()
        var currentDirectory = rootDirectory
        val lines = FileUtil.getFile("day07").readText().lines()
        lines
            .forEachIndexed { index, line ->
                if (line.startsWith("$")) {
                    // $ tells us we're starting a command
                    // All commands start at index 2
                    // All commands are in the form of "command input"
                    val command = line.substring(2).split(" ")
                    when {
                        command[0] == "cd" -> {
                            currentDirectory = when {
                                command[1] == ".." -> {
                                    if (path.isEmpty()) { rootDirectory } else { path.removeLast() }
                                }
                                command[1] == "/" -> {
                                    path.clear()
                                    rootDirectory
                                }
                                else -> {
                                    path.add(currentDirectory)
                                    (currentDirectory.contents[command[1]] as DirectoryNode)
                                }
                            }
                        }
                        command[0] == "ls" -> {
                            lines.subList(index + 1, lines.size)
                                .takeWhile { !it.startsWith("$") }
                                .map {
                                    val values = it.split(" ")
                                    when {
                                        values[0] == "dir" -> values[1] to DirectoryNode()
                                        else -> values[1] to FileNode(values[0].toLong())
                                    }
                                }
                                .forEach {
                                    currentDirectory.contents[it.first] = it.second
                                }
                        }
                        else -> throw RuntimeException("Unknown command")
                    }
                }
            }
        return rootDirectory
    }
}
