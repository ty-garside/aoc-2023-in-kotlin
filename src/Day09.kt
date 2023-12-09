fun main() {

    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map { it.split(' ').map(String::toInt) }
//            .onEach { println("before: $it") }
            .map { numbers ->
                val diff = mutableListOf(numbers)
                while (diff.last().any { it != 0 }) {
                    diff.add(diff.last().windowed(2) { it[1] - it[0] })
                }
//                println(diff)
                diff.sumOf { it.last() }
            }
//            .onEach { println("after: $it") }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { it.split(' ').map(String::toInt) }
//            .onEach { println("before: $it") }
            .map { numbers ->
                val diff = mutableListOf(numbers)
                while (diff.last().any { it != 0 }) {
                    diff.add(diff.last().windowed(2) { it[1] - it[0] })
                }
//                println(diff)
                diff.reverse()
                diff.map { it.first() }.reduce { acc: Int, i: Int -> i - acc }
            }
//            .onEach { println("after: $it") }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
