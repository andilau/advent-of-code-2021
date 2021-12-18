package days

import days.Day18.SnailfishNumber.Pair
import days.Day18.SnailfishNumber.Regular
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.DynamicTest.dynamicTest

@DisplayName("Day 18")
class Day18Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {

        @TestFactory
        fun `Snailfish numbers should parse to itself`(): List<DynamicTest> = """
                [1,2]
                [[1,2],3]
                [9,[8,7]]
                [[1,9],[8,5]]
                [[[[1,2],[3,4]],[[5,6],[7,8]]],9]
                [[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]
                [[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]
                """.trimIndent().lines()
            .map { number ->
                dynamicTest("""Snailfish number "$number" should parse to itself""") {
                    assertThat(Day18.fromAndToStringOf(number)).isEqualTo(number)
                }
            }

        @Test
        fun `Sailfish number is eligible for exploding pair`() {
            assertThat(Day18.explodingTermOf("[[[[[9,8],1],2],3],4]"))
                .isNotNull
                .isInstanceOf(Pair::class.java)
                .hasToString("[9,8]")
        }

        @Test
        fun `Sailfish number is eligible for splitting regular`() {
            assertThat(Day18.splittingTermOf("[[[[0,7],4],[15,[0,13]]],[1,1]]"))
                .isNotNull
                .isInstanceOf(Regular::class.java)
                .hasToString("15")
        }

        @TestFactory
        fun `Snailfish number should reduce in steps`(): List<DynamicTest> = listOf(
            "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]",
            "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]",
            "[[[[0,7],4],[15,[0,13]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
        ).windowed(2).mapIndexed { i, (unreduced, reduced) ->
            dynamicTest("""Step $i: $unreduced to $reduced """) {
                assertThat(Day18.reducedFormOf(unreduced)).isEqualTo(reduced)
            }
        }

        @TestFactory
        fun `Snailfish numbers should sum up to`(): List<DynamicTest> = listOf(
            "[1,1]\n[2,2]\n[3,3]\n[4,4]" to "[[[[1,1],[2,2]],[3,3]],[4,4]]",
            "[1,1]\n[2,2]\n[3,3]\n[4,4]\n[5,5]" to "[[[[3,0],[5,3]],[4,4]],[5,5]]",
            "[1,1]\n[2,2]\n[3,3]\n[4,4]\n[5,5]\n[6,6]" to "[[[[5,0],[7,4]],[5,5]],[6,6]]",
            """
                [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
                [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
                [7,[5,[[3,8],[1,4]]]]
                [[2,[2,2]],[8,[8,1]]]
                [2,9]
                [1,[[[9,3],9],[[9,0],[0,7]]]]
                [[[5,[7,4]],7],1]
                [[[[4,2],2],6],[8,7]]
                """.trimIndent() to "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
        ).map { (numbers, sum) ->
            dynamicTest("""Snailfish numbers "$numbers" should sum to "$sum"""") {
                val lines = numbers.lines()
                val actual = Day18(lines).reduce().toString()
                assertThat(actual).isEqualTo(sum)
            }
        }

        @TestFactory
        fun `Magnitude of snailfish numbers should equal to`(): List<DynamicTest> = listOf(
            "[[1,2],[[3,4],5]]" to 143,
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" to 1384,
            "[[[[1,1],[2,2]],[3,3]],[4,4]]" to 445,
            "[[[[3,0],[5,3]],[4,4]],[5,5]]" to 791,
            "[[[[5,0],[7,4]],[5,5]],[6,6]]" to 1137,
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]" to 3488
        ).map { (sum, magnitude) ->
            dynamicTest("""Magnitude of snailfish number "$sum" should equal to $magnitude""") {
                assertThat(Day18.magnitudeOf(sum)).isEqualTo(magnitude)
            }
        }

        @Test
        fun `Example homework assignment, magnitude of final sum is 4140`() {
            assertThat(Day18(homework).partOne()).isEqualTo(4140)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Example homework assignment, largest magnitude of any sum is 4140`() {
            assertThat(Day18(homework).partTwo()).isEqualTo(3993)
        }
    }

    private val homework = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
            """.trimIndent().lines()
}