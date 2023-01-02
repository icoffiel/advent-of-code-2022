private const val DAY = "Day11"
fun main() {
    data class Test(val divisibleBy: Int, val throwToTrue: Int, val throwToFalse: Int)
    data class Operation(val operand: String, val value: String) {
        fun perform(itemValue: Long): Long {
            val parsedValue: Long = when (value) {
                "old" -> itemValue
                else -> value.toLong()
            }

            return when (operand) {
                "*" -> itemValue * parsedValue
                "+" -> itemValue + parsedValue
                else -> error("Unknown operand: $operand")
            }
        }
    }

    data class Monkey(
        val name: String,
        val items: MutableList<Long>,
        val operation: Operation,
        val test: Test,
        var timesCounted: Long = 0L
    ) {
        fun thrownTo(item: Long) {
            items.add(item)
        }
    }

    /**
     * Takes a string and converts it to the monkey name:
     *
     * Example input:
     * `Monkey 0:`
     *
     * Output:
     * `0`
     */
    fun String.toMonkeyName(): String = "[0-9]+"
        .toRegex()
        .find(this)
        ?.value ?: error("$this cannot be converted to name")

    /**
     * Takes a string in the following format and converts it to a list of Strings:
     *
     * Example input:
     * `  Starting items: 79, 98`
     *
     * Output:
     * `[79, 98]`
     */
    fun String.toStartingItems(): MutableList<Long> = this
        .replace("Starting items: ", "")
        .trim()
        .split(",")
        .map { it.trim() }
        .map { it.toLong() }
        .toMutableList()

    fun String.toDivisibleBy(): Int {
        return this
            .replace("Test: divisible by ", "")
            .trim()
            .toInt()
    }

    fun String.toThrowToTrue(): Int {
        return this
            .replace("If true: throw to monkey ", "")
            .trim()
            .toInt()
    }

    fun String.toThrowToFalse(): Int {
        return this
            .replace("If false: throw to monkey ", "")
            .trim()
            .toInt()
    }

    fun String.toOperation(): Operation {
        val (operand, value) = this
            .replace("Operation: new = old ", "")
            .trim()
            .split(" ")

        return Operation(operand, value)
    }

    fun toTest(testLine: String, trueLine: String, falseLine: String): Test {
        return Test(
            testLine.toDivisibleBy(),
            trueLine.toThrowToTrue(),
            falseLine.toThrowToFalse(),
        )
    }

    fun parse(input: String) = input
        .split("${System.lineSeparator()}${System.lineSeparator()}")
        .map { monkey ->
            val monkeyLines = monkey.lines()
            Monkey(
                monkeyLines[0].toMonkeyName(),
                monkeyLines[1].toStartingItems(),
                monkeyLines[2].toOperation(),
                toTest(monkeyLines[3], monkeyLines[4], monkeyLines[5])
            )
        }

    fun worryScore(monkeys: List<Monkey>): Long = monkeys
        .sortedByDescending { it.timesCounted }
        .take(2)
        .map { it.timesCounted }
        .reduce { acc, i -> acc * i }

    fun part1(input: String): Long {

        val monkeys: List<Monkey> = parse(input)

        repeat((0..19).count()) {
            monkeys.forEach { monkey ->
                val itemsIter = monkey.items.listIterator()

                while (itemsIter.hasNext()) {
                    val item = itemsIter.next()
                    val worryLevelIncrease = monkey.operation.perform(item)
                    val afterBored = worryLevelIncrease / 3
                    val thrownToMonkeyIndex = when (afterBored % monkey.test.divisibleBy) {
                        0L -> monkey.test.throwToTrue
                        else -> monkey.test.throwToFalse
                    }

                    monkeys[thrownToMonkeyIndex].thrownTo(afterBored)
                    itemsIter.remove()
                    monkey.timesCounted += 1L
                }
            }
        }

        return worryScore(monkeys)
    }

    fun part2(input: String): Long {
        val monkeys: List<Monkey> = parse(input)
        val commonModulus = monkeys.map { it.test.divisibleBy }.reduce(Int::times)

        repeat((0..9999).count()) {
            monkeys.forEach { monkey ->
                val itemsIter = monkey.items.listIterator()

                while (itemsIter.hasNext()) {
                    val item = itemsIter.next()
                    val worryLevelIncrease = monkey.operation.perform(item)
                    val afterBored = worryLevelIncrease % commonModulus
                    val thrownToMonkeyIndex = when (afterBored % monkey.test.divisibleBy) {
                        0L -> monkey.test.throwToTrue
                        else -> monkey.test.throwToFalse
                    }

                    monkeys[thrownToMonkeyIndex].thrownTo(afterBored)
                    itemsIter.remove()
                    monkey.timesCounted += 1
                }
            }
        }

        return worryScore(monkeys)
    }

    val testInput = readInputAsText("${DAY}_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInputAsText(DAY)
    println("Part One: ${part1(input)}")
    check(part1(input) == 120056L)
    println("Part Two: ${part2(input)}")
    check(part2(input) == 21816744824)
}
