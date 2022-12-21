import kotlin.math.ceil

private const val DAY = "Day10"
fun main() {
    fun List<String>.parse() = map {
            when {
                it.startsWith("noop") -> Command.NoOp()
                it.startsWith("addx") -> Command.AddX(it.split(" ")[1].toInt())
                else -> error("Invalid command: $it")
            }
        }
        .flatMap {
            when (it) {
                is Command.AddX -> listOf<Command>(it, it.copy(numberOfOps = it.numberOfOps - 1))
                is Command.NoOp -> listOf(it)
            }
        }
        .fold(listOf(State())) { acc: List<State>, command ->
            when (command) {
                is Command.AddX -> {
                    when (command.numberOfOps) {
                        1 -> acc + State(
                            x = acc.last().x + command.number,
                            command = command,
                            xDuring = acc.last().x,
                            cycle = acc.last().cycle + 1
                        )

                        else -> acc + State(
                            x = acc.last().x,
                            command = command,
                            xDuring = acc.last().x,
                            cycle = acc.last().cycle + 1
                        )
                    }
                }

                is Command.NoOp -> acc + State(
                    x = acc.last().x,
                    command = command,
                    xDuring = acc.last().x,
                    cycle = acc.last().cycle + 1
                )
            }
        }

    /**
     * Work out the row that something falls on given the amount of columns (adjusted for 0 based index)
     */
    fun Int.cycleRow(width: Int = 40): Int {
        return if (this == 0) {
            0
        } else {
            ceil(this.toDouble() / width).toInt() - 1
        }
    }

    /**
     * Work out if the register overlaps given a column width.
     * Register takes up 3 spaces, the current register pointer, 1 before, and 1 after
     * Register does not wrap to the next line
     */
    fun Int.overlaps(register: Int, columns: Int = 40): Boolean {
        val modReg = register % columns
        return (this % columns) in modReg - 1..modReg + 1
    }

    /**
     * Create a mutable grid of a certain size with an initial default value in each
     */
    fun createGrid(rows: Int, columns: Int, defaultValue: String) = Array(rows) {
        Array(columns) { defaultValue }.toMutableList()
    }.toMutableList()

    fun part1(input: List<String>): Int {
        return input
            .parse()
            .filter { it.cycle in listOf(20, 60, 100, 140, 180, 220) }
            .sumOf { it.xDuring * it.cycle }
    }


    fun MutableList<MutableList<String>>.printGrid() {
        forEach { row ->
                row.forEachIndexed { index, column ->
                    print(String.format("%2s", column))
                    if (index == row.size - 1) {
                        println()
                    }
                }
            }
    }

    fun part2(input: List<String>): Int {
        val crt: MutableList<MutableList<String>> = createGrid(6, 40, "")

        input
            .parse()
            .forEach { cycle ->
                val cycleRow = cycle.cycle.cycleRow()
                val column = cycle.cycle % 40
                when {
                    cycle.x.overlaps(cycle.cycle) -> crt[cycleRow][column] = "#"
                    else -> crt[cycleRow][column] = "."
                }

            }

        crt.printGrid()

        return input.size
    }

    val testInput = readInput("${DAY}_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 146)

    val input = readInput(DAY)
    println("Part One: ${part1(input)}")
    check(part1(input) == 15140)
    println("Part Two: ${part2(input)}")
    check(part2(input) == 137)
}

sealed class Command {
    abstract var numberOfOps: Int

    data class NoOp(override var numberOfOps: Int = 1) : Command()
    data class AddX(val number: Int, override var numberOfOps: Int = 2) : Command()
}

data class State(val cycle: Int = 0, val x: Int = 1, val command: Command = Command.NoOp(), val xDuring: Int = 1)