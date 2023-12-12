import kotlin.math.abs

fun main() {

    data class Galaxy(
        val row: Long,
        val col: Long
    )

    fun List<String>.findGalaxies() = flatMapIndexed { row, line ->
        line.withIndex()
            .filter { it.value == '#' }
            .map { Galaxy(row.toLong(), it.index.toLong()) }
    }

    fun List<Galaxy>.expandSpace(input: List<String>, factor: Long): List<Galaxy> {
        val expandRow = input.indices.filter { row ->
            input[row].all { it == '.' }
        }.toSet()

        val expandCol = input.first().indices.filter { col ->
            input.all { it[col] == '.' }
        }.toSet()

        return map { galaxy ->
            galaxy.copy(
                row = galaxy.row + (factor - 1) * expandRow.count { it < galaxy.row },
                col = galaxy.col + (factor - 1) * expandCol.count { it < galaxy.col },
            )
        }
    }

    fun <T> List<T>.mapPairs() = flatMapIndexed { index, each ->
        subList(index + 1, size).map {
            each to it
        }
    }

    fun Pair<Galaxy, Galaxy>.distance() =
        abs(second.row - first.row) + abs(second.col - first.col)

    fun part1(input: List<String>): Long {
        return input.findGalaxies()
            .expandSpace(input, 2)
            .mapPairs()
            .sumOf { it.distance() }
    }

    fun part2(input: List<String>, factor: Long = 1000000): Long {
        return input.findGalaxies()
            .expandSpace(input, factor)
            .mapPairs()
            .sumOf { it.distance() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374L)
    check(part2(testInput, 10L) == 1030L)
    check(part2(testInput, 100L) == 8410L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
