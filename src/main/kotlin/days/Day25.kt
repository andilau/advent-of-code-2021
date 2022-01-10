package days

@AdventOfCodePuzzle(
    name = "Sea Cucumber",
    url = "https://adventofcode.com/2021/day/25",
    date = Date(day = 25, year = 2021)
)
class Day25(val input: List<String>) : Puzzle {
    private val seafloor = parseSeafloor()
    private val max = Point(input.first().lastIndex, input.lastIndex)

    override fun partOne() =
        generateSequence(seafloor) { it.next() }
            .withIndex()
            .zipWithNext()
            .first { it.first.value == it.second.value }
            .first.index + 1 // 1-based

    override fun partTwo() {}

    private fun Map<Point, SeaCucumber>.next(): Map<Point, SeaCucumber> =
        toMutableMap().move(SeaCucumber.EAST) { east() }
            .move(SeaCucumber.SOUTH) { south() }

    private fun Point.east() = copy(x = (x + 1) % (max.x + 1))
    private fun Point.south() = copy(y = (y + 1) % (max.y + 1))

    private inline fun MutableMap<Point, SeaCucumber>.move(type: SeaCucumber, transition: Point.() -> Point) =
        this.apply {
            filterValues { it == type }
                .keys
                .associateWith(transition)
                .filterValues { new -> new !in this }
                .forEach { (old, new) ->
                    this.remove(old)
                    this[new] = type
                }
        }

    internal fun stepAndList(times: Int = 1): List<String> =
        (1..times)
            .fold(seafloor) { map, _ -> map.next() }
            .asList()

    internal fun asString() = seafloor.asList().joinToString("\n")

    private fun Map<Point, SeaCucumber>.asList() = buildList {
        for (y in 0..max.y)
            add((0..max.x).mapNotNull { x ->
                this@asList[Point(x, y)]?.type ?: SeaCucumber.NONE
            }.joinToString(""))
    }

    private fun parseSeafloor() = buildMap {
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                if (char in SeaCucumber.values().map(SeaCucumber::type))
                    put(Point(x, y), SeaCucumber.from(char))
            }
        }
    }

    enum class SeaCucumber(val type: Char) {
        EAST('>'), SOUTH('v');

        companion object {
            const val NONE = '.'
            fun from(type: Char) = when (type) {
                '>' -> EAST
                'v' -> SOUTH
                else -> error("Unknown sea cucumber of type: $type")
            }
        }
    }
}