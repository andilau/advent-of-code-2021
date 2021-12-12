package days

@AdventOfCodePuzzle(
    name = "Passage Pathing",
    url = "https://adventofcode.com/2021/day/12",
    date = Date(day = 12, year = 2021)
)
class Day12(val input: List<String>) : Puzzle {

    private val nodes = input.flatMap { line ->
        val (from, to) = line.split('-')
        buildList {
            if (from != END && to != START) add(from to to)
            if (from != START && to != END) add(to to from)
        }
    }.groupBy(keySelector = { it.first }, valueTransform = { it.second })

    override fun partOne(): Int {
        var paths: MutableSet<List<String>> = nodes.getValue("start").map { listOf("start", it) }.toMutableSet()
        while (true) {
            val new = mutableSetOf<List<String>>()

            for (path in paths) {
                val last = path.last()
                if (last == END) {
                    new.add(path)
                    continue
                }
                val all = nodes.getValue(last)
                for (next in all) {
                    if (!next.isSmallCave())
                        new.add(path + next)
                    else if (next !in path)
                        new.add(path + next)
                }
            }

            paths = new
            if (new.map { it.last() }.all { it == END }) break
        }
        return paths.size
    }

    override fun partTwo(): Int {
        var paths: MutableSet<List<String>> = nodes.getValue("start").map { listOf("start", it) }.toMutableSet()

        while (true) {
            val new = mutableSetOf<List<String>>()

            for (path in paths) {
                val last = path.last()
                if (last == END) {
                    new.add(path); continue
                }
                val all = nodes.getValue(last)
                for (next in all) {
                    if (!next.isSmallCave()) {
                        new.add(path + next)
                    }
                    else
                        if (next !in path || path.filter(String::isSmallCave).groupBy { it }
                                .filterValues { it.size == 2 }.isEmpty())
                            new.add(path + next)
                }
            }
            paths = new
            if (new.map { it.last() }.all { it == END }) break
        }
        return paths.size
    }


    companion object {
        const val START = "start"
        const val END = "end"
    }
}

private fun String.isSmallCave() = first().isLowerCase()
private fun String.isBigCave() = first().isUpperCase()
