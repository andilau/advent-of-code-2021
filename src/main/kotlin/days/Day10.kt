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
            .map { it.autocomplete() }
            .map { it.autocompleteScore() }
            .sorted()
            .middle()

    private fun Iterable<Char>.autocompleteScore() =
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
        val ok = mutableListOf<Char>()
        forEach { char ->
            if (char in opening) ok.add(char)
            else if (char in closing) {
                if (ok.last() == closedToOpen[char]) ok.removeLast()
                else if (char in closing) return char
            }
        }
        return null
    }

    private fun CharSequence.autocomplete() =
        fold("") { string, char ->
            when {
                (char in opening) -> string.plus(char)
                (char in closing && string.last() == closedToOpen[char]) -> string.dropLast(1)
                else -> string
            }
        }.map { c -> getMatchingClosing(c) }.reversed()

    companion object {
        val closedToOpen = listOf(
            ')' to '(',
            ']' to '[',
            '}' to '{',
            '>' to '<'
        ).toMap()

        private val opening get() = closedToOpen.values
        private val closing get() = closedToOpen.keys
        private fun getMatchingClosing(c: Char) = closedToOpen.filterValues { it == c }.firstNotNullOf { it.key }

        private fun <E> Collection<E>.middle(): E = this.drop(this.size / 2).first()
    }
}