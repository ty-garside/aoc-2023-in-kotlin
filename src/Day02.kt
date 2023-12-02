fun main() {
    data class Reveal(
        val red: Int,
        val green: Int,
        val blue: Int,
    )

    data class Game(
        val id: Int,
        val reveals: List<Reveal>,
    )

    fun parseGame(line: String) =
        Game(id = line.substringBefore(": ").substringAfter(' ').toInt(), reveals = buildList {
            line.substringAfter(": ").split("; ").forEach { reveal ->
                var red = 0
                var green = 0
                var blue = 0
                reveal.split(", ").forEach { color ->
                    val count = color.substringBefore(' ').toInt()
                    when (color.substringAfter(' ')) {
                        "red" -> red += count
                        "green" -> green += count
                        "blue" -> blue += count
                    }
                }
                add(Reveal(red, green, blue))
            }
        })

    fun Game.isPossibleWith(red: Int, green: Int, blue: Int) = reveals.all {
        it.red <= red &&
            it.green <= green &&
            it.blue <= blue
    }

    fun part1(input: List<String>): Int {
        return input.map { parseGame(it) }
            .filter { it.isPossibleWith(12, 13, 14) }
//            .onEach { println(it) }
            .sumOf { it.id }
    }

    fun Game.computePower(): Int {
        val red = reveals.maxOf { it.red }
        val green = reveals.maxOf { it.green }
        val blue = reveals.maxOf { it.blue }
        return red * green * blue
    }

    fun part2(input: List<String>): Int {
        return input.map { parseGame(it) }
//            .onEach { println("$it: ${it.computePower()}") }
            .sumOf { it.computePower() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
