package days

@AdventOfCodePuzzle(
    name = "Lanternfish",
    url = "https://adventofcode.com/2021/day/6",
    date = Date(day = 6, year = 2021)
)
class Day6(input: String) : Puzzle {
    private val initial = input.split(',').map(String::toInt).toList()

    override fun partOne() = populationMapAt(80)

    override fun partTwo() = populationMapAt(256)

    private fun populationAt(generation: Int) = initial.reproduce().drop(generation).first().count()

    private fun populationMapAt(generation: Int) = initial
        .groupBy { it }
        .mapValues { it.value.count().toLong() }.toMutableMap()
        .reproduce()
        .drop(generation)
        .first()
        .values.sum()

    private fun List<Int>.reproduce() = generateSequence(this) { swarm ->
        val cnt = swarm.filter { it == 0 }.map { 8 }.toList()
        swarm.map { if (it == 0) 6 else it - 1 } + cnt
    }

    private fun MutableMap<Int, Long>.reproduce() = generateSequence(this) { swarm ->
        val cnt = swarm.getOrDefault(0, 0) // add to [8]
        for (i in 1..8) {
            swarm[i - 1] = swarm.getOrDefault(i, 0)
        }
        swarm[6] = swarm.getOrDefault(6, 0) + cnt
        swarm[8] =  cnt
        swarm
    }


}