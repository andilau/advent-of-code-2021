package days

@AdventOfCodePuzzle(
    name = "Packet Decoder",
    url = "https://adventofcode.com/2021/day/16",
    date = Date(day = 16, year = 2021)
)
class Day16(transmission: String) : Puzzle {
    private val binary = hexToBin(transmission)

    override fun partOne() =
        Parser(binary).apply { parse(1) }
            .sumOfVersions()


    override fun partTwo(): Long {
        return Parser(binary)
            .parse(1)
            .first()
    }

    companion object {
        internal fun hexToBin(hex: String) =
            hex.map { char ->
                char.digitToInt(16)
                    .toString(2)
                    .padStart(4, '0')
            }.joinToString("")
    }

    class Parser(private val transmission: String) {
        private var p = 0
        private var versionSum = 0

        fun sumOfVersions() = versionSum

        fun parse(packets: Int = Int.MAX_VALUE, length: Int = Int.MAX_VALUE): ArrayList<Long> {
            val result = ArrayList<Long>()
            var number = 0
            val start = p
            while (number < packets && (p - start < length)) {
                number++
                val version = take(3)
                versionSum += version
                when (val type = take(3)) {
                    4 -> { // Literal type
                        var value = 0L
                        while (take(1) == 1) value = (16 * value) + take(4)
                        value = (16 * value) + take(4)

                        val literal = Packet.Literal(value = value, version)
                        result.add(literal.value)
                    }
                    else -> { // Operation type
                        val args = when (take(1)) {
                            0 -> parse(length = take(15))   // length of sub-packets
                            1 -> parse(packets = take(11))  // number of sub-packets
                            else -> throw IllegalStateException()
                        }
                        val packet = Packet.Operator(type, version)
                        val value = packet.evaluate(args)
                        result += value
                    }
                }
            }
            return result
        }

        private fun take(chars: Int): Int {
            val what = transmission.substring(p, p + chars).toInt(2)
            p += chars
            return what
        }
    }

    sealed class Packet() {
        abstract val version: Int
        abstract fun evaluate(args: Iterable<Long>): Long

        data class Literal(val value: Long, override val version: Int) : Packet() {
            override fun evaluate(args: Iterable<Long>) = value
        }

        data class Operator(val type: Int, override val version: Int) : Packet() {
            override fun evaluate(args: Iterable<Long>) = when (type) {
                0 -> args.sum()
                1 -> args.reduce { a, b -> a * b }
                2 -> args.minOf { it }
                3 -> args.maxOf { it }
                5 -> if (args.first() > args.last()) 1L else 0L     // greater than
                6 -> if (args.first() < args.last()) 1L else 0L      // less than
                7 -> if (args.first() == args.last()) 1L else 0L      // equal to
                else -> error("Unknown operation of type: $type")
            }
        }
    }
}