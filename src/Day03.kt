fun main() {

    data class Token(
        val id: String,
        val line: Int,
        val left: Int,
        val right: Int,
    ) {
        val isDigit = id.first().isDigit()
        val isSymbol = !isDigit
    }

    val tokenPattern = Regex("(\\d+|[^\\d.])")

    fun Sequence<String>.tokenize() = mapIndexed { index, line ->
        tokenPattern.findAll(line)
            .map { Token(it.value, index, it.range.first, it.range.last) }
            .toList()
    }

    fun Sequence<List<Token>>.findAdjacent(
        distance: Int = 1,
        filter: (token: Token, candidate: Token) -> Boolean = { _, _ -> true }
    ) = sequence {
        sequence<List<Token>> {
            repeat(distance) { yield(listOf()) }
            forEach { yield(it) }
            repeat(distance) { yield(listOf()) }
        }.windowed(distance * 2 + 1, 1).forEach { window ->
            for (token in window[distance]) {
                val left = token.left - distance
                val right = token.right + distance
                val nearby = window.asSequence()
                    .flatten()
                    .filter { it !== token }
                    .filter { it.right >= left }
                    .filter { it.left <= right }
                    .filter { filter(token, it) }
                    .toList()
                if (nearby.isNotEmpty()) {
                    yield(token to nearby)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input.asSequence()
            .tokenize()
            .findAdjacent { token, candidate -> token.isDigit && candidate.isSymbol }
//            .onEach { println(it) }
            .sumOf { it.first.id.toInt() }
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .tokenize()
            .findAdjacent { token, candidate -> token.id == "*" && candidate.isDigit }
            .filter { it.second.size == 2 }
//            .onEach { println(it) }
            .sumOf { it.second[0].id.toInt() * it.second[1].id.toInt() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
