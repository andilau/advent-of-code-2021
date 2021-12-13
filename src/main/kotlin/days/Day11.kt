package days

@AdventOfCodePuzzle(
    name = "Dumbo Octopus",
    url = "https://adventofcode.com/2021/day/11",
    date = Date(day = 11, year = 2021)
)
class Day11(val input: List<String>) : Puzzle {
    private var dumbos = parseInput(input)

    override fun partOne() =
        flashCount(100)

    override fun partTwo(): Int {
        val dumbos = parseInput(input)
        var cycle = 0
        while (true) {
            cycle++
            cycle(dumbos)
            if (dumbos.all { row -> row.all { it == 0 } }) {
                break
            }
        }
        return cycle
    }

    internal fun flashCount(generation: Int = 1): Int {
        var flashCount = 0
        repeat(generation) {
            cycle(dumbos)
            flashCount += dumbos.sumOf { row -> row.count { it == 0 } }
        }
        return flashCount
    }

    internal fun step(times: Int = 1): List<String> {
        repeat(times) { cycle(dumbos) }
        return dumbos.map { it.joinToString("") }
    }

    private fun cycle(dumbos: List<MutableList<Int>>) {
        val flashed = mutableListOf<Point>()

        dumbos.mapIndexed { y, row ->
            row.indices.forEach { x ->
                dumbos.set(y, x, flashed)
            }
        }

        var count = 0
        while (count < flashed.size) {
            val point = flashed[count++]
            point.neighbors().forEach { (x, y) ->
                if (y in dumbos.indices && x in dumbos[y].indices) {
                    dumbos.set(y, x, flashed)
                }
            }
        }

        for (point in flashed) {
            dumbos[point.y][point.x] = 0
        }
    }

    private fun List<MutableList<Int>>.set(y: Int, x: Int, flashed: MutableList<Point>) {
        this[y][x]++
        if (this[y][x] == 10)
            flashed.add(Point(x, y))
    }

    private fun parseInput(input: List<String>) =
        input.map { line -> line.map { it.digitToInt() }.toMutableList() }
}