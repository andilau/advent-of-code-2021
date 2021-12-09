package days

typealias Basin = MutableList<List<IntRange>>
typealias Basins = MutableSet<Basin>

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
        seafloor.indices.map { y -> ranges(y) }
            //.also { println(it.joinToString("\n")) }
            .fold(mutableSetOf(), build())
            .also { it.forEach { println("Basin:" + it.joinToString(" ")) } }
            .map { it.sumOf { it.sumOf { it.last - it.first + 1 } } }
            .sortedDescending()
            .take(3)
            .let { it[0] * it[1] * it[2] }

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

    private fun build() = { basins: Basins, lineRanges: List<IntRange> ->
        println("Day9.build")
        val rangesToAdd = lineRanges.toMutableSet()
        for (basin in basins) {
            if (basin.last().isEmpty()) continue
            print("basin: $basin ")
            val add = basin.inRangeOf(lineRanges)
            basin.add(add)
            rangesToAdd.removeAll(add)
            println("--> $basin")
        }
        for (addRange in rangesToAdd) {
            val newBasin = mutableListOf(listOf(addRange))
            basins.add(newBasin)
            println("new basin: $newBasin")
        }
        basins
    }

    private fun Basin.inRangeOf(ranges: List<IntRange>): List<IntRange> {
        val basinRanges = last()
        return ranges.filter { range ->
            basinRanges.any { basinRange ->
                range.any { it in basinRange }
            }
        }
    }

    fun IntRange.inRange(others: List<IntRange>): IntRange? {
        this.forEach { spot ->
            others.forEach { lineOfBasin ->
                if (spot in lineOfBasin) return lineOfBasin
            }
        }
        return null
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