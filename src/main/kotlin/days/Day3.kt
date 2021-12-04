package days

@AdventOfCodePuzzle(
    name = "Binary Diagnostic",
    url = "https://adventofcode.com/2021/day/3",
    date = Date(day = 3, year = 2021)
)
class Day3(private val report: List<String>) : Puzzle {

    override fun partOne(): Int {
        val gamma = report.calculateRate { mostCommon() }.toNumber()
        val epsilon = report.calculateRate { leastCommon() }.toNumber()

        return gamma * epsilon
    }

    override fun partTwo(): Int {
        val oxygenGeneratorRating = report.findRating { mostCommonOrEqual() }.toNumber()
        val co2ScrubberRating = report.findRating { leastCommonOrEqual() }.toNumber()

        return oxygenGeneratorRating * co2ScrubberRating
    }

    private inline fun Collection<String>.calculateRate(findCriteria: List<Char>.() -> Char) =
        first().indices
            .map { at -> this.map { line -> line[at] }.findCriteria() }
            .joinToString("")

    private inline fun Collection<String>.findRating(criteria: List<Char>.() -> Char): String {
        val candidates = this.toMutableList()
        for (index in first().indices) {
            val wanted: Char = candidates.map { it[index] }.criteria()
            candidates.retainAll { it[index] == wanted }
            if (candidates.size == 1) return candidates.single()
        }
        error("Could not find rating.")
    }

    private fun Iterable<Char>.mostCommon() =
        if (count { it == '1' } > count { it == '0' }) '1' else '0'

    private fun Iterable<Char>.mostCommonOrEqual() =
        if (count { it == '1' } >= count { it == '0' }) '1' else '0'

    private fun Iterable<Char>.leastCommon() =
        if (count { it == '1' } > count { it == '0' }) '0' else '1'

    private fun Iterable<Char>.leastCommonOrEqual() =
        if (count { it == '1' } >= count { it == '0' }) '0' else '1'

    private fun String.toNumber() = toInt(2)
}