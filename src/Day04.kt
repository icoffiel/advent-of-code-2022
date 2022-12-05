fun main() {

    fun part1(input: List<String>): Int {
       return input
           .map { it.toRangeList() }
           .count { (first, second) -> first.toSet() fullyContains second.toSet() }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toRangeList() }
            .count { (it[0] intersect it[1]).isNotEmpty() }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println("Part 1: ${part1(input)}")
    check(part1(input) == 453)
    println("Part 2: ${part2(input)}")
    check(part2(input) == 919)
}

/**
 * Checks if a Set of Int values is fully contained in another.
 * For instance:
 *  [1, 2, 3] is fully within [1, 2, 3, 4] (true)
 *  [1, 2] is fully within [2, 3] (false)
 */
private infix fun Set<Int>.fullyContains(second: Set<Int>): Boolean {
    val intersect = this intersect second
    return intersect.toSet() == this || intersect.toSet() == second
}

/**
 * Converts a String in the forms 1-2,3-4 into a list of IntRange.
 * Note that the String function substringBefore is also a good suggestion for this function instead of the split by ,
 */
private fun String.toRangeList(): List<IntRange> {
    val split = this.split(",", "-")
    val firstElf = split[0].toInt()..split[1].toInt()
    val secondElf = split[2].toInt() .. split[3].toInt()

    return listOf(firstElf, secondElf)
}
