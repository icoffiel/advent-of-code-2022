fun main() {
    fun parseToSums(input: String) = input
        .split("${System.lineSeparator()}${System.lineSeparator()}")
        .map { elf ->
            elf
                .lines()
                .sumOf { it.toInt() }
        }
        .sorted()

    fun part1(input: String): Int {
        return parseToSums(input)
            .takeLast(1)
            .first()
    }

    fun part2(input: String): Int {
        return parseToSums(input)
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInputAsText("Day01")
    check(part1(input) == 69281)
    println(part1(input))
    check(part2(input) == 201524)
    println(part2(input))
}
