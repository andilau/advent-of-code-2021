package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 3")
class Day3Test {
    private val input =
        """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
        """.trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @Test
        fun powerConsumptionOfTheSubmarineShouldEqual_198() {
            assertThat(Day3(input).partOne()).isEqualTo(22 * 9)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun lifeSupportRatingOfTheSubmarineShouldEqual_230() {
            assertThat(Day3(input).partTwo()).isEqualTo(23 * 10)
        }
    }
}