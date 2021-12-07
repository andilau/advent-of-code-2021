package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 7")
class Day7Test {
    private val example = "16,1,2,0,4,2,7,1,2,14"

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun leastEnergyToMoveFromEmptyShouldEqual_0() {
            assertThat(Day7("").partOne()).isEqualTo(0)
        }

        @Test
        fun leastEnergyToMoveFromPosition_0_ShouldEqual_0() {
            assertThat(Day7("0").partOne()).isEqualTo(0)
        }

        @Test
        fun leastEnergyToMoveFromPositions_1_2_3_ShouldEqual_2() {
            assertThat(Day7("1,2,3").partOne()).isEqualTo(2)
        }

        @Test
        fun leastEnergyToMoveFromPositionsShouldEqual_37() {
            assertThat(Day7(example).partOne()).isEqualTo(37)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun leastEnergyToMoveFromPositions_1_2_3_ShouldEqual_2() {
            assertThat(Day7("1,2,3").partTwo()).isEqualTo(2)
        }

        @Test
        fun leastEnergyToMoveFromPositions_1_3_5_ShouldEqual_6() {
            assertThat(Day7("1,3,5").partTwo()).isEqualTo(6)
        }

        @Test
        fun leastEnergyToMoveFromPositionsShouldEqual_168() {
            assertThat(Day7(example).partTwo()).isEqualTo(168)
        }
    }
}