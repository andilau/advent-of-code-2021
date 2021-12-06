package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 6")
class Day6Test {
    private val input = "3,4,3,1,2"

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun horizontalAndVerticalLinesShouldOverlapIn_5Points() {
            assertThat(Day6(input).partOne()).isEqualTo(5934)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun allLinesShouldOverlapIn_12Points() {
            assertThat(Day6(input).partTwo()).isEqualTo(5934)
        }
    }
}