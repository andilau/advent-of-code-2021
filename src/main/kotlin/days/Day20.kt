package days

import javax.swing.tree.VariableHeightLayoutCache

@AdventOfCodePuzzle(
    name = "Beacon Scanner",
    url = "https://adventofcode.com/2021/day/20",
    date = Date(day = 20, year = 2021)
)
class Day20(val input: List<String>) : Puzzle {
    private val algorithm = input[0].map { if (it == '#') 0 else 1 }.toIntArray()
    private val inv = algorithm[0] == 1
    var data = input.drop(2).filter{it.isNotEmpty()}.map { it.map { if (it == '#') 1 else 0 }.toIntArray() }.toTypedArray()

    class Image(val data: Array<IntArray>) {
        val height = data.size
        val width = data[0].size

        init {
            println("init with: $height $width")
        }

        fun enhance(algorithm: IntArray): Image {
            val b = Array(height + 2) { IntArray(width + 2) }
            val bg = 0
            for (i in 0 until height + 2) for (j in 0 until width + 2) {
                var s = 0
                for (i1 in -1..1) for (j1 in -1..1) {
                    s = (s shl 1) + when (data.getOrNull(i + i1 - 1)?.getOrNull(j + j1 - 1)) {
                        null -> 0
                        1 -> 1
                        0 -> 0
                        else -> error("")
                    }
                }
                b[i][j] = algorithm[s] xor 1
            }
            return Image(b)
        }

        fun pixels() = data.sumOf { it.sum() }

        fun print() {
            println(data.joinToString("\n") {
                it.map {
                    when (it) {
                        0 -> '.';1 -> '#';else -> error("")
                    }
                }.joinToString("")
            })
        }
    }

    override fun partOne(): Int {
        return Image(data)
            .also { it.print(); println("{${it.height}} {${it.width}}") }
            .enhance(algorithm)
            .also { it.print(); println("{${it.height}} {${it.width}}") }
            .enhance(algorithm)
            .also { it.print(); println("{${it.height}} {${it.width}}") }
            .pixels()
    }

    override fun partTwo(): Any {
        return 0
    }
}