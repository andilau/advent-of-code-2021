package days

@AdventOfCodePuzzle(
    name = "Syntax Scoring",
    url = "https://adventofcode.com/2021/day/10",
    date = Date(day = 10, year = 2021)
)
class Day10(private val parentheses: List<String>) : Puzzle {

    override fun partOne() =
        parentheses
            .mapNotNull { line -> line.charCorrupted() }
            .also { println("$it") }
            .mapScore()

    fun Iterable<Char>.mapScore() =
        fold(0) { score, char ->
            score + when (char) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 1
            }

        }

    override fun partTwo() =
        0
}

private fun CharSequence.charCorrupted(): Char? {
    println("this = ${this}")
    val pairs = listOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<'
    ).toMap()
    val opens = "([{<"
    val closes = ")]}>"
    val stack = mutableListOf<Char>()
    forEach { c ->
        if (c in opens) stack.add(c)
        else if (c in closes && stack.last() == pairs[c]) stack.removeLast()
        else if (c in closes) return c
        //println("stack =$c  ${stack.joinToString("")}")
    }
    return null
}
