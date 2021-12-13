package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 13")
class Day13Test {

    private val example = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
            
            fold along y=7
            fold along x=5
""".trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Example")
        internal fun example() {
            // Arrange

            // Act
            val actual = Day13(example).partOne()
            // Assert
            assertThat(actual).isEqualTo(17)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {

    }
}