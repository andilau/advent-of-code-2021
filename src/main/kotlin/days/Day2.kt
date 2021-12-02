package days

@AdventOfCodePuzzle(
    name = "Dive!",
    url = "https://adventofcode.com/2021/day/2",
    date = Date(day = 2, year = 2021)
)
class Day2(private val course: List<String>) : Puzzle {

    override fun partOne() =
        Submarine().navigate(course).horizontalTimesDepth()

    override fun partTwo() =
        SubmarineWithAim().navigate(course).horizontalTimesDepth()

    private fun NauticalVehicle.navigate(commands: List<String>) =
        commands
            .map { Command.of(it) }
            .fold(this) { submarine, command ->
                when (command.command) {
                    "forward" -> submarine.forward(command.units)
                    "down" -> submarine.down(command.units)
                    "up" -> submarine.up(command.units)
                    else -> throw IllegalArgumentException("Unknown command: $command")
                }
            }

    private fun NauticalVehicle.horizontalTimesDepth() = horizontal.toLong() * depth

    interface NauticalVehicle {
        val horizontal: Int
        val depth: Int

        fun forward(units: Int): NauticalVehicle
        fun down(units: Int): NauticalVehicle
        fun up(units: Int): NauticalVehicle
    }

    data class Submarine(
        override val horizontal: Int = 0,
        override val depth: Int = 0
    ) : NauticalVehicle {
        override fun forward(units: Int) = copy(horizontal = horizontal + units)
        override fun down(units: Int) = copy(depth = depth + units)
        override fun up(units: Int) = copy(depth = depth - units)
    }

    data class SubmarineWithAim(
        override val horizontal: Int = 0,
        override val depth: Int = 0,
        val aim: Int = 0
    ) : NauticalVehicle {
        override fun forward(units: Int) = copy(horizontal = horizontal + units, depth = depth + aim * units)
        override fun down(units: Int) = copy(aim = aim + units)
        override fun up(units: Int) = copy(aim = aim - units)
    }

    data class Command(val command: String, val units: Int) {
        companion object {
            fun of(line: String): Command {
                return Command(
                    line.substringBefore(" "),
                    line.substringAfter(" ").toInt()
                )
            }
        }
    }
}