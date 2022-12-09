fun main() {

    class Node(
        val name: String,
        var size: Int,
        val parent: Node? = null,
        val children: MutableList<Node> = mutableListOf()
    )

    fun directoryList(node: Node): List<Node> {
        return listOf(node) + node.children.map { directoryList(it) }.flatten()
    }

    fun calculateDirectorySize(node: Node): Int {
        if (node.children.isEmpty()) {
            return node.size
        }

        return node.size + node.children.sumOf { calculateDirectorySize(it) }
    }

    fun part1(input: List<String>): Int {
        val rootNode = Node(name = "/", size = 0)
        var currentNode = rootNode
        input
            .filter { !it.startsWith("$ ls") }
            .forEach {
                when {
                    it.startsWith("$ cd") -> {
                        val splits = it.split(" ")

                        currentNode = when(val target = splits.last()) {
                            "/" -> rootNode
                            ".." -> currentNode.parent ?: rootNode
                            else -> currentNode.children.first { child -> child.name == target }
                        }
                    }
                    it.startsWith("dir") -> {
                        val (_, name) = it.split(" ")
                        currentNode.children.add(Node(name = name, size = 0, parent = currentNode))
                    }
                    else -> {
                        val (size, _) = it.split(" ")
                        currentNode.size += size.toInt()
                    }
                }
            }
        return directoryList(rootNode)
            .filter { it.name != "/" }
            .map { calculateDirectorySize(it) }
            .filter { it < 100000 }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val rootNode = Node(name = "/", size = 0)
        var currentNode = rootNode
        input
            .filter { !it.startsWith("$ ls") }
            .forEach {
                when {
                    it.startsWith("$ cd") -> {
                        val splits = it.split(" ")

                        currentNode = when(val target = splits.last()) {
                            "/" -> rootNode
                            ".." -> currentNode.parent ?: rootNode
                            else -> currentNode.children.first { child -> child.name == target }
                        }
                    }
                    it.startsWith("dir") -> {
                        val (_, name) = it.split(" ")
                        currentNode.children.add(Node(name = name, size = 0, parent = currentNode))
                    }
                    else -> {
                        val (size, _) = it.split(" ")
                        currentNode.size += size.toInt()
                    }
                }
            }
        val unusedSpace = 70000000 - calculateDirectorySize(rootNode)
        return directoryList(rootNode)
            .filter { it.name != "/" }
            .map { calculateDirectorySize(it) }
            .filter { unusedSpace + it > 30000000 }
            .min()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println("Part One: ${part1(input)}")
    check(part1(input) == 1243729)
    println("Part Two: ${part2(input)}")
    check(part2(input) == 4443914)
}
