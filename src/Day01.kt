fun main() {
    fun parseToSums(input: List<String>) = input
        .joinToString("-")
        .split("--")
        .map { elf ->
            elf
                .split("-")
                .sumOf { it.toInt() }
        }
        .sorted()

    fun part1(input: List<String>): Int {
        return parseToSums(input)
            .takeLast(1)
            .first()
    }

    fun part2(input: List<String>): Int {
        return parseToSums(input)
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
