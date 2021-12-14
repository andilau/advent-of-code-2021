package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 14")
class Day14Test {
    private val example = """
        NNCB
        
        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C""".trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        val polymere = listOf(
            "NNCB",
            "NCNBCHB",
            "NBCCNBBBCBHCB",
            "NBBBCNCCNBBNBNBBCHBHHBCHB",
            "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB",
        )

        @Test
        @DisplayName("Polymer test N")
        internal fun polymerTestN() {
            assertThat(Day14(example).countChars(0)).isEqualTo(polymere[0].countChars())
            assertThat(Day14(example).countChars(1)).isEqualTo(polymere[1].countChars())
            assertThat(Day14(example).countChars(2)).isEqualTo(polymere[2].countChars())
            assertThat(Day14(example).countChars(3)).isEqualTo(polymere[3].countChars())
            assertThat(Day14(example).countChars(4)).isEqualTo(polymere[4].countChars())
        }

        private fun String.countChars() = groupingBy { it }.eachCount().values.sum().toLong()

        @Test
        @DisplayName("Polymere test After 5 Steps")
        internal fun polymereTestAfter5Steps() {
            assertThat(Day14(example).countChars(5)).isEqualTo(97)
        }

        @Test
        @DisplayName("Polymere test After 10 Steps")
        internal fun polymereTestAfter10Steps() {
            assertThat(Day14(example).countChars(10)).isEqualTo(3073)
            /*  occurs 1749 times, C occurs 298 times, H occurs 161 times, and N occurs 865 times;
            * */
        }

        @Test
        @DisplayName("Example Answer Do 10 Times")
        internal fun exampleAnswer() {
            assertThat(Day14(example).partOne()).isEqualTo(1588)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        @DisplayName("Example Answer Do 40 Times")
        internal fun Do40Times() {
            assertThat(Day14(example).partTwo()).isEqualTo(2_188_189_693_529)
        }
    }
}