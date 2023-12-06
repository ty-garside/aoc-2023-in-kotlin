import kotlin.math.pow

fun main() {

    data class Card(
        val id: Int,
        val winning: Set<Int>,
        val picks: Set<Int>,
        val score: Int = 2.0.pow(
            picks.count {
                winning.contains(it)
            } - 1.0
        ).toInt()
    )

    fun parseCard(line: String) = Card(
        line.substringBefore(": ").substringAfterLast(" ").toInt(),
        line.substringAfter(": ").substringBefore(" | ").trim().split(Regex(" +")).map { it.toInt() }.toSet(),
        line.substringAfter(": ").substringAfter(" | ").trim().split(Regex(" +")).map { it.toInt() }.toSet(),
    )

    fun part1(input: List<String>): Int {
        return input.map { parseCard(it) }.onEach { println(it) }.sumOf { it.score }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == 0)

    val input = readInput("Day04")
    part1(input).println()
//    part2(input).println()
}
