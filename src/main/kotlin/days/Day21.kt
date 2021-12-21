package days

@AdventOfCodePuzzle(
    name = "Dirac Dice",
    url = "https://adventofcode.com/2021/day/21",
    date = Date(day = 21, year = 2021)
)
class Day21(val input: List<String>) : Puzzle {
    private val start1 = input[0].substringAfter("Player 1 starting position: ").toInt()
    private val start2 = input[1].substringAfter("Player 2 starting position: ").toInt()

    override fun partOne() =
        Universe(start1, 0, start2, 0).solveWithDeterministicDice()

    override fun partTwo() =
        Universe(start1, 0, start2, 0).solveWithDiracDice().let { maxOf(it.first, it.second) }

    data class Universe(val field1: Int, val score1: Int, val field2: Int, val score2: Int) {
        fun solveWithDiracDice(): Pair<Long, Long> {
            memo[this]?.let { return it }
            if (score1 >= 21) return 1L to 0L
            if (score2 >= 21) return 0L to 1L

            var answer = 0L to 0L
            for ((dice, count) in diceCount) {
                val newField1 = (field1 + dice - 1) % 10 + 1
                val newScore1 = score1 + newField1
                val (win2, win1) = Universe(field2, score2, newField1, newScore1).solveWithDiracDice()
                answer = answer.first + win1 * count to answer.second + win2 * count
            }
            memo[this] = answer
            return answer
        }

        fun solveWithDeterministicDice(): Int {
            var players = Player(field1) to Player(field2)
            val dice = DeterministicDice(100)

            val min = dice.takeSumOfThree().firstNotNullOf { moves ->
                players.first.moves(moves)
                if (players.first.wins())
                    minOf(players.first.points, players.second.points)
                else {
                    players = players.second to players.first
                    null
                }
            }
            return dice.rolls * min
        }

        companion object {
            val memo = mutableMapOf<Universe, Pair<Long, Long>>()

            val diceCount = buildList {
                for (i in 1..3)
                    for (j in 1..3)
                        for (k in 1..3)
                            add(i + j + k)
            }.groupingBy { it }.eachCount()
        }

        class Player(var field: Int, var points: Int = 0) {
            fun wins() = points >= 1000

            fun moves(amount: Int) {
                field += amount
                field = (field - 1) % 10 + 1
                points += field
            }
        }

        class DeterministicDice(private val sides: Int) {
            private var r = 0
            val rolls get() = r

            fun takeSumOfThree() =
                generateSequence(1) { x -> if (x == sides) 1 else x + 1 }
                    .chunked(3)
                    .onEach { r += 3 }
                    .map { it.sum() }
        }
    }
}