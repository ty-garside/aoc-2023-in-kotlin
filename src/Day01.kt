fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val tensDigit = line.first { it.isDigit() }.digitToInt()
            val onesDigit = line.last { it.isDigit() }.digitToInt()
            tensDigit * 10 + onesDigit
        }
    }

    val numberMap = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    val numberKeys = numberMap.keys

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val tensDigit = numberMap[line.findAnyOf(numberKeys)?.second]!!
            val onesDigit = numberMap[line.findLastAnyOf(numberKeys)?.second]!!
            tensDigit * 10 + onesDigit
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
