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
        .associate { it.split(" -> ").let { it[0] to it[1] } }

    override fun partOne() =
        find(10)

    override fun partTwo()=find(40)


     fun find(times:Int): Long {
        println("polymer = ${polymer}")
        var mapValues = polymer.windowed(2)
            .groupingBy { pairs -> pairs }.eachCount()
            .mapValues { counts -> counts.value.toLong() }

        repeat(times) {
            mapValues = mapValues
                .flatMap { (a, count) ->
                    val b = rules.getValue(a)
                    val left = a[0] + b
                    val right = b + a[1]
                    listOf(left to count, right to count)
                }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }
            // println("mapValues = $mapValues")
        }
        val counts = mapValues
            .flatMap { (pair, count) -> listOf(pair[0] to count, pair[1] to count) }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }
            .mapValues { if (it.key in polymer) it.value + 1 else it.value }
            .mapValues { it.value / 2 }
            .toMutableMap()

        //println("counts = ${counts}")

        return counts.values.sorted().let { it.last() - it.first() }
        /*After step 10, B occurs 1749 times, C occurs 298 times, H occurs 161 times, and N occurs 865 times
        * */
    }

    fun process(times: Int): String {
        var result = polymer
        repeat(times) {
            result = result
                .windowed(2, 1)
                .map { it.first() + rules.getValue(it) }
                .joinToString("") + polymer.last()
        }
        return result
    }
}