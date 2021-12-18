package days

import days.Day18.SnailfishNumber.Pair
import days.Day18.SnailfishNumber.Regular
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 18")
class Day18Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        private var example = listOf(
            "[[1,2],[[3,4],5]]" to 143,
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" to 1384,
            "[[[[1,1],[2,2]],[3,3]],[4,4]]" to 445,
            "[[[[3,0],[5,3]],[4,4]],[5,5]]" to 791,
            "[[[[5,0],[7,4]],[5,5]],[6,6]]" to 1137,
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]" to 3488
        )

        @TestFactory
        fun `From String`(): List<DynamicTest> =
            example
                .map { (number, _) ->
                    DynamicTest.dynamicTest("""Sailfish number "$number" should parse""") {
                        assertThat(Day18.toString(number)).isEqualTo(number)
                    }
                }

        @TestFactory
        fun `Magnitude of sum`(): List<DynamicTest> = example
            .map { (sum, magnitude) ->
                DynamicTest.dynamicTest("""Magnitude of sum $sum should equal to $magnitude""") {
                    assertThat(Day18.magnitudeOf(sum)).isEqualTo(magnitude)
                }
            }

        @Test
        fun `Is Explodable`() {
            assertThat(Day18.sailfishForExplode("[[[[[9,8],1],2],3],4]"))
                .isNotNull
                .isInstanceOf(Pair::class.java)
            //    .isEqualTo(Pair(Regular(9), Regular(8)))
        }

        @Test
        fun `Is Splittable`() {
            assertThat(Day18.sailfishForSplit("[[[[0,7],4],[15,[0,13]]],[1,1]]"))
                .isNotNull
                .isInstanceOf(Regular::class.java)
            //    .isEqualTo(Regular(15))
        }

        @TestFactory
        fun `Not Splittable`(): List<DynamicTest> =
            example
                .map { (number, _) ->
                    DynamicTest.dynamicTest("""$number should not be eligible for split""") {
                        assertThat(Day18.sailfishForSplit(number)).isNull()
                    }
                }

        @TestFactory
        fun reduce(): List<DynamicTest> = listOf(
            "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]",
            "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]",
            "[[[[0,7],4],[15,[0,13]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
        ).windowed(2).mapIndexed() { i, (unreduced, reduced) ->
            DynamicTest.dynamicTest("""Step $i: $unreduced should reduce to $reduced """) {
                assertThat(Day18.reducedAsString(unreduced)).isEqualTo(reduced)
            }
        }


        @Test
        @DisplayName("Homework")
        fun homework() {
            assertThat(Day18(homework).partOne()).isEqualTo(4140)
        }

   }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        @DisplayName("Homework 2")
        fun homework2() {
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