package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 21")
class Day21Test {
    val example = """
        Player 1 starting position: 4
        Player 2 starting position: 8
        """.trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @Test
        fun `multiply the score of the losing player by the number of times the die was rolled during the game`() {
            assertThat(Day21(example).partOne()).isEqualTo(739785)
        }

    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `multiply the score of the losing player by the number of times the die was rolled during the game`() {
            assertThat(Day21(example).partTwo()).isEqualTo(444356092776315)
        }

    }
}