fun main() {

    val cardValue = mapOf(
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 14,
    )

    data class Hand(
        val cards: String,
        val bid: Int
    ) {
        fun type(): Int {
            val counts = cards.map { cardValue[it]!! }
                .groupingBy { it }
                .eachCount()
                .values
                .sortedDescending()
            return when (counts) {
                listOf(5) -> 6
                listOf(4, 1) -> 5
                listOf(3, 2) -> 4
                listOf(3, 1, 1) -> 3
                listOf(2, 2, 1) -> 2
                listOf(2, 1, 1, 1) -> 1
                listOf(1, 1, 1, 1, 1) -> 0
                else -> error("Invalid hand: $cards => $counts")
            }
        }
    }

    val handComparator = Comparator<Hand> { o1, o2 ->
        var c = o1.type().compareTo(o2.type())
        var i = 0
        while (c == 0 && i < 5) {
            c = cardValue[o1.cards[i]]!!.compareTo(cardValue[o2.cards[i]]!!)
            i++
        }
        c
    }

    fun List<String>.parseInput() = map {
        Hand(it.substringBefore(' '), it.substringAfter(' ').toInt())
    }

    fun part1(input: List<String>): Int {
        return input.parseInput()
            .sortedWith(handComparator)
            .mapIndexed { i,it->
                it.bid * (i + 1)
            }
            .sum()
//            .map { it.type() }
//            .forEachIndexed { i,it -> println("$i: $it") }
//        TODO()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
//    check(part2(testInput) == 71503)

    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}
