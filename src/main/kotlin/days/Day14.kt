package days

@AdventOfCodePuzzle(
    name = "Extended Polymerization",
    url = "https://adventofcode.com/2021/day/14",
    date = Date(day = 14, year = 2021)
)
class Day14(val input: List<String>) : Puzzle {
    private val polymer: String = input.first()
    private val rules = input
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .associate { line -> line.split(" -> ").let { it[0] to it[1] } }

    override fun partOne() =
        process(10)
            .countByChar()
            .maxMinusMin()

    override fun partTwo() =
        process(40)
            .countByChar()
            .maxMinusMin()

    fun process(times: Int): Map<String, Long> {
        var pairCount = polymer.windowed(2)
            .groupingBy { pairs -> pairs }.eachCount()
            .mapValues { counts -> counts.value.toLong() }

        repeat(times) {
            pairCount = pairCount
                .flatMap { (a, count) ->
                    val b = rules.getValue(a)
                    val left = a[0] + b
                    val right = b + a[1]
                    listOf(left to count, right to count)
                }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }
        }
        return pairCount
    }

    fun Map<String, Long>.countByChar(): Map<Char, Long> =
        this
            .flatMap { (pair, count) -> listOf(pair[0] to count, pair[1] to count) }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }
            .mapValues { if (it.key in polymer) it.value + 1 else it.value }
            .mapValues { it.value / 2 }

    fun Map<Char, Long>.maxMinusMin() = values.sorted().let { it.last() - it.first() }

    internal fun countChars(times: Int) = process(times).countByChar().values.sum()
}