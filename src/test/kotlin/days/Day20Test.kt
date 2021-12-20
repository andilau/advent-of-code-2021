package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.InputReader

@DisplayName("Day 20")
class Day20Test {
    val example = """..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

#..#.
#....
##..#
..#..
..###""".lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `After enhancing the original input image twice, 35 pixels are lit`() {
            assertThat(Day20(example).partOne()).isEqualTo(35)
        }

        @Test
        fun `After enhancing the original input image twice, 5395 pixels are lit`() {
            val actual = InputReader.getInputAsList(20)
            assertThat(Day20(actual).partOne()).isEqualTo(5395)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `After enhancing the original input image 50 times, 3351 pixels are lit`() {
            assertThat(Day20(example).partTwo()).isEqualTo(3351)
        }

        @Test
        fun `After enhancing the original input image 50 times, 5395 pixels are lit`() {
            val actual = InputReader.getInputAsList(20)
            assertThat(Day20(actual).partTwo()).isEqualTo(17584)
        }
    }
}