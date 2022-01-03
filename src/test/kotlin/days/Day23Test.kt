package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 23")
class Day23Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Day23Fast The Least energy required to organize the amphipods is 12521`() {
            assertThat(Day23(example).partOne()).isEqualTo(12521)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Day23Fast The Least energy required to organize the amphipods is 44169`() {
            assertThat(Day23(example).partTwo()).isEqualTo(44169)
        }
    }

    val example = """
    #############
    #...........#
    ###B#C#B#D###
      #A#D#C#A#
      #########""".trimIndent().lines()
}