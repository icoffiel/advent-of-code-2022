fun main() {

    fun winLoseOrDraw(playerOne: ITEM, playerTwo: ITEM) = when (playerOne) {
        playerTwo -> OUTCOME.DRAW
        playerTwo.beats() -> OUTCOME.WIN
        else -> OUTCOME.LOSE
    }

    fun shouldPlay(playerOne: ITEM, expectedOutcome: OUTCOME): ITEM = when(expectedOutcome) {
        OUTCOME.LOSE -> playerOne.beats()
        OUTCOME.DRAW -> playerOne
        OUTCOME.WIN -> playerOne.beatenBy()
    }

    fun part1(input: List<String>): Int {
        val items = input
            .map {
                it.split(" ")
                    .map { letter -> ITEM.fromLetter(letter) }
            }

        val basePoints = items.sumOf { it[1].score }
        val winPoints = items.sumOf { winLoseOrDraw(it[0], it[1]).score }

        return basePoints + winPoints
    }

    fun part2(input: List<String>): Int {
        val itemToOutcomes = input
            .map {
                val strings = it.split(" ")
                ITEM.fromLetter(strings[0]) to OUTCOME.fromLetter(strings[1])
            }

        val winPoints = itemToOutcomes.sumOf { it.second.score }
        val basePoints = itemToOutcomes.sumOf { shouldPlay(it.first, it.second).score }

        return winPoints + basePoints
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    check(part1(input) == 11150)
    println(part2(input))
    check(part2(input) == 8295)
}

enum class ITEM(val letters: String, val score: Int) {
    ROCK("AX", 1),
    PAPER("BY", 2),
    SCISSORS("CZ", 3);

    companion object {
        fun fromLetter(letter: String): ITEM = ITEM.values().first { letter in it.letters }

    }
}

fun ITEM.beats(): ITEM = when(this) {
    ITEM.ROCK -> ITEM.SCISSORS
    ITEM.PAPER -> ITEM.ROCK
    ITEM.SCISSORS -> ITEM.PAPER
}

fun ITEM.beatenBy(): ITEM = when(this) {
    ITEM.ROCK -> ITEM.PAPER
    ITEM.PAPER -> ITEM.SCISSORS
    ITEM.SCISSORS -> ITEM.ROCK
}

enum class OUTCOME(val letters: String, val score: Int) {
    LOSE("X", 0),
    DRAW("Y", 3),
    WIN("Z", 6);

    companion object {
        fun fromLetter(letter: String): OUTCOME = OUTCOME.values().first { letter in it.letters }
    }
}
