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
                val entry = map.floorEntry(range.number)?.value
                if (entry == null || number >= entry.source + entry.length) {
                    add(Range(number, length))
                    break
                }
                minOf(entry.source - number, length).let {
                    add(Range(number, it))
                    number += it
                    length -= it
                }
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
        var line = next()
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

    fun part1(input: List<String>): Long {
        val file = input.iterator().readFile {
            map { Range(it, 1) }
        }
        println(file)
        return file.seeds
            .asSequence()
            .onEach(::println)
            .flatMap { file.seedToSoil.map(it) }
            .onEach(::println)
            .flatMap { file.soilToFertilizer.map(it) }
            .onEach(::println)
            .flatMap { file.fertilizerToWater.map(it) }
            .onEach(::println)
            .flatMap { file.waterToLight.map(it) }
            .onEach(::println)
            .flatMap { file.lightToTemperature.map(it) }
            .onEach(::println)
            .flatMap { file.temperatureToHumidity.map(it) }
            .onEach(::println)
            .flatMap { file.humidityToLocation.map(it) }
            .onEach(::println)
            .minOf { it.number }
    }

    fun part2(input: List<String>): Long {
        val file = input.iterator().readFile {
            chunked(2).map { Range(it[0], it[1]) }
        }
        TODO()
//        return file.seeds
//            .asSequence()
//            .chunked(2) {
//                it[0]..it[0] + it[1]
//            }
//            .flatten()
//            .map { file.seedToSoil[it] }
//            .map { file.soilToFertilizer[it] }
//            .map { file.fertilizerToWater[it] }
//            .map { file.waterToLight[it] }
//            .map { file.lightToTemperature[it] }
//            .map { file.temperatureToHumidity[it] }
//            .map { file.humidityToLocation[it] }
//            .min()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
// check(part2(testInput) == 46L)

    val input = readInput("Day05")
//    part1(input).println()
//    part2(input).println()
}
