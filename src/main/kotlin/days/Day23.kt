package days

import java.util.*
import kotlin.math.abs

@AdventOfCodePuzzle(
    name = "Amphipod",
    url = "https://adventofcode.com/2021/day/23",
    date = Date(day = 23, year = 2021)
)

class Day23(val input: List<String>) : Puzzle {

    override fun partOne(): Int = Layout.parse(input).organizeWithLeastEnergy()

    override fun partTwo(): Int =
        buildList {
            addAll(input.slice(0..2))
            add("  #D#C#B#A#")
            add("  #D#B#A#C#")
            addAll(input.slice(3..4))
        }.let { Layout.parse(it).organizeWithLeastEnergy() }

    data class Layout(
        val size: Int,
        val energy: Int,
        val rooms: Rooms,
        val hallway: Hallway = List<Amphipod?>(11) { null }
    ) {
        private fun state() = rooms to hallway
        private fun hallwayIsFree(from: Int, to: Int = from + 1) = (from until to).all { hallway[it] == null }
        private fun Rooms.getByAmphipod(amphipod: Amphipod): Room = get(amphipod.ordinal)
        private fun Room.finished(roomNumber: Int) = isNotEmpty() && all { it.ordinal == roomNumber }

        private fun finished() =
            rooms.all { it.size == size } &&
                    rooms.withIndex().all { (index, room) -> room.finished(index) }

        fun organizeWithLeastEnergy(): Int {
            val todo = PriorityQueue(compareBy(Layout::energy))
            val done = mutableSetOf<Pair<Rooms, Hallway>>()

            fun Layout.queue() = if (this.state() !in done) todo.add(this) else null
            this.queue()

            while (todo.isNotEmpty()) {
                val layout = todo.poll()

                if (layout.finished()) return layout.energy
                if (layout.state() in done) continue
                done.add(layout.state())

                with(layout) {
                    // move amphipods to room
                    for ((from, amphipod) in hallway.withIndex()) {
                        if (amphipod == null) continue
                        if (from < amphipod.to && !hallwayIsFree(from + 1, amphipod.to)) continue
                        if (from > amphipod.to && !hallwayIsFree(amphipod.to + 1, from)) continue
                        if (rooms.getByAmphipod(amphipod).any { it != amphipod }) continue

                        val steps = size - rooms.getByAmphipod(amphipod).size + abs(amphipod.to - from)
                        Layout(
                            size,
                            energy + steps * amphipod.energy,
                            rooms.copy(amphipod.ordinal, rooms.getByAmphipod(amphipod) + amphipod),
                            hallway.copy(from, null)
                        ).queue()
                    }

                    // move amphipods to hallway
                    for ((index, room) in rooms.withIndex()) {
                        if (room.isEmpty() || room.finished(index)) continue

                        //listOf(0, 1, 3, 5, 7, 9, 10)
                        val left = ((exits[index] downTo 0)).takeWhile { hallway[it] == null }
                        val right = ((exits[index]..10)).takeWhile { hallway[it] == null }
                        for (position in (left + right)) {
                            if (position in exits) continue
                            val steps = (size - room.size + 1) + abs(exits[index] - position)
                            val amphipod = room.last()
                            Layout(
                                size,
                                energy + steps * amphipod.energy,
                                rooms.copy(index, room.dropLast(1)),
                                hallway.copy(position, amphipod)
                            ).queue()
                        }
                    }
                }
            }
            error("Solution not found")
        }

        override fun toString() = buildString {
            appendLine("#############")
            appendLine("#" + hallway.joinToString("") { it?.name ?: "." } + "#") // hallway
            appendLine("###" + rooms.joinToString("#") { it.getOrNull(0)?.name ?: "." } + "###")
            (1 until this@Layout.size).forEach { i ->
                appendLine("  #" + rooms.joinToString("#") { it.getOrNull(i)?.name ?: "." } + "#")
            }
            appendLine("  #########")
            appendLine("energy=$energy")
        }

        companion object {
            val exits = listOf(2, 4, 6, 8)

            fun parse(lines: List<String>): Layout {
                val rooms = lines
                    .flatMap { line -> line.filter { it in "ABCD" }.map(Char::toString) }
                    .map(Amphipod::valueOf)
                    .withIndex()
                    .groupBy({ it.index % 4 }, { it.value })
                    .mapValues { it.value.reversed() }
                    .values.toList()
                return Layout(rooms.first().size, 0, rooms)
            }
        }
    }
}

enum class Amphipod(val energy: Int, val to: Int) {
    A(1, 2),
    B(10, 4),
    C(100, 6),
    D(1000, 8)
}

private typealias Hallway = List<Amphipod?>
private typealias Rooms = List<Room>
private typealias Room = List<Amphipod>

fun <T> List<T>.copy(i: Int, value: T): List<T> = toMutableList().apply { set(i, value) }