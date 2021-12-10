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

    private fun CharSequence.autoCompleteScore() =
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
                else -> 1
            }
        }

    private fun CharSequence.firstIllegalCharacter(): Char? {
        val stack = mutableListOf<Char>()
        forEach { char ->
            if (char in pairs.values) stack.add(char)
            else if (char in pairs.keys) {
                if (stack.last() == pairs[char]) stack.removeLast()
                else if (char in pairs.keys) return char
            }
        }
        return null
    }

    private fun CharSequence.autoComplete(): CharSequence {
        val stack = mutableListOf<Char>()
        forEach { c ->
            if (c in pairs.values) stack.add(c)
            else if (c in pairs.keys) {
                if (stack.last() == pairs[c]) stack.removeLast()
                else if (c in pairs.keys) throw IllegalStateException("")
            }
        }
        return stack
            .reversed()
            .map { c -> pairs.filterValues { it == c }.firstNotNullOf { it.key } }
            .joinToString("")
    }

    companion object {
        val pairs = listOf(
            ')' to '(',
            ']' to '[',
            '}' to '{',
            '>' to '<'
        ).toMap()
    }
}

private fun <E> Collection<E>.middle(): E = this.drop(this.size / 2).first()
