package days

@AdventOfCodePuzzle(
    name = "Seven Segment Search",
    url = "https://adventofcode.com/2021/day/8",
    date = Date(day = 8, year = 2021)
)
class Day8(input: List<String>) : Puzzle {
    private val positions = input
        .map { line ->
            line.substringBefore(" |").trim().split(' ') to
                    line.substringAfter(" |").trim().split(' ')
        }

    override fun partOne() =
        positions
            .map { line -> line.second }
            .sumOf { output -> output.count { it.length in setOf(2, 3, 4, 7) } }

    override fun partTwo() =
        positions.sumOf { (pattern, output) -> decodeOutput(pattern, output).toInt() }

    companion object {
        fun decodeOutput(pattern: List<String>, output: List<String>): String {
            val one =
                pattern.firstOrNull { it.length == 2 }?.toSet()
                    ?: throw IllegalArgumentException("Cant decode without one")
            val four =
                pattern.firstOrNull { it.length == 4 }?.toSet()
                    ?: throw IllegalArgumentException("Cant decode without four")

            return output.map { encoded ->
                when (encoded.length) {
                    2 -> 1
                    3 -> 7
                    4 -> 4
                    7 -> 8
                    5 -> { // 2,3,5
                        if (one.all { it in encoded }) 3       // 1 is ony in 3 -> 3
                        else when ((four intersect encoded.toSet()).size) {
                            2 -> 2
                            3 -> 5
                            else -> IllegalArgumentException("Hä?")
                        }
                    }
                    6 -> { // 6,9,0
                        if (four.all { it in encoded }) 9      // 4 is in 9 -> 9
                        else if (one.all { it in encoded }) 0  // 1 is in 0 -> 0
                        else if (four.intersect(encoded.toSet()).size == 3) 6 // -> 6
                        else IllegalArgumentException("Hä?")
                    }
                    else -> throw IllegalArgumentException("""Can't decode "$encoded" with length: ${encoded.length}""")
                }
            }.joinToString("")
        }
    }
}