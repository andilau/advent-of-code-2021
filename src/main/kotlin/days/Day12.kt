package days

@AdventOfCodePuzzle(
    name = "Passage Pathing",
    url = "https://adventofcode.com/2021/day/12",
    date = Date(day = 12, year = 2021)
)
class Day12(val input: List<String>) : Puzzle {

    private val links = input.parseInput()

    override fun partOne() =
        links.generatePaths { path, next -> next.isBigCave() || next !in path }.size

    override fun partTwo() =
        links.generatePaths { path, next ->
            next.isBigCave() || next !in path ||
                    path.filter { it.isSmallCave() }.groupBy { it }
                        .filterValues { it.size == 2 }
                        .isEmpty()
        }
            .size

    private inline fun Map<String, List<String>>.generatePaths(
        visitPredicate: (List<String>, String) -> Boolean
    ): Set<List<String>> {

        var paths: Set<List<String>> = this.getValue(START).map { listOf(START, it) }.toSet()
        while (paths
                .map { path -> path.last() }
                .any { it != END }
        ) {
            paths =
                paths.filter { path -> path.last() == END }.toSet() +
                        paths
                            .filter { path -> path.last() != END }
                            .flatMap { path ->
                                val last = path.last()
                                this.getValue(last).mapNotNull { next ->
                                    if (visitPredicate.invoke(path, next))
                                        path + next
                                    else
                                        null
                                }
                            }
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

    private fun String.isSmallCave() = first().isLowerCase()
    private fun String.isBigCave() = first().isUpperCase()
}