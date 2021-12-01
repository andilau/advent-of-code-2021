package days

@AdventOfCodePuzzle(
    name = "Sonar Sweep",
    url = "https://adventofcode.com/2021/day/1",
    date = Date(day = 1, year = 2001)
)
class Day1(private val measurements: List<Int>) : Puzzle {

    override fun partOne(): Int =
        measurements
            .zipWithNext()
            .count { it.first < it.second }

    override fun partTwo() =
        measurements
            .windowed(3)
            .map { it.sum() }
            .zipWithNext()
            .count { it.first < it.second }
}