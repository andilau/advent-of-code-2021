package days

@AdventOfCodePuzzle(
    name = "Syntax Scoring",
    url = "https://adventofcode.com/2021/day/10",
    date = Date(day = 10, year = 2021)
)
class Day10(private val chunks: List<String>) : Puzzle {

    override fun partOne() =
        chunks
            .mapNotNull { chunk -> chunk.firstIllegalCharacter() }
            .syntaxCheckerScore()

    override fun partTwo() =
        chunks
            .filter { chunk -> chunk.firstIllegalCharacter() == null }
            .map { it.autoComplete() }
            .map { it.autoCompleteScore() }
            .sorted()
            .middle()

    private fun Iterable<Char>.autoCompleteScore() =
        fold(0L) { score, char ->
            score * 5 + when (char) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
        }

    private fun Iterable<Char>.syntaxCheckerScore() =
        fold(0) { score, char ->
            score + when (char) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }
        }

    private fun CharSequence.firstIllegalCharacter(): Char? {
        val stack = mutableListOf<Char>()
        forEach { char ->
            if (char in closedToOpen.values) stack.add(char)
            else if (char in closedToOpen.keys) {
                if (stack.last() == closedToOpen[char]) stack.removeLast()
                else if (char in closedToOpen.keys) return char
            }
        }
        return null
    }

    private fun CharSequence.autoComplete(): Iterable<Char> {
        return fold("") { string, char ->
            when {
                (char in closedToOpen.values) -> string.plus(char)
                (char in closedToOpen.keys && string.last() == closedToOpen[char]) -> string.dropLast(1)
                else -> string
            }
        }
            .map { c -> closedToOpen.filterValues { it == c }.firstNotNullOf { it.key } }
            .reversed()
    }

    private fun <E> Collection<E>.middle(): E = this.drop(this.size / 2).first()

    companion object {
        val closedToOpen = listOf(
            ')' to '(',
            ']' to '[',
            '}' to '{',
            '>' to '<'
        ).toMap()
    }
}