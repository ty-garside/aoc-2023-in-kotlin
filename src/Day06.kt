fun main() {

    data class Race(
        val time: Long,
        val distance: Long
    )

    fun parseInput(input: List<String>) = buildList {

        val times = input[0].substringAfter(":")
            .trim()
            .split(Regex(" +"))
            .map { it.toLong() }

        val distances = input[1].substringAfter(":")
            .trim()
            .split(Regex(" +"))
            .map { it.toLong() }

        times.zip(distances).forEach { (time, distance) ->
            add(Race(time, distance))
        }
    }

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .map { race ->
                (1..<race.time)
                    .map { it * (race.time - it) }
                    .count { it > race.distance }
            }
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        return part1(input.map { it.replace(" ", "") })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
