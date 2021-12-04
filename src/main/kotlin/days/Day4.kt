package days

@AdventOfCodePuzzle(
    name = "Giant Squid",
    url = "https://adventofcode.com/2021/day/4",
    date = Date(day = 4, year = 2021)
)
class Day4(input: List<String>) : Puzzle {
    private val drawnNumbers = input.first().split(',').map(String::toInt)

    private val boards = input
        .drop(1)
        .chunked(6)
        .map { it.joinToString(" ") }
        .filter { it.isNotEmpty() }
        .map { BingoBoard.from(it) }
        .toList()

    override fun partOne(): Int {
        drawnNumbers.forEach { number ->
            boards.forEach { board ->
                if (board.mark(number)) {
                    return number * board.notMarkedSum()
                }
            }
        }
        return 0
    }

    override fun partTwo(): Int {
        val notWon = boards.toMutableSet()

        drawnNumbers.forEach { number ->
            val looser = notWon.first()
            boards.forEach { board ->
                if (board.mark(number)) {
                    notWon.remove(board)
                }
            }
            if (notWon.size == 0) {
                return number * looser.notMarkedSum()
            }
        }
        return 0
    }

    data class BingoBoard(val array: List<Int>) {
        private val marked = mutableSetOf<Int>()

        init {
            require(array.size == 25)
        }

        fun mark(number: Int): Boolean {
            marked += number
            val index = array.indexOf(number)
            return ranges
                .filter { it.contains(index) }
                .map { it.map { array[it] }.toSet() }
                .any { marked.containsAll(it) }
        }

        private val ranges = listOf(
            0..4, 5..9, 10..14, 15..19, 20..24,
            0 until 25 step 5,
            1 until 25 step 5,
            2 until 25 step 5,
            3 until 25 step 5,
            4 until 25 step 5,
        )

        fun notMarkedSum() =
            (array subtract marked).sum()

        companion object {
            fun from(input: String): BingoBoard {
                return BingoBoard(input
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .map(String::toInt)
                    .toList())
            }

        }
    }
}