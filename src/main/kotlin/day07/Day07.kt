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
        return calculatedNodes.values.filter { it < 100000 }.sum()
    }

    override fun part2(): Int {
        val fileSystem = buildFilesystem()
        val calculatedNodes = calculateDirectorySizes(fileSystem)
        val usedSpace = 70_000_000 - calculatedNodes[fileSystem]!!
        val neededSpace = 30_000_000 - usedSpace
        return calculatedNodes.values.filter { it >= neededSpace }.minOf { it }
    }

    override fun url(): String = "https://adventofcode.com/2022/day/7"

    /**
     * This function calculates the size of all directories, it does so using a non-recursive DFS
     *
     * @return a Map of all directories and their size
     */
    private fun calculateDirectorySizes(fileSystem: DirectoryNode): MutableMap<DirectoryNode, Int> {
        // Our map of nodes we've fully calculated
        val calculatedNodes = mutableMapOf<DirectoryNode, Int>()
        // Map of nodes we need to check
        val discovered = mutableListOf<DirectoryNode>()
        // Start at the passed root node, we DFS from here
        discovered.add(fileSystem)
        // Run until we're out of nodes
        while (discovered.isNotEmpty()) {
            val currentNode = discovered.removeLast()
            // If we haven't fully calculated a node yet
            if (calculatedNodes[currentNode] == null) {
                val subdirs = currentNode
                    .contents
                    .values
                    .filterIsInstance<DirectoryNode>()
                // Check if we've fully calculated all the child directories yet
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
                    // If we haven't calculated everything yet, re-add this node. This is needed
                    // because we have to come back to it after we calculate all of it's children
                    discovered.add(currentNode)
                    // Add all the subdirectories afterwards, so they're calculated before this one.
                    discovered.addAll(subdirs)
                }
            }
        }
        return calculatedNodes
    }

    /**
     * This function builds our miniature file system.  Each element in the file system is
     * represented by a Node, either a DirectoryNode or a FileNode.  Directory nodes hold
     * maps of names to nodes, and files hold sizes.  We can later traverse this like a tree
     *
     * @return A DirectoryNode that acts are the Root directory of the file system
     */
    private fun buildFilesystem(): DirectoryNode {
        val rootDirectory = DirectoryNode()
        // This is the current path to the current directory
        // as we go down and up with cd we use this as a stack
        // to track the path
        val path = mutableListOf<DirectoryNode>()
        // This is the current directory our commands are functioning against
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
                            /*
                             * "cd" in our reduced instruction set supports only
                             * .. - Go up one directory
                             * / - Return to the root
                             * dirName - Go down one directory
                             * We don't allow things like ../dirName or dirName/dirName, or /dirName
                             * As a result we implement this in a very simplistic way, handling each
                             * instruction directly
                             */
                            currentDirectory = when {
                                command[1] == ".." -> {
                                    // Go pop off the most recent directory path, or go to the root
                                    // if we don't have anything to pop
                                    // this protects against someone doing cd .. at the root
                                    if (path.isEmpty()) { rootDirectory } else { path.removeLast() }
                                }
                                command[1] == "/" -> {
                                    // Clear out the path and return the root directory
                                    path.clear()
                                    rootDirectory
                                }
                                else -> {
                                    // Add the current directory to the path,
                                    path.add(currentDirectory)
                                    // then go to the requested directory
                                    (currentDirectory.contents[command[1]] as DirectoryNode)
                                }
                            }
                        }
                        command[0] == "ls" -> {
                            /*
                             * This is a kind of dumb way to do this, but we look at the remaining
                             * list starting at the current index + 1, then takes everything until we
                             * hit another command.
                             */
                            lines.subList(index + 1, lines.size)
                                .takeWhile { !it.startsWith("$") }
                                .map {
                                    val values = it.split(" ")
                                    // Directories start with dir
                                    // Files start with a size
                                    // There are no there options
                                    when {
                                        values[0] == "dir" -> values[1] to DirectoryNode()
                                        else -> values[1] to FileNode(values[0].toInt())
                                    }
                                }
                                .forEach {
                                    // Add everything to the current directories contents
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
