package util

import days.Puzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.reflections.Reflections
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
object Runner {
    private val puzzleClasses by lazy {
        Reflections("days").getSubTypesOf(Puzzle::class.java)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val solutions = puzzleClasses.map { dayNumber(it.simpleName) }.toSet()

        args.map { it.toIntOrNull() ?: error("Day argument must be an integer") }
            .filter { it !in solutions }
            .forEach { error("No solution for day $it found") }

        val days: List<Int>? = args
            .mapNotNull { it.toIntOrNull() }
            .filter { it in solutions }
            .takeIf { it.isNotEmpty() }

        puzzleClasses
            .sortedBy { dayNumber(it.simpleName) }
            .filter { days == null || dayNumber(it.simpleName) in days }
            .takeIf { it.isNotEmpty() }
            ?.forEach { printDay(it) }
            ?: printError("Days $days not found")
    }

    private fun printDay(puzzleClass: Class<out Puzzle>) {
        val dayNumber: Int = dayNumber(puzzleClass.simpleName)
        println("\n=== DAY $dayNumber (${puzzleClass.simpleName}) ===")

        val puzzle: Puzzle = try {
            puzzleClass.constructors[0].newInstance() as Puzzle
        } catch (e: IllegalArgumentException) {
            when (val type = puzzleClass.constructors[0].genericParameterTypes[0].typeName) {
                "java.lang.String" -> {
                    puzzleClass.constructors[0].newInstance(InputReader.getInputAsString(dayNumber)) as Puzzle
                }
                "java.util.List<java.lang.String>" -> {
                    puzzleClass.constructors[0].newInstance(InputReader.getInputAsList(dayNumber)) as Puzzle
                }
                "java.util.List<java.lang.Integer>" -> {
                    puzzleClass.constructors[0].newInstance(InputReader.getInputAsListOfInt(dayNumber)) as Puzzle
                }
                "java.util.List<java.lang.Long>" -> {
                    puzzleClass.constructors[0].newInstance(InputReader.getInputAsListOfLong(dayNumber)) as Puzzle
                }
                else -> error("Cant apply input: $type")
            }
        }

        runBlocking(Dispatchers.IO) {
            val d1 = async { measureTimedValue { puzzle.partOne() } }
            val d2 = async { measureTimedValue { puzzle.partTwo() } }
            with(d1.await()) {
                println("Part 1: $value   (${duration})")
            }
            with(d2.await()) {
                println("Part 2: $value   (${duration})")
            }
        }
    }

    private fun printError(message: String) =
        System.err.println("\n=== ERROR ===\n$message")

    private fun dayNumber(dayClassName: String): Int =
        NUMBER_PATTERN.find(dayClassName)?.value?.toIntOrNull() ?: 0

    private val NUMBER_PATTERN = Regex("""(\d+)""")
}