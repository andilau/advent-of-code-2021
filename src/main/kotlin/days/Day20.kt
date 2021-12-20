package days

@AdventOfCodePuzzle(
    name = "Trench Map",
    url = "https://adventofcode.com/2021/day/20",
    date = Date(day = 20, year = 2021)
)
class Day20(val input: List<String>) : Puzzle {
    private val enhancement = input.first().toCharArray().map { if (it == '#') 1 else 0 }
    private val image = Image.parse(input.drop(2))

    override fun partOne() =
        (0 until 2).fold(image) { i, _ -> i.enhance(enhancement) }.litPixels()

    override fun partTwo() =
        (0 until 50).fold(image) { i, _ -> i.enhance(enhancement) }.litPixels()

    data class Image(val data: List<List<Int>>, val default: Int) {
        private val height = data.size
        private val width = data.first().size

        companion object {
            fun parse(lines: List<String>): Image {
                val data = lines.map { l -> l.map { if (it == '#') 1 else 0 } }
                return Image(data, 0)
            }
        }

        fun enhance(mapping: List<Int>) =
            Image(
                data = (-1..height).map { y ->
                    (-1..width).map { x ->
                        Point(x, y)
                            .neighborsAndSelf()
                            .map { at(it) }
                            .joinToString("")
                            .toInt(2)
                            .let { mapping[it] }
                    }
                }, default =
                if (mapping[0] == 1) (1 - default) else default
            )

        fun litPixels() = data.sumOf { it.sum() }

        private fun at(point: Point): Int {
            if (point.x in 0 until width && point.y >= 0 && point.y < height) {
                return data[point.y][point.x]
            }
            return default
        }

        override fun toString(): String =
            data.joinToString("\n") { row ->
                row.map { if (it == 1) '#' else '.' }.joinToString("")
            }
    }
}