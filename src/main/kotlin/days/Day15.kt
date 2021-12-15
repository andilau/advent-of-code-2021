package days

import java.util.*

@AdventOfCodePuzzle(
    name = "Chiton",
    url = "https://adventofcode.com/2021/day/15",
    date = Date(day = 15, year = 2021)
)
class Day15(val input: List<String>) : Puzzle {

    override fun partOne() =
        SimpleCave.parse(input).totalRisk()

    override fun partTwo() =
        ExtendedCave(SimpleCave.parse(input)).totalRisk()

    private fun Grid.totalRisk(
        from: Point = Point(0, 0),
        to: Point = Point(width - 1, height - 1)
    ): Int {
        val visited = mutableSetOf<Point>()
        val queue = PriorityQueue<RiskAtPoint>()

        queue.add(RiskAtPoint(from, 0))

        while (true) {
            val riskyPoint = queue.remove()
            if (riskyPoint.point in visited) continue

            visited.add(riskyPoint.point)

            if (riskyPoint.point == to) return riskyPoint.risk

            this.neighborsAt(riskyPoint.point)
                .forEach { n -> queue.add(RiskAtPoint(n, riskyPoint.risk + this[n])) }
        }
    }

    interface Grid {
        val width: Int
        val height: Int
        operator fun get(at: Point): Int
        fun neighborsAt(at: Point): List<Point>
    }

    data class SimpleCave(val cells: List<List<Int>>) : Grid {
        override val width get() = cells.first().size
        override val height get() = cells.size
        override fun get(at: Point): Int = cells[at.y][at.x]
        override fun neighborsAt(at: Point) = buildList {
            if (at.x > 0) add(Point(at.x - 1, at.y))
            if (at.y > 0) add(Point(at.x, at.y - 1))
            if (at.x < width - 1) add(Point(at.x + 1, at.y))
            if (at.y < height - 1) add(Point(at.x, at.y + 1))
        }

        companion object {
            fun parse(input: List<String>): Grid {
                val cells = input.map { line -> line.map { it.digitToInt() } }
                return SimpleCave(cells)
            }
        }
    }

    data class ExtendedCave(val parent: Grid) : Grid {
        override val width get() = parent.width * 5
        override val height get() = parent.height * 5
        override fun get(at: Point): Int {
            val parentAt = Point(at.x % parent.width, at.y % parent.height)
            val risk = parent[parentAt] + (at.x / parent.width + at.y / parent.height)
            return (risk - 1) % 9 + 1
        }

        override fun neighborsAt(at: Point) = buildList {
            if (at.x > 0) add(Point(at.x - 1, at.y))
            if (at.y > 0) add(Point(at.x, at.y - 1))
            if (at.x < width - 1) add(Point(at.x + 1, at.y))
            if (at.y < height - 1) add(Point(at.x, at.y + 1))
        }
    }

    data class RiskAtPoint(val point: Point, val risk: Int) : Comparable<RiskAtPoint> {
        override fun compareTo(other: RiskAtPoint) = risk.compareTo(other.risk)
    }
}