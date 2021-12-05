package days

import kotlin.math.absoluteValue

@AdventOfCodePuzzle(
    name = "Hydrothermal Venture",
    url = "https://adventofcode.com/2021/day/5",
    date = Date(day = 5, year = 2021)
)
class Day5(input: List<String>) : Puzzle {
    private val lines = input.map { Line.from(it) }.toSet()

    override fun partOne() =
        lines.commonPoints { vertical() || horizontal() }

    override fun partTwo() =
        lines.commonPoints { vertical() || horizontal() || diagonal() }

    private fun Set<Line>.commonPoints(filter: Line.() -> Boolean) =
        this.filter { it.filter() }
            .flatMap { it.points() }
            .groupBy { it }
            .filterValues { it.size >= 2 }
            .count()

    data class Line(val from: Point, val to: Point) {
        fun vertical() = from.x == to.x
        fun horizontal() = from.y == to.y
        fun diagonal() = (from.x - to.x).absoluteValue == (from.y - to.y).absoluteValue

        fun points(): Set<Point> =
            when {
                vertical() -> rangeVertical().map { Point(from.x, it) }.toSet()
                horizontal() -> rangeHorizontal().map { Point(it, from.y) }.toSet()
                diagonal() -> rangeHorizontal().toList()
                    .zip(rangeVertical().toList())
                    .map { (x, y) -> Point(x, y) }
                    .toSet()
                else -> error("Line is not vertical, horizontal or diagonal: $this")
            }

        private fun rangeHorizontal() = if (from.x < to.x) from.x..to.x else from.x downTo to.x
        private fun rangeVertical() = if (from.y < to.y) from.y..to.y else from.y downTo to.y

        companion object {
            private val linePattern = Regex("""(\d+),(\d+)\s+->\s+(\d+),(\d+)""")

            fun from(line: String): Line {
                val find = linePattern.find(line) ?: throw IllegalArgumentException("")
                val (x1, y1, x2, y2) = find.groupValues.drop(1).map(String::toInt)
                return Line(Point(x1, y1), Point(x2, y2))
            }
        }
    }
}