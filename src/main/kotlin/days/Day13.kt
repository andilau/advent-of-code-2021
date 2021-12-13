package days

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

    private fun Set<Point>.foldAxis(instruction: FoldInstruction): Set<Point> =
        when (instruction.axis) {
            'x' -> this.map {
                if (it.x < instruction.foldAt) it
                else it.copy(x = 2 * instruction.foldAt - it.x)
            }.toSet()
            'y' -> this.map {
                if (it.y < instruction.foldAt) it
                else it.copy(y = 2 * instruction.foldAt - it.y)
            }.toSet()
            else -> throw IllegalArgumentException("Unable to fold axis: ${instruction.axis}")
        }

    private fun Set<Point>.print() =
        this.groupBy { it.y }
            .toSortedMap()
            .forEach { (y, points) ->
                val xMax = points.maxByOrNull { it.x }?.x ?: 0
                (0..xMax).forEach {
                    print(if (Point(it, y) in points) "#" else " ")
                }
                println()
            }

    private fun List<String>.parsePoints(): Set<Point> =
        this.takeWhile { it.isNotEmpty() }
            .map { from(it) }
            .toSet()

    private fun List<String>.parseInstructions(): List<FoldInstruction> =
        this.dropWhile { it.isNotEmpty() }
            .filter { it.isNotEmpty() }
            .map { FoldInstruction.from(it) }

    data class FoldInstruction(val axis: Char, val foldAt: Int) {

        companion object {
            fun from(line: String) =
                FoldInstruction(
                    line.substringAfter("fold along ").first(),
                    line.substringAfter('=').toInt()
                )
        }
    }

    private fun from(line: String): Point {
        return Point(
            line.substringBefore(',').toInt(),
            line.substringAfter(',').toInt()
        )
    }
}