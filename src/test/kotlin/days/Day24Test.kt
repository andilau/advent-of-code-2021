package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.InputReader

@DisplayName("Day 24")
class Day24Test {
        @Test
        fun `Program should compute negative of X`() {
            val input = """
                inp x
                mul x -1
                """.trimIndent().lines()
            assertThat(Day24(input).computeState(listOf(1))).isEqualTo(Day24.State(0, -1, 0, 0, 0))
        }

        @Test
        fun `Program should compute Z=3 and set X=3`() {
            val input = """
                inp z
                inp x
                mul z 3
                """.trimIndent().lines()
            assertThat(Day24(input).computeState(listOf(1, 3))).isEqualTo(Day24.State(0, 3, 0, 3, 0))
        }

        @Test
        fun `Program should compute Z=1 if the second input number is three times larger than the first`() {
            val input = """
                inp z
                inp x
                mul z 3
                eql z x
                """.trimIndent().lines()
            assertThat(Day24(input).computeState(listOf(1, 3))).isEqualTo(Day24.State(0, 3, 0, 1, 0))
        }

        @Test
        fun `Program should convert positive number into binary`() {
            val input = """
                inp w
                add z w
                mod z 2
                div w 2
                add y w
                mod y 2
                div w 2
                add x w
                mod x 2
                div w 2
                mod w 2
                """.trimIndent().lines()
            assertThat(Day24(input).computeState(listOf(15))).isEqualTo(Day24.State(1, 1, 1, 1, 0))
        }

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `98_998_519_596_997 should be the largest model number accepted by MONAD`() {
            assertThat(Day24(InputReader.getInputAsList(24)).partOne()).isEqualTo(98_998_519_596_997)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `31_521_119_151_421 should be the smallest model number accepted by MONAD`() {
            assertThat(Day24(InputReader.getInputAsList(24)).partTwo()).isEqualTo(31_521_119_151_421)
        }
    }
}