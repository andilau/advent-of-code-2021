package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 2")
class Day2Test {
    private val commands = """
forward 5
down 5
forward 8
up 3
down 8
forward 2
            """.trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @Test
        fun commandsShouldDirectTo_Horizontal15_Depth10() {
            assertThat(Day2(commands).partOne()).isEqualTo(15 * 10)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun commandsShouldDirectTo_Horizontal15_Depth60() {
            assertThat(Day2(commands).partTwo()).isEqualTo(15*60)
        }
    }
}