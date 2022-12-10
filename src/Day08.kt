private const val DAY = "Day08"
fun main() {

    fun <T> Iterable<T>.takeWhileInclusive(
        predicate: (T) -> Boolean
    ): List<T> {
        var shouldContinue = true
        return takeWhile {
            val result = shouldContinue
            shouldContinue = predicate(it)
            result
        }
    }

    fun part1(input: List<String>): Int {
        val grid: List<List<Int>> = input
            .map { row ->
                row.map { it.digitToInt() }
            }

        val visibilityMatrix: List<List<Boolean>> = grid.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, column ->

                val sizeToBeat = column

                when {
                    rowIndex == 0 -> true // at the top of the grid
                    rowIndex == grid.size - 1 -> true // at the bottom of the grid
                    columnIndex == 0 -> true
                    columnIndex == grid[0].size - 1 -> true
                    else -> {
                        val rowLeftBeat: Boolean = (0 until columnIndex)
                            .map {
                                when {
                                    grid[rowIndex][it] < sizeToBeat -> true
                                    else -> false
                                }
                            }
                            .all { it }
                        val rowRightBeat = (columnIndex + 1 until row.size)
                            .map {
                                when {
                                    grid[rowIndex][it] < sizeToBeat -> true
                                    else -> false
                                }
                            }
                            .all { it }

                        val columnUpBeat = (0 until rowIndex)
                            .map {
                                when {
                                    grid[it][columnIndex] < sizeToBeat -> true
                                    else -> false
                                }
                            }
                            .all { it }

                        val columnDownBeat = (rowIndex + 1 until grid.size)
                            .map {
                                when {
                                    grid[it][columnIndex] < sizeToBeat -> true
                                    else -> false
                                }
                            }
                            .all { it }

                        when {
                            rowLeftBeat || rowRightBeat -> true // check if visible along the row
                            columnUpBeat || columnDownBeat -> true // check if visible along the column
                            else -> false // otherwise return false
                        }
                    }
                }

            }
        }
        return visibilityMatrix
            .flatten()
            .count { it }
    }

    fun part2(input: List<String>): Int {
        val grid: List<List<Int>> = input
            .map { row ->
                row.map { it.digitToInt() }
            }

        val visibilityMatrix: List<List<Int>> = grid.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, column ->
                val currentValue = column

                val rowLeftScan = when (columnIndex) {
                    0 -> 0
                    else -> {
                        (columnIndex - 1 downTo 0)
                            .map { grid[rowIndex][it] }
                            .takeWhileInclusive { it < currentValue }
                            .count()
                    }
                }

                val rowRightBeat = when (columnIndex) {
                    row.size -> 0
                    else -> {
                        (columnIndex + 1 until row.size)
                            .map { grid[rowIndex][it] }
                            .takeWhileInclusive { it < currentValue }
                            .count()
                    }
                }

                val columnUpBeat = when (rowIndex) {
                    0 -> 0
                    else -> {
                        (rowIndex - 1 downTo 0)
                            .map { grid[it][columnIndex] }
                            .takeWhileInclusive { it < currentValue }
                            .count()
                    }
                }

                val columnDownBeat = when (rowIndex) {
                    grid.size -> 0
                    else -> {
                        (rowIndex + 1 until grid.size)
                            .map { grid[it][columnIndex] }
                            .takeWhileInclusive { it < currentValue }
                            .count()
                    }
                }

                rowLeftScan * rowRightBeat * columnUpBeat * columnDownBeat
            }
        }
        return visibilityMatrix
            .flatten()
            .max()

    }

    val testInput = readInput("${DAY}_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput(DAY)
    println("Part One: ${part1(input)}")
    check(part1(input) == 1676)
    println("Part Two: ${part2(input)}")
    check(part2(input) == 313200)
}