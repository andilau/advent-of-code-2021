package days

@AdventOfCodePuzzle(
    name = "Snailfish",
    url = "https://adventofcode.com/2021/day/18",
    date = Date(day = 18, year = 2021)
)
class Day18(val input: List<String>) : Puzzle {
    val snailFishNumbers = input.map(SnailfishNumber::from)

    override fun partOne(): Int =
        snailFishNumbers
            .reduce { a, b -> a + b }
            .magnitude()

    override fun partTwo(): Int =
        snailFishNumbers.flatMap { i ->
            snailFishNumbers.map { j -> i to j }
        }
            .filterNot { (i, j) -> i == j }
            .map { (i, j) -> i + j }
            .maxOf { it.magnitude() }

    companion object {
        internal fun toString(sailfish: String) = SnailfishNumber.from(sailfish).toString()
        internal fun magnitudeOf(sailfish: String) = SnailfishNumber.from(sailfish).magnitude()
        internal fun sailfishForExplode(sailfish: String) = SnailfishNumber.from(sailfish).findPairEligibleForExplode()
        internal fun sailfishForSplit(sailfish: String) = SnailfishNumber.from(sailfish).findRegularEligibleForSplit()
        internal fun reducedAsString(sailfish: String): String {
            val s = SnailfishNumber.from(sailfish)
            return s.reduce()?.let { s.replace(it) }.toString()
        }
    }


    sealed class SnailfishNumber {
        class Regular(val value: Int) : SnailfishNumber() {
            override fun toString() = "$value"
        }

        class Pair(val left: SnailfishNumber, val right: SnailfishNumber) : SnailfishNumber() {
            override fun toString() = "[$left,$right]"
        }

        operator fun plus(other: SnailfishNumber): SnailfishNumber =
            generateSequence<SnailfishNumber>(Pair(this, other)) { s -> s.reduce()?.let { s.replace(it) } }.last()

        fun findRegularEligibleForSplit(): Regular? = when (this) {
            is Pair -> {
                left.findRegularEligibleForSplit()?.run { return this }
                right.findRegularEligibleForSplit()?.run { return this }
            }
            is Regular -> if (value >= 10) this else null
        }

        fun findPairEligibleForExplode(depth: Int = 4): Pair? = when (this) {
            is Pair -> {
                if (depth == 0) this
                else {
                    left.findPairEligibleForExplode(depth - 1)?.run { return this }
                    right.findPairEligibleForExplode(depth - 1)?.run { return this }
                }
            }
            is Regular -> null
        }

        private fun asList(pair: Pair): List<SnailfishNumber> = when (this) {
            is Regular -> listOf(this)
            is Pair -> if (this == pair) listOf(this) else left.asList(pair) + right.asList(pair)
        }

        fun reduce(): Map<SnailfishNumber, SnailfishNumber>? {
            findPairEligibleForExplode()?.run {
                val sub = mutableMapOf<SnailfishNumber, SnailfishNumber>(this to Regular(0))

                val asList = this@SnailfishNumber.asList(this)
                val index = asList.indexOf(this)

                val regularLeft = asList.getOrNull(index - 1) as Regular?
                regularLeft?.let { sub[it] = Regular(it.value + (left as Regular).value) }

                val regularRight = asList.getOrNull(index + 1) as Regular?
                regularRight?.let { sub[it] = Regular(it.value + (right as Regular).value) }

                return sub
            }
            findRegularEligibleForSplit()?.run {
                return mapOf(
                    this to Pair(
                        Regular(value / 2),
                        Regular((value + 1) / 2)
                    )
                )
            }
            return null
        }

        fun replace(substitute: Map<SnailfishNumber, SnailfishNumber>): SnailfishNumber {
            substitute[this]?.let {
                return it
            }
            return when (this) {
                is Regular -> this
                is Pair -> Pair(left.replace(substitute), right.replace(substitute))
            }
        }

        fun magnitude(): Int = when (this) {
            is Regular -> value
            is Pair -> 3 * left.magnitude() + 2 * right.magnitude()
        }

        companion object {
            fun from(line: String): SnailfishNumber {
                var index = 0

                fun parse(): SnailfishNumber {
                    if (line[index] == '[') {
                        index++ // '['
                        val x = parse()
                        index++ // ','
                        val y = parse()
                        index++ // ']'
                        return Pair(x, y)
                    }
                    val begin = index
                    while (line[index].isDigit()) index++
                    return Regular(line.substring(begin, index).toInt())
                }
                return parse()
            }
        }
    }
}