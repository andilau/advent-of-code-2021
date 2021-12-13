package days

import kotlin.math.absoluteValue

@AdventOfCodePuzzle(
    name = "Transparent Origami",
    url = "https://adventofcode.com/2021/day/13",
    date = Date(day = 13, year = 2021)
)
class Day13(val input: List<String>) : Puzzle {

    private val points = input.parsePoints()
    private val instructions = input.parseInstructions()

    override fun partOne() =
        instructions
            .take(1)
            .fold(points) { points, instruction -> points.foldAxis(instruction) }
            .count()

    override fun partTwo() =
        instructions
            .fold(points) { points, instruction -> points.foldAxis(instruction) }
            .print()

    private fun Set<Point>.foldAxis(instruction: String): Set<Point> {
        val axis = instruction.first()
        val d = instruction.substringAfter('=').toInt()
        println("Before.count() = ${this.count()}")
        return when (axis) {
            'x' -> this.map { it.copy(x = d - (it.x - d).absoluteValue) }.toSet()
            'y' -> this.map { it.copy(y = d - (it.y - d).absoluteValue) }.toSet()
            else -> throw IllegalArgumentException(instruction)
        }
    }

    private fun List<String>.parsePoints(): Set<Point> =
        this.takeWhile { it.isNotEmpty() }
            .map { from(it) }
            .toSet()

    private fun List<String>.parseInstructions(): List<String> =
        this.dropWhile { it.isNotEmpty() }
            .drop(1)
            .map { it.substringAfter("fold along ") }
            .also { println("it = ${it}") }
            .toList()
}

private fun Set<Point>.print() {
    this.groupBy { it.y }
        .toSortedMap()
        .forEach { (y, u) ->
            val maxByOrNull = u.maxByOrNull { it.x }?.x ?: 0
            (0..maxByOrNull).forEach() {
                if (Point(it, y) in u)
                    print("#")
                else print(" ")
            }
            println()
        }

}

fun from(line: String): Point {
    return Point(
        line.substringBefore(',').toInt(),
        line.substringAfter(',').toInt()
    )
}
