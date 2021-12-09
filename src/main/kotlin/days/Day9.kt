package days

typealias Basin = MutableList<List<IntRange>>
typealias Basins = Set<Basin>

@AdventOfCodePuzzle(
    name = "Smoke Basin",
    url = "https://adventofcode.com/2021/day/9",
    date = Date(day = 9, year = 2021)
)
class Day9(private val seafloor: List<String>) : Puzzle {

    override fun partOne() =
        seafloor.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, spot ->
                if (seafloor.getNeighbours(x, y).all { it > spot }) Pair(x, y) else null
            }
        }.sumOf { seafloor.levelAt(it) }

    override fun partTwo() =
        seafloor.indices.asSequence().map { y -> ranges(y) }
            .fold(emptySet(), buildBasins())
            .map { it.size() }
            .sortedDescending()
            .take(3)
            .reduce { a, b -> a * b }

    private fun Basin.size() =
        sumOf { ranges -> ranges.sumOf { range -> range.last - range.first + 1 } }

    private fun ranges(y: Int): List<IntRange> {
        val line = seafloor[y]

        val nineAt = line.mapIndexedNotNull { index, c -> if (c == '9') index else null }
        val limits = buildList {
            add(-1)
            addAll(nineAt)
            add(line.lastIndex + 1)
        }
        return limits
            .zipWithNext()
            .mapNotNull { (start, end) -> if (end - start > 1) start + 1 until end else null }
    }

    private fun buildBasins() = { basins: Basins, lineRanges: List<IntRange> ->
        val newBasins = mutableSetOf<Basin>()

        val rangesToAdd = lineRanges.toMutableSet()
        for (basin in basins) {
            if (basin.last().isEmpty()) {
                newBasins.add(basin)
            }
            else {
                val add = basin.inRangeOf(lineRanges)
                basin.add(add)
                newBasins.add(basin)
                rangesToAdd.removeAll(add)
            }
        }

        val basinsToMerge: List<MutableList<List<IntRange>>>? = basins
            .filter { it.last().isNotEmpty() }
            .groupBy { it.last() }
            .filterValues { it.size >= 2 }
            .values
            .firstOrNull()

        if (basinsToMerge != null && basinsToMerge.isNotEmpty()) {

            val basinMerged = basinsToMerge
                .map { it.reversed() }
                .flatMap { it.withIndex() }
                .groupBy { it.index }
                .mapValues { it.value.flatMap { it.value }.distinct() }
                .values
                .reversed()
                .toMutableList()

            newBasins.removeAll(basinsToMerge)
            newBasins.add(basinMerged)
        }
        for (addRange in rangesToAdd) {
            val newBasin = mutableListOf(listOf(addRange))
            newBasins.add(newBasin)
        }
        newBasins
    }

    private fun Basin.inRangeOf(ranges: List<IntRange>): List<IntRange> =
        ranges.filter { range ->
            last().any { basinRange ->
                range.any { it in basinRange }
            }
        }

    private fun List<String>.getNeighbours(x: Int, y: Int) =
        setOfNotNull(
            this.getOrNull(y - 1)?.getOrNull(x),
            this.getOrNull(y)?.getOrNull(x - 1),
            this.getOrNull(y)?.getOrNull(x + 1),
            this.getOrNull(y + 1)?.getOrNull(x)
        )

    private fun List<String>.levelAt(at: Pair<Int, Int>) =
        get(at.second)[at.first].toString().toInt() + 1
}