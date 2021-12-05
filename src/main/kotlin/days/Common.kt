package days

import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {

    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)

    fun normalize() = Point(x / x.absoluteValue, y / y.absoluteValue)
}