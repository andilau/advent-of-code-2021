package days

@AdventOfCodePuzzle(
    name = "Smoke Basin",
    url = "https://adventofcode.com/2021/day/9",
    date = Date(day = 9, year = 2021)
)
class Day9(private val seafloor: List<String>) : Puzzle {

    override fun partOne() =
        seafloor.flatMapIndexed() { y, line ->
            line.mapIndexedNotNull() { x, spot ->
                if (seafloor.getNeighbours(x, y).all { it > spot }) Pair(x, y) else null
            }
        }.sumOf { seafloor.levelAt(it) }


    override fun partTwo() = 0

    private fun List<String>.getNeighbours(x: Int, y: Int) =
        setOfNotNull(
            this.getOrNull(y - 1)?.getOrNull(x),
            this.getOrNull(y)?.getOrNull(x - 1),
            this.getOrNull(y)?.getOrNull(x + 1),
            this.getOrNull(y + 1)?.getOrNull(x)
        )

    private fun List<String>.levelAt(at: Pair<Int, Int>) =
        get(at.second)[at.first].toString().toInt() + 1
}