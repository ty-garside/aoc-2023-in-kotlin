import java.util.*

fun main() {

    data class Range(
        val number: Long,
        val length: Long,
    )

    data class RangeEntry(
        val destination: Long,
        val source: Long,
        val length: Long,
    )

    class RangeMap(
        entries: Collection<RangeEntry>,
    ) {
        private val map = TreeMap(
            entries.associateBy { it.source }
        )

        fun map(range: Range): List<Range> = buildList {
            var number = range.number
            var length = range.length
            while (length > 0) {
                val entry = map.floorEntry(number)?.value
                if (entry == null || number >= entry.source + entry.length) {
                    add(Range(number, length))
                    break
                }
                if (number + length <= entry.source + entry.length) {
                    add(Range(number - entry.source + entry.destination, length))
                    break
                }
                val newNumber = number - entry.source + entry.destination
                val newLength = entry.length - number + entry.source
                add(Range(newNumber, newLength))
                number += newLength
                length -= newLength
            }
        }
    }

    data class File(
        val seeds: List<Range>,
        val seedToSoil: RangeMap,
        val soilToFertilizer: RangeMap,
        val fertilizerToWater: RangeMap,
        val waterToLight: RangeMap,
        val lightToTemperature: RangeMap,
        val temperatureToHumidity: RangeMap,
        val humidityToLocation: RangeMap,
    )

    fun Iterator<String>.readList(name: String) = buildList {
        val line = next()
        if (!line.startsWith("$name: ")) error("Invalid line: $line")
        if (next().isNotBlank()) error("Blank line expected")
        line.substringAfter(": ")
            .split(" ")
            .map { it.toLong() }
            .forEach(::add)
    }

    fun Iterator<String>.readMap(name: String) = RangeMap(buildList {
        var line = next()
        if (line != "$name map:") error("Invalid line: $line")
        while (true) {
            line = if (hasNext()) next() else ""
            if (line.isBlank()) break
            val nums = line.split(" ").map { it.toLong() }
            add(RangeEntry(nums[0], nums[1], nums[2]))
        }
    })

    fun Iterator<String>.readFile(toRanges: List<Long>.() -> List<Range>): File {
        return File(
            readList("seeds").toRanges(),
            readMap("seed-to-soil"),
            readMap("soil-to-fertilizer"),
            readMap("fertilizer-to-water"),
            readMap("water-to-light"),
            readMap("light-to-temperature"),
            readMap("temperature-to-humidity"),
            readMap("humidity-to-location"),
        )
    }

    fun File.findMinLocation() = seeds.asSequence()
        .flatMap { seedToSoil.map(it) }
        .flatMap { soilToFertilizer.map(it) }
        .flatMap { fertilizerToWater.map(it) }
        .flatMap { waterToLight.map(it) }
        .flatMap { lightToTemperature.map(it) }
        .flatMap { temperatureToHumidity.map(it) }
        .flatMap { humidityToLocation.map(it) }
        .onEach { println(it) }
        .minOf { it.number }

    fun part1(input: List<String>): Long {
        return input.iterator()
            .readFile { map { Range(it, 1) } }
            .findMinLocation()
    }

    fun part2(input: List<String>): Long {
        return input.iterator()
            .readFile { chunked(2).map { Range(it[0], it[1]) } }
            .findMinLocation()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
