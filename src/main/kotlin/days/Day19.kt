package days

import kotlin.math.absoluteValue

@AdventOfCodePuzzle(
    name = "Beacon Scanner",
    url = "https://adventofcode.com/2021/day/19",
    date = Date(day = 19, year = 2021)
)
class Day19(val input: String) : Puzzle {
    private val scanners = input.parse()
    private val alignedScanner: List<Scanner> by lazy { scanners.align() }

    override fun partOne() =
        alignedScanner.flatMap { it.beacons }.toSet().size

    override fun partTwo() =
        alignedScanner.flatMap { i ->
            alignedScanner.map { j -> i to j }
        }
            .filterNot { (i, j) -> i == j }
            .maxOf { (i, j) -> i.offset.manhattanDistance(j.offset) }

    private fun String.parse() = this
        .split("\n\n")
        .map { section ->
            val lines = section.lines()
            val id = lines.first().substringAfter("--- scanner ").substringBefore(" ---").toInt()
            val beacons = lines.drop(1)
                .filter(String::isNotEmpty)
                .map { line -> Beacon.from(line) }
                .toSet()
            Scanner(id, beacons)
        }

    private fun List<Scanner>.align() = buildList<Scanner> {
        val unaligned = this@align.toMutableList()
        add(unaligned.removeFirst())

        loop@ while (unaligned.isNotEmpty()) {
            for (next in unaligned)
                for (aligned in this) {
                    val match = aligned.match(next)
                    if (match != null) {
                        add(match)
                        unaligned.remove(next)
                        continue@loop
                    }
                }
            error("No match found")
        }
    }

    data class Scanner(val id: Int, val beacons: Set<Beacon>, val offset: Beacon = Beacon(0, 0, 0)) {

        override fun toString(): String {
            return "Scanner #$id beacons=${beacons.size} offset=$offset"
        }

        fun rotate() = sequence {
            yield(beacons)
            // @formatter:off
            listOf(
                "X", "Y", "Z", "XX", "XY", "XZ", "YX", "YY", "ZY", "ZZ",
                "XXX", "XXY", "XXZ", "XYX", "XYY", "XZZ", "YXX", "YYY", "ZZZ",
                "XXXY", "XXYX", "XYXX", "XYYY"
            )
                .forEach {
                    yield(
                        it.fold(beacons) { p, c ->
                            when (c) {
                                'X' -> p.map { it.rotateX() }.toSet()
                                'Y' -> p.map { it.rotateY() }.toSet()
                                'Z' -> p.map { it.rotateZ() }.toSet()
                                else -> error("")
                            }
                        })
                }
        }

        fun match(other: Scanner): Scanner? {
            for (beacon in beacons) {
                for (rotatedBeacons in other.rotate()) {
                    for (rotatedBeacon in rotatedBeacons) {
                        val offset = beacon - rotatedBeacon
                        val shiftedBeacons = rotatedBeacons.map { it + offset }.toSet()
                        if ((beacons intersect shiftedBeacons).count() >= 12) {
                            println("Scanner $id matches scanner ${other.id}")
                            println("vector = $offset")
                            return other.copy(beacons = shiftedBeacons, offset = offset)
                        }
                    }
                }
            }
            return null
        }
    }

    data class Beacon(val x: Int, val y: Int, val z: Int) {
        fun rotateX() = Beacon(x, -z, y)
        fun rotateY() = Beacon(z, y, -x)
        fun rotateZ() = Beacon(y, -x, z)

        fun manhattanDistance(other: Beacon) =
            (x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue

        operator fun plus(other: Beacon) =
            Beacon(x + other.x, y + other.y, z + other.z)

        operator fun minus(other: Beacon) =
            Beacon(x - other.x, y - other.y, z - other.z)

        companion object {
            fun from(line: String) = line.split(',')
                .map(String::toInt)
                .let { (x, y, z) -> Beacon(x, y, z) }
        }
    }
}