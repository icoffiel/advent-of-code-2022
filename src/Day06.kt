private const val DIFFERENCE_TO_UNIQUE_INDEX_START = 1

fun main() {
    fun firstNonUniqueStrings(input: String, uniqueStringLength: Int) = input
        .windowed(uniqueStringLength) // Move over the expected length at a time
        .takeWhile { it.toSet().size != uniqueStringLength }

    fun part1(input: String): Int {
        val uniqueStringLength = 4
        val previousNonUniqueStrings: List<String> = firstNonUniqueStrings(input, uniqueStringLength)

        return input.indexOf(previousNonUniqueStrings.last()) + DIFFERENCE_TO_UNIQUE_INDEX_START + uniqueStringLength
    }

    fun part2(input: String): Int {
        val uniqueStringLength = 14
        val previousNonUniqueStrings: List<String> = firstNonUniqueStrings(input, uniqueStringLength)

        return input.indexOf(previousNonUniqueStrings.last()) + DIFFERENCE_TO_UNIQUE_INDEX_START + uniqueStringLength
    }

    val testInput = readInputAsText("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInputAsText("Day06")
    println("Part One: ${part1(input)}")
    check(part1(input) == 1623)
    println("Part Two: ${part2(input)}")
    check(part2(input) == 3774)
}