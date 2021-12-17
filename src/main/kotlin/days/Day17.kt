package days

import kotlin.math.sign

@AdventOfCodePuzzle(
    name = "Trick Shot",
    url = "https://adventofcode.com/2021/day/17",
    date = Date(day = 17, year = 2021)
)
class Day17(input: String) : Puzzle {

    // target area: x=32..65, y=-225..-177
    private val targetArea = input
        .substringAfter("target area: ")
        .split(", ")
        .map { it.substringAfter('=').split("..").map(String::toInt) }
        .map { if (it.first() < it.last()) it.first()..it.last() else it.last()..it.first() }
        .let { it[0] to it[1] }

    private val xRange = targetArea.first
    private val yRange = targetArea.second

    private val altitudes: Sequence<Int> by lazy {
        generateVelocities()
            .mapNotNull { velocity -> maxAltitudeIfHittingTargetFor(velocity) }
    }

    override fun partOne(): Int = altitudes.maxOrNull() ?: error("No altitudes found")

    override fun partTwo(): Int = altitudes.count()

    private fun generateVelocities() = sequence {
        for (x in 1..xRange.last) for (y in yRange.first..500) yield(Point(x, y))
    }

    private fun maxAltitudeIfHittingTargetFor(velocity: Point): Int? {
        val points = velocityByTime(velocity)
            .trajectory()
            .takeWhile { it.x <= xRange.last && it.y >= yRange.first }
        return points.maxOf { it.y }
            .takeIf { points.any { it.x in xRange && it.y in yRange } }
    }

    private fun velocityByTime(velocity: Point) =
        generateSequence(velocity) { (x, y) -> Point(x - x.sign, y - 1) }

    private fun Sequence<Point>.trajectory(): Sequence<Point> =
        runningFold(Point.ORIGIN) { pos, v -> pos + v }
}