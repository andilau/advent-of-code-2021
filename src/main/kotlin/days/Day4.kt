package days

@AdventOfCodePuzzle(
    name = "Giant Squid",
    url = "https://adventofcode.com/2021/day/4",
    date = Date(day = 4, year = 2021)
)
class Day4(input: List<String>) : Puzzle {
    private val draws = readDraws(input)
    private val boards = readBoards(input)

    override fun partOne() =
        draws.firstNotNullOf { draw ->
            boards
                .firstOrNull { board -> board.markForBingo(draw) }
                ?.let { draw * it.unmarked().sum() }
        }

    override fun partTwo() =
        boards
            .associateWith { board -> draws.first { board.markForBingo(it) } }
            .maxByOrNull { draws.indexOf(it.value) }
            ?.let { it.key.unmarked().sum() * it.value }
            ?: error("")

    private fun readDraws(input: List<String>) = input.first().split(',').map(String::toInt)

    private fun readBoards(input: List<String>) = input
        .asSequence()
        .drop(1)
        .filter { it.isNotEmpty() }
        .chunked(5) { it.joinToString(" ") }
        .map { BingoBoard.from(it) }
        .toSet()

    data class BingoBoard(val numbers: List<Int>) {
        private val marked = mutableSetOf<Int>()

        init {
            require(numbers.size == 25)
        }

        fun markForBingo(number: Int): Boolean {
            marked += number
            return isBingo(number)
        }

        fun unmarked() = (numbers subtract marked)

        private fun isBingo(number: Int) =
            winnerRanges
                .filter { range -> range.contains(numbers.indexOf(number)) }
                .map { range -> range.map { numbers[it] }.toSet() }
                .any { marked.containsAll(it) }

        companion object {
            fun from(input: String) =
                BingoBoard(
                    input
                        .trim()
                        .split(whitespaces)
                        .map(String::toInt)
                )

            private val whitespaces = Regex("\\s+")

            private val winnerRanges = listOf(
                0..4, 5..9, 10..14, 15..19, 20..24,
                0..24 step 5, 1..24 step 5, 2..24 step 5, 3..24 step 5, 4..24 step 5,
            )
        }
    }
}