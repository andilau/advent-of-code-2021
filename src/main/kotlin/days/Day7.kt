package days

import kotlin.math.absoluteValue

@AdventOfCodePuzzle(
    name = "The Treachery of Whales",
    url = "https://adventofcode.com/2021/day/7",
    date = Date(day = 7, year = 2021)
)
class Day7(input: String) : Puzzle {
    private val positions = input
        .split(',')
        .filter(String::isNotEmpty)
        .map(String::toInt)
        .toIntArray()

    override fun partOne() =
        positions.minEnergyBy { it }

    override fun partTwo() =
        positions.minEnergyBy { it * (it + 1) / 2 }

    private inline fun IntArray.minEnergyBy(energyByDistance: (Int) -> Int): Int {
        val min = minOrNull() ?: 0
        val max = maxOrNull() ?: 0
        return (min..max)
            .minOfOrNull { destination ->
                sumOf { position ->
                    val distance = (destination - position).absoluteValue
                    energyByDistance.invoke(distance)
                }
            }!!
    }
}