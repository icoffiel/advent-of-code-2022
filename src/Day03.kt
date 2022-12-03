private const val UPPERCASE_ASCII_RESET = 38
private const val LOWERCASE_ASCII_RESET = 96

fun main() {

    fun Char.toPriority() = when {
        isUpperCase() -> code - UPPERCASE_ASCII_RESET
        isLowerCase() -> code - LOWERCASE_ASCII_RESET
        else -> error("Unexpected condition")
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.chunked(it.length / 2) }
            .flatMap { (first, second) -> first.toSet() intersect second.toSet() }
            .sumOf { it.toPriority() }
    }

    fun part2(input: List<String>): Int {
        return input
            .chunked(3)
            .flatMap { (first, second, third) -> first.toSet() intersect second.toSet() intersect third.toSet() }
            .sumOf { it.toPriority() }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    check(part1(input) == 8085)
    println(part2(input))
    check(part2(input) == 2515)
}