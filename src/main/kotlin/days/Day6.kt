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
        .populationByTimer()
        .reproduceByTimer()
        .drop(generation)
        .first()
        .values.sum()

    private fun List<Int>.populationByTimer() =
        this.groupBy { it }
            .mapValues { it.value.count().toLong() }
            .toMutableMap()

    private fun MutableMap<Int, Long>.reproduceByTimer() = generateSequence(this) { map ->
        // population reproduces
        val resetsAndReproduces = map.getOrDefault(0, 0)

        // update population by decreased timer
        (1..8).forEach { timer -> map[timer - 1] = map.getOrDefault(timer, 0) }

        // add population of resetted lanternfish with timer 6
        map[6] = map.getOrDefault(6, 0) + resetsAndReproduces

        // set population of new lanternfish with timer 8
        map[8] = resetsAndReproduces

        map
    }
}