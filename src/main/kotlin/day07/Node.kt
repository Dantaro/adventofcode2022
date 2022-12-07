package day07

sealed class Node

class DirectoryNode(val contents: MutableMap<String, Node> = mutableMapOf()): Node()

class FileNode(val size: Long): Node()
