fun main() {

    data class Hand(
        val cards: String,
        val bid: Int,
    )

    class Rules(
        cardOrder: String,
        val joker: Char = '_',
    ) {
        private val cardValue = cardOrder
            .mapIndexed { index, ch -> ch to index }
            .associate { it }

        fun cardValue(ch: Char) = cardValue[ch]!!

        fun handType(cards: String): Int = buildSet {
            if (cards.contains(joker)) {
                cardValue.keys.minus(joker).forEach {
                    add(cards.replace(joker, it))
                }
            } else {
                add(cards)
            }
        }.maxOf { hand ->
            val counts = hand
                .groupingBy { it }
                .eachCount()
                .values
                .sortedDescending()
            when (counts) {
                listOf(5) -> 6
                listOf(4, 1) -> 5
                listOf(3, 2) -> 4
                listOf(3, 1, 1) -> 3
                listOf(2, 2, 1) -> 2
                listOf(2, 1, 1, 1) -> 1
                listOf(1, 1, 1, 1, 1) -> 0
                else -> error("Invalid hand: $hand => $counts")
            }
        }
    }

    fun List<String>.parseInput() = map {
        Hand(
            it.substringBefore(' '),
            it.substringAfter(' ').toInt()
        )
    }

    fun handComparator(rules: Rules) = Comparator<Hand> { o1, o2 ->
        var c = rules.handType(o1.cards).compareTo(rules.handType(o2.cards))
        var i = 0
        while (c == 0 && i < 5) {
            c = rules.cardValue(o1.cards[i]).compareTo(rules.cardValue(o2.cards[i]))
            i++
        }
        c
    }

    fun part1(input: List<String>): Int {
        return input.parseInput()
            .sortedWith(handComparator(Rules("23456789TJQKA")))
            .mapIndexed { rank, hand -> hand.bid * (rank + 1) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.parseInput()
            .sortedWith(handComparator(Rules("J23456789TQKA", 'J')))
            .mapIndexed { rank, hand -> hand.bid * (rank + 1) }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
