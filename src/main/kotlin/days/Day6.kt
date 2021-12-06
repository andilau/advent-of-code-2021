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
        val resetAndCreate = map.getOrDefault(0, 0)

        // timer decreases
        for (timer in 1..8) {
            map[timer - 1] = map.getOrDefault(timer, 0)
        }
        map[6] = map.getOrDefault(6, 0) + resetAndCreate // add population of resetted lanternfish
        map[8] = resetAndCreate  // create population of new lanternfish
        map
    }
}