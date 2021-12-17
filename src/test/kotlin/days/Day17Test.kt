package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 17")
class Day17Test {

    val example = "target area: x=20..30, y=-10..-5"

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Highest y position of probe and still be within the target should equal to 45`() {
            assertThat(Day17(example).partOne()).isEqualTo(45)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `All distinct initial velocity values which cause the probe to hit target should equal to 112`() {
            assertThat(Day17(example).partTwo()).isEqualTo(112)
        }
    }
}