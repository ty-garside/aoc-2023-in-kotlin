fun main() {

    fun part1(input: List<String>): Int {
        val moves = input[0]
        val nodes = input.subList(2, input.size).associate {
            it.substringBefore(" = ") to
                it.substringAfter(" = ")
                    .removeSurrounding("(", ")")
                    .split(", ")
                    .let { (left, right) ->
                        left to right
                    }
        }

        var node = "AAA"
        var path = nodes["AAA"]!!
        var move = 0
        var count = 0

        while (node != "ZZZ") {
            println("node=$node path=$path move=$move moves[move]=${moves[move]} count=$count")
            node = when (moves[move]) {
                'L' -> path.first
                'R' -> path.second
                else -> error("?")
            }
            path = nodes[node]!!
            move = (move + 1) % moves.length
            count++
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val moves = input[0]
        val nodes = input.subList(2, input.size).associate {
            it.substringBefore(" = ") to
                it.substringAfter(" = ")
                    .removeSurrounding("(", ")")
                    .split(", ")
                    .let { (left, right) ->
                        left to right
                    }
        }

        var node = nodes.keys.filter { it.endsWith('A') }
        var path = node.map { nodes[it]!! }
        var move = 0
        var count = 0

        while (node.any { !it.endsWith('Z') }) {
            if (node.count { it.endsWith('Z') } > 3) {
                println("node=$node path=$path move=$move moves[move]=${moves[move]} count=$count")
            }
            node = when (moves[move]) {
                'L' -> path.map { it.first }
                'R' -> path.map { it.second }
                else -> error("?")
            }
            path = node.map { nodes[it]!! }
            move = (move + 1) % moves.length
            count++
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    val testInput3 = readInput("Day08_test3")
//    check(part1(testInput) == 2)
//    check(part1(testInput2) == 6)
    check(part2(testInput3) == 6)

    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}
