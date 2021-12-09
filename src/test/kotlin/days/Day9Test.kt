@file:Suppress("SpellCheckingInspection")

package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 9")
@SuppressWarnings("SpellCheckingInspection")
class Day9Test {
    private val seafloor =
        """
2199943210
3987894921
9856789892
8767896789
9899965678""".trimIndent().lines()
    private val seafloorConnecting =
        """
2199943210
3987894921
8856789892
8767896789
9899965678""".trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Sum Of The Risk Levels Of All Low Points Should Be 15")
        fun sumOfTheRiskLevelsOfAllLowPointsShouldBe_15() {
            assertThat(Day9(seafloor).partOne()).isEqualTo(15)
        }
    }

    // three largest basins and multiply their sizes together
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Product of the size of the three largest basins should be 1134`() {
            assertThat(Day9(seafloor).partTwo()).isEqualTo(1134)
        }
        @Test
        fun `Product of the size of the three largest basins with Connect should be 1134`() {
            assertThat(Day9(seafloorConnecting).partTwo()).isEqualTo(9*9*18)
        }
    }

}