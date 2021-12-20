package days

import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {

    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)

    fun normalize() = Point(x / x.absoluteValue, y / y.absoluteValue)

    fun neighbors(): List<Point> =
        listOf(
            Point(x - 1, y - 1),
            Point(x + 0, y - 1),
            Point(x + 1, y - 1),
            Point(x + 1, y),
            Point(x - 1, y),
            Point(x - 1, y + 1),
            Point(x + 0, y + 1),
            Point(x + 1, y + 1),
        )

    fun neighborsAndSelf() =
        (-1..1).flatMap { dy ->
            (-1..1).map { dx ->
                Point(x + dx, y + dy)
            }
        }

    companion object {
        val ORIGIN = Point(0, 0)
    }
}