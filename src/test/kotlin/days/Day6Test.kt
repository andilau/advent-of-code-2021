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
        fun populationAfter_18GenerationsShouldEqual_26() {
            assertThat(Day6(input).populationAt(18)).isEqualTo(26)
        }

        @Test
        fun populationAfter_80GenerationsShouldEqual_5934() {
            assertThat(Day6(input).partOne()).isEqualTo(5934)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun populationAfter_80GenerationsShouldEqual_26984457539() {
            assertThat(Day6(input).partTwo()).isEqualTo(26_984_457_539)
        }
    }
}