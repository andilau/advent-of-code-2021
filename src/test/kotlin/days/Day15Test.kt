package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 15")
class Day15Test {
    private val example = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581""".trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        internal fun `Lowest total risk level to travel simple cave should equal 40`() {
            assertThat(Day15(example).partOne()).isEqualTo(40)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        internal fun `Lowest total risk level to travel extended cave should equal 315`() {
            assertThat(Day15(example).partTwo()).isEqualTo(315)
        }
    }
}