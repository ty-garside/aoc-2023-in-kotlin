fun main() {

    data class Tile(val row: Int, val col: Int) {
        fun north() = Tile(row - 1, col)
        fun south() = Tile(row + 1, col)
        fun east() = Tile(row, col + 1)
        fun west() = Tile(row, col - 1)
    }

    val validNorth = setOf('|', '7', 'F', 'S')
    val validSouth = setOf('|', 'J', 'L', 'S')
    val validEast = setOf('-', '7', 'J', 'S')
    val validWest = setOf('-', 'F', 'L', 'S')

    class Grid(val input: List<String>) {

        val start = input.indexOfFirst { it.contains('S') }.let {
            Tile(it, input[it].indexOf('S'))
        }

        val loop = buildSet {
            var tile = start
            while (true) {
                add(tile)
//                println("${get(tile)} ${tile.row + 1} ${tile.col + 1}")
                tile = paths(tile)
                    .firstOrNull { !contains(it) }
                    ?: break
            }
        }

        fun paths(from: Tile) = buildList {
            if (get(from.north()) in validNorth && get(from) in validSouth) {
                add(from.north())
            }
            if (get(from.south()) in validSouth && get(from) in validNorth) {
                add(from.south())
            }
            if (get(from.east()) in validEast && get(from) in validWest) {
                add(from.east())
            }
            if (get(from.west()) in validWest && get(from) in validEast) {
                add(from.west())
            }
        }

        operator fun get(tile: Tile) = get(tile.row, tile.col)

        operator fun get(row: Int, col: Int): Char = if (
            row in input.indices &&
            col in input[row].indices
        ) {
            input[row][col]
        } else {
            '.' // avoids grid boundaries
        }
    }

    fun part1(input: List<String>): Int {
        val grid = Grid(input)
        return grid.loop.size / 2
    }

    fun countInside(grid: Grid, line: List<Tile>): Int {
        println("countInside: $line")

        println("input: ${grid.input[line.first().row]}")

        val intersections = line
//            .asSequence()
            .flatMap {
                buildList {
                    val ch = grid[it]
                    val wc = ch in validEast && grid[it.west()] in validWest
                    val ec = ch in validWest && grid[it.east()] in validEast
                    if (!wc) add(it)
                    if (!ec) add(it)
                }
            }
            .chunked(2)

        println(intersections)

        var count = 0
        var inside = false
        for((left,right) in intersections.zipWithNext()) {
            val ch0 = grid[left[0]]
            val nc0 = ch0 in validSouth && grid[left[0].north()] in validNorth
            val sc0 = ch0 in validNorth && grid[left[0].south()] in validSouth
            val ch1 = grid[left[1]]
            val nc1 = ch1 in validSouth && grid[left[1].north()] in validNorth
            val sc1 = ch1 in validNorth && grid[left[1].south()] in validSouth
            if((nc0 && sc1) || (sc0 && nc1)) {
                inside = !inside
            }
            println("inside: $inside $left $right")
            if(inside) {
                count += right[0].col - left[1].col - 1
            }
        }

        println("count: $count")

        return count


//        var count = 0
//        var last = line.next()
//        while (line.hasNext()) {
//            var next = line.n
//        }

//        for (col in line.first().col..line.last().col)
//            return count
        TODO()
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)

        return grid.loop
            .groupBy { it.row }
            .map { it.value.sortedBy(Tile::col) }
            .sumOf { countInside(grid, it) }
            .also { println("sum: $it") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val testInput2 = readInput("Day10_test2")
    val testInput3 = readInput("Day10_test3")
    val testInput4 = readInput("Day10_test4")
    val testInput5 = readInput("Day10_test5")
    val testInput6 = readInput("Day10_test6")
//    check(part1(testInput) == 4)
//    check(part1(testInput2) == 8)
//    check(part2(testInput) == 1)
    check(part2(testInput2) == 1)
    check(part2(testInput3) == 4)
    check(part2(testInput4) == 4)
    check(part2(testInput5) == 8)
    check(part2(testInput6) == 10)

    val input = readInput("Day10")
//    part1(input).println()
    part2(input).println()
}
