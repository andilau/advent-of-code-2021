package days

@AdventOfCodePuzzle(
    name = "Passage Pathing",
    url = "https://adventofcode.com/2021/day/12",
    date = Date(day = 12, year = 2021)
)
class Day12(val input: List<String>) : Puzzle {

    private val links = input.parseInput()

    override fun partOne() =
        links.step { path, next -> next !in path }.size

    override fun partTwo() =
        links.step { path, next ->
            next !in path ||
                    path.filter(String::isSmallCave).groupBy { it }
                        .filterValues { it.size == 2 }
                        .isEmpty()
        }
            .size

    private inline fun Map<String, List<String>>.step(
        visitBig: (List<String>, String) -> Boolean = { _, _ -> true },
        visitSmall: (List<String>, String) -> Boolean
    ): Set<List<String>> {
        var paths: MutableSet<List<String>> = this.getValue("start").map { listOf("start", it) }.toMutableSet()
        while (true) {
            val new = mutableSetOf<List<String>>()
            for (path in paths) {
                val last = path.last()
                if (last == END) {
                    new.add(path)
                }
                else {
                    this.getValue(last).forEach { next ->
                        if (next.isBigCave() && visitBig.invoke(path, next))
                            new.add(path + next)
                        if (next.isSmallCave() && visitSmall.invoke(path, next))
                            new.add(path + next)
                    }
                }
            }
            paths = new
            if (new.map { it.last() }.all { it == END }) break
        }
        return paths.toSet()
    }

    private fun List<String>.parseInput(): Map<String, List<String>> =
        flatMap { line ->
            val (from, to) = line.split('-')
            buildList {
                if (from != END && to != START) add(from to to)
                if (from != START && to != END) add(to to from)
            }
        }.groupBy(keySelector = { it.first }, valueTransform = { it.second })

    companion object {
        const val START = "start"
        const val END = "end"
    }
}

private fun String.isSmallCave() = first().isLowerCase()
private fun String.isBigCave() = first().isUpperCase()
