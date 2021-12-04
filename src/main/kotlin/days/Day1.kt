package days

@AdventOfCodePuzzle(
    name = "Sonar Sweep",
    url = "https://adventofcode.com/2021/day/1",
    date = Date(day = 1, year = 2021)
)
class Day1(private val measurements: List<Int>) : Puzzle {

    override fun partOne(): Int =
        measurements.increasesTimes { it }

    override fun partTwo() =
        measurements.increasesTimes { it -> it.windowed(3).map { it.sum() } }

    private fun <T> List<T>.increasesTimes(transform: (List<T>) -> List<T>)
            where T : Comparable<T>, T : Number =
        transform.invoke(this).zipWithNext().count { it.first < it.second }
}