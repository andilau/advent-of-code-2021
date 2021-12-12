package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 12")
class Day12Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Number of paths that visit small caves at most once")
        fun test() {
            val actual = Day12(example).partOne()
            assertThat(actual).isEqualTo(10)
        }

        @Test
        @DisplayName("Slightly Larger Example")
        fun slightlyLargerExample() {
            val actual = Day12(slightlyLargerExample).partOne()
            assertThat(actual).isEqualTo(19)
        }

        @Test
        @DisplayName("Even Larger Example")
        fun evenLargerExample() {
            val actual = Day12(evenLargerExample).partOne()
            assertThat(actual).isEqualTo(226)
        }

    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        @DisplayName("Slightly Larger Example")
        fun slightlyLargerExample() {
            val actual = Day12(slightlyLargerExample).partTwo()
            assertThat(actual).isEqualTo(103)
        }

        @Test
        @DisplayName("Even Larger Example")
        fun evenLargerExample() {
            val actual = Day12(evenLargerExample).partTwo()
            assertThat(actual).isEqualTo(3509)
        }
    }

    val example = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end""".trimIndent().lines()

    val slightlyLargerExample = """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc""".trimIndent().lines()

    val evenLargerExample = """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW""".trimIndent().lines()
}