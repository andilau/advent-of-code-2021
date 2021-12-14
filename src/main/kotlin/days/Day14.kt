package days

@AdventOfCodePuzzle(
    name = "Extended Polymerization",
    url = "https://adventofcode.com/2021/day/14",
    date = Date(day = 14, year = 2021)
)
class Day14(val input: List<String>) : Puzzle {
    private val polymer: String = input.first()
    private val rules = input.drop(2)
        .associate { line -> line.split(" -> ").let { it[0] to it[1] } }

    override fun partOne() =
        buildMoleculePairAndCount(10)
            .countByMolecule()
            .values
            .mostMinusLeastCommon()

    override fun partTwo() =
        buildMoleculePairAndCount(40)
            .countByMolecule()
            .values
            .mostMinusLeastCommon()

    private fun buildMoleculePairAndCount(times: Int): Map<String, Long> {
        var pairCount = polymer.windowed(2)
            .groupingBy { pairs -> pairs }.eachCount()
            .mapValues { counts -> counts.value.toLong() }

        repeat(times) {
            pairCount = pairCount
                .flatMap { (pair, count) ->
                    val molecule = rules.getValue(pair)
                    val left = pair[0] + molecule
                    val right = molecule + pair[1]
                    listOf(left to count, right to count)
                }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }
        }
        return pairCount
    }

    private fun Map<String, Long>.countByMolecule(): Map<Char, Long> =
        this
            .flatMap { (pair, count) -> listOf(pair[0] to count, pair[1] to count) }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }
            .mapValues { if (it.key in polymer) it.value + 1 else it.value }
            .mapValues { it.value / 2 }

    private fun Collection<Long>.mostMinusLeastCommon() = sorted().let { it.last() - it.first() }

    internal fun countMolecules(times: Int) = countByMolecule(times).values.sum()

    internal fun countByMolecule(times: Int) =
        buildMoleculePairAndCount(times)
            .countByMolecule()
}