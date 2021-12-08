@file:Suppress("SpellCheckingInspection")

package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 8")
@SuppressWarnings("SpellCheckingInspection")
class Day8Test {
    private val singleEntry =
        "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab |cdfeb fcadb cdfeb cdbaf".lines()

    private val multipleEntriesWithExpected = listOf(
        "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb |fdgacbe cefdb cefbgd gcbe" to 8394,
        "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec |fcgedb cgb dgebacf gc" to 9781,
        "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef |cg cg fdcagb cbg" to 1197,
        "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega |efabcd cedba gadfec cb" to 9361,
        "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga |gecf egdcabf bgf bfgea" to 4873,
        "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf |gebdcfa ecba ca fadegcb" to 8418,
        "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf |cefg dcbef fcge gbcadfe" to 4548,
        "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd |ed bcgafe cdgba cbgef" to 1625,
        "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg |gbdfcae bgc cg cgb" to 8717,
        "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc |fgae cfgab fg bagce" to 4315,
    )

    private val multipleEntries = multipleEntriesWithExpected.map { it.first }.toList()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Simple digits (1,4,7,8) should sum up to 26")
        fun digits_1_4_7_8_ShouldAppear_26Times() {
            assertThat(Day8(multipleEntries).partOne()).isEqualTo(26)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        @DisplayName("Single Entries should decode display to 5353")
        fun singleEntryShouldEqualTo_5353() {
            val (signal, display) = parseInput(singleEntry.first())
            assertThat(Day8.decodeOutput(signal, display)).isEqualTo("5353")
        }

        @TestFactory
        @DisplayName("Multiple Entries should decode display")
        internal fun multipleEntriesWithExpected(): List<DynamicTest> =
            multipleEntriesWithExpected.map { (input, expected) ->
                DynamicTest.dynamicTest("""Signal: "$input" => Display: $expected""") {
                    val (signal, display) = parseInput(input)
                    assertThat(Day8.decodeOutput(signal, display)).isEqualTo(expected.toString())
                }
            }

        @Test
        @DisplayName("All Entries should sum up to 61229")
        fun allEnriesShouldSumTo_61229() {
            assertThat(Day8(multipleEntries).partTwo()).isEqualTo(61_229)
        }

        private fun parseInput(input: String) = input.split(" |").map { it.split(" ") }
    }
}