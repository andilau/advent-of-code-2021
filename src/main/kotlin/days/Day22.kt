package days

import kotlin.math.max
import kotlin.math.min


@AdventOfCodePuzzle(
    name = "Reactor Reboot",
    url = "https://adventofcode.com/2021/day/22",
    date = Date(day = 22, year = 2021)
)
class Day22(private val instructions: List<String>) : Puzzle {

    override fun partOne() = instructions
        .filter { it.isNotEmpty() }
        .map { Instruction.parse(it) }
        .filter { it.volume.inRange(-50..50) }
        .fold(mutableMapOf(), ::intersectVolumeAndMerge)
        .entries.sumOf { (volume, times) -> volume.size() * times }

    override fun partTwo() = instructions
        .filter { it.isNotEmpty() }
        .map { Instruction.parse(it) }
        .fold(mutableMapOf(), ::intersectVolumeAndMerge)
        .entries.sumOf { (volume, times) -> volume.size() * times }

    private fun intersectVolumeAndMerge(
        volumes: MutableMap<Volume, Int>,
        instruction: Instruction
    ): MutableMap<Volume, Int> {
        val volume = instruction.volume

        volumes
            .mapNotNull { (otherVolume, times) ->
                (volume intersect otherVolume)?.let { it to -times }
            }
            .groupingBy { it.first }.fold(0) { acc, e -> acc + e.second }
            .forEach { (volume, times) ->
                volumes[volume] = volumes.getOrDefault(volume, 0) + times
            }

        if (instruction.add)
            volumes[volume] = volumes.getOrDefault(volume, 0) + 1

        return volumes
    }

    data class Volume(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
        private val ranges = listOf(xRange, yRange, zRange).toTypedArray()
        private fun min(index: Int) = ranges[index].first
        private fun max(index: Int) = ranges[index].last

        fun size(): Long = 1L * xRange.length * yRange.length * zRange.length

        fun inRange(other: IntRange) = ranges.all { it.first in other && it.last in other }

        infix fun intersect(other: Volume): Volume? {
            val result = IntArray(6)
            for (i in 0 until 3) {
                val min = max(min(i), other.min(i))
                val max = min(max(i), other.max(i))
                if (max >= min) { // intersects
                    result[2 * i] = min
                    result[2 * i + 1] = max
                }
                else return null
            }
            return Volume(
                result[0]..result[1],
                result[2]..result[3],
                result[4]..result[5],
            )
        }

        private val IntRange.length get() = last - first + 1
    }

    data class Instruction(val add: Boolean, val volume: Volume) {

        companion object {
            private val INSTRUCTION =
                Regex("""(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""")

            fun parse(line: String) =
                INSTRUCTION.matchEntire(line)?.destructured?.let { (what, x1, x2, y1, y2, z1, z2) ->
                    Instruction(
                        add = what == "on",
                        volume = Volume(
                            x1.toInt()..x2.toInt(),
                            y1.toInt()..y2.toInt(),
                            z1.toInt()..z2.toInt()
                        )
                    )
                } ?: error("Unable to parse: $line")
        }
    }
}