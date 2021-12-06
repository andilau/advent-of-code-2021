package days

@AdventOfCodePuzzle(
    name = "Lanternfish",
    url = "https://adventofcode.com/2021/day/6",
    date = Date(day = 6, year = 2021)
)
class Day6(input: String) : Puzzle {
    private val population = input.split(',').map(String::toInt).toList()

    override fun partOne() = populationAt(80)

    override fun partTwo() = populationAt(256)

    fun populationAt(generation: Int) = population
        .populationMap()
        .reproduceMap()
        .drop(generation)
        .first()
        .values.sum()

    private fun List<Int>.populationMap() =
        this.groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()

    private fun Map<Int, Long>.reproduceMap() =
        generateSequence(this) { map ->
            val mapWithDefault = map.withDefault { 0 }
            mapOf(
                0 to mapWithDefault.getValue(1),
                1 to mapWithDefault.getValue(2),
                2 to mapWithDefault.getValue(3),
                3 to mapWithDefault.getValue(4),
                4 to mapWithDefault.getValue(5),
                5 to mapWithDefault.getValue(6),
                6 to mapWithDefault.getValue(7) +
                        mapWithDefault.getValue(0),
                7 to mapWithDefault.getValue(8),
                8 to mapWithDefault.getValue(0),
            )
        }
}