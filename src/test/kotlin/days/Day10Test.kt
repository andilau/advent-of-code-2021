package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 10")
class Day10Test {

    private val parentheses =
        """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        @DisplayName("Sum Of The Risk Levels Of All Low Points Should Be 15")
        fun sumOfTheRiskLevelsOfAllLowPointsShouldBe_26397() {
            assertThat(Day10(parentheses).partOne()).isEqualTo(26397)
        }

        @Test
        @DisplayName("Sum Of The Risk Levels Of All Low Points Should Be corrupted")
        fun sumOfTheRiskLevelsOfAllLowPointsShouldBe_corrupted() {
            assertThat(Day10("{([(<{}[<>[]}>{[]{[(<()>".lines()).partOne()).isEqualTo(1197)
        }
    }


    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
    }
}