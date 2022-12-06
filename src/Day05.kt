fun main() {

    operator fun <E> List<E>.component6(): E = get(5)
    fun <E>ArrayDeque<E>.pop() = removeLast()
    fun <E>ArrayDeque<E>.push(element: E) = addLast(element)
    fun <E>ArrayDeque<E>.push(elements: List<E>) {
        elements.forEach { element -> push(element) }
    }

    fun String.toMovement(): Movement {
        val (_, amount, _, from, _, to) = split(" ")
        return Movement(amount, from, to)
    }

    fun part1(input: List<String>): String {
        val stacksStrings = input.filter { "[" in it }

        val stackRows: List<List<CharSequence>> = stacksStrings
            .map { it.chunked(4) { block -> block.trim() } }
            .reversed()

        val stacks: Array<ArrayDeque<CharSequence>> = Array(stackRows.maxOf { it.size }) {
            ArrayDeque()
        }

        stackRows.forEach { row ->
            row.forEachIndexed { index, crate ->
                if (crate.isNotBlank()) {
                    stacks[index].addLast(crate)
                }
            }
        }

        input
            .filter { "move" in it }
            .map { it.toMovement() }
            .forEach {
                val (amount, from, to) = it
                repeat(amount.toInt()) {
                    val popped = stacks[from.toInt() - 1].pop()
                    stacks[to.toInt() - 1].push(popped)
                }

            }

        return stacks
            .map { it.removeLast() }
            .map { it.toString() }
            .joinToString("") { it.replace("[", "").replace("]", "") }
    }

    fun part2(input: List<String>): String {
        val stacksStrings = input.filter { "[" in it }
        val moves = input.filter { "move" in it }

        val stackRows: List<List<CharSequence>> = stacksStrings
            .map { it.chunked(4) { block -> block.trim() } }
            .reversed()

        val stacks: Array<ArrayDeque<CharSequence>> = Array(stackRows.maxOf { it.size }) {
            ArrayDeque()
        }

        stackRows.forEach { row ->
            row.forEachIndexed { index, crate ->
                if (crate.isNotBlank()) {
                    stacks[index].addLast(crate)
                }
            }
        }

        moves
            .map { it.toMovement() }
            .forEach {
                val (amount, from, to) = it
                val fromStack = stacks[from.toInt() -1]
                val toStack = stacks[to.toInt() -1]
                val popped = fromStack.subList(fromStack.size - amount.toInt(), fromStack.size)
                toStack.push(popped)
                repeat(amount.toInt()) {
                    fromStack.pop()
                }
            }

        return stacks
            .map { it.removeLast() }
            .map { it.toString() }
            .joinToString("") { it.replace("[", "").replace("]", "") }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println("Part One: ${part1(input)}")
    check(part1(input) == "QGTHFZBHV")
    println("Part Two: ${part2(input)}")
    check(part2(input) == "MGDMPSZTM")
}

data class Movement(val amount: String, val from: String, val to: String)
