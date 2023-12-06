import kotlin.math.pow

fun main() {

    data class Card(
        val id: Int,
        val winning: Set<Int>,
        val picks: Set<Int>,
    )

    fun parseCard(line: String) = Card(
        line.substringBefore(": ").substringAfterLast(" ").toInt(),
        line.substringAfter(": ").substringBefore(" | ").trim().split(Regex(" +")).map { it.toInt() }.toSet(),
        line.substringAfter(": ").substringAfter(" | ").trim().split(Regex(" +")).map { it.toInt() }.toSet(),
    )

    fun Card.score() = 2.0.pow(
        picks.count {
            winning.contains(it)
        } - 1.0
    ).toInt()

    fun part1(input: List<String>): Int {
        return input.map { parseCard(it) }
//            .onEach { println(it) }
            .sumOf { it.score() }
    }

    fun part2(input: List<String>): Int {
        val cardCounts = object : MutableMap<Int, Int> by mutableMapOf() {
            fun increment(key: Int) = this.compute(key) { _, value -> (value ?: 0) + 1 } ?: 0
        }

        for (card in input.map { parseCard(it) }) {
            val winCount = card.picks.count { card.winning.contains(it) }
            repeat(cardCounts.increment(card.id)) {
                for (id in card.id + 1..card.id + winCount) {
                    cardCounts.increment(id)
                }
            }
        }

        return cardCounts.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
