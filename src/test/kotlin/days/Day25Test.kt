package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 25")
class Day25Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Seafloor should parse and equal itself`() {
            assertThat(Day25(example[0]!!).asString()).isEqualTo(example[0]!!.joinToString("\n"))
        }

        @Test
        fun `Movement only the rightmost sea cucumber should have moved`() {
            assertThat(Day25("...>>>>>...".lines()).stepAndList()).isEqualTo("...>>>>.>..".lines())
        }

        @Test
        fun `Movement after the next step, two sea cucumbers move`() {
            assertThat(Day25("...>>>>.>..".lines()).stepAndList()).isEqualTo("...>>>.>.>.".lines())
        }

        @Test
        fun `Movement during a single step, the east-facing herd moves first then the south-facing herd moves`() {
            val input = """
                ..........
                .>v....v..
                .......>..
                ..........
                """.trimIndent().lines()
            val expected = """
                ..........
                .>........
                ..v....v>.
                ..........
                """.trimIndent().lines()
            assertThat(Day25(input).stepAndList()).isEqualTo(expected)
        }

        @TestFactory
        fun `Movement sea cucumbers appear on the opposite of the map if destination is empty`(): List<DynamicTest> =
            states
                .map(String::lines)
                .zipWithNext()
                .mapIndexed() { i, (input, expected) ->
                    DynamicTest.dynamicTest("Sea cucumbers appear on the opposite of the map if destination is empty $i") {
                        assertThat(Day25(input).stepAndList()).isEqualTo(expected)
                    }
                }

        @TestFactory
        fun `Sea cucumbers map should transition to next map`(): List<DynamicTest> =
            example.toSortedMap()
                .toList()
                .zipWithNext()
                .map() { (prev, next) ->
                    DynamicTest.dynamicTest("Step: ${prev.first} -> ${next.first} ") {
                        val steps = next.first - prev.first
                        assertThat(Day25(prev.second).stepAndList(steps)).isEqualTo(next.second)
                    }
                }

        @TestFactory
        fun `Sea cucumbers map should transition steps to next map`(): List<DynamicTest> =
            example
                .filterNot { it.key == 0 }
                .toSortedMap()
                .map { (step, map) ->
                    DynamicTest.dynamicTest("Step: $step ") {
                        assertThat(Day25(example[0]!!).stepAndList(step)).isEqualTo(map)
                    }
                }

        @Test
        fun `What is the first step on which no sea cucumbers move`() {
            assertThat(Day25(example[0]!!).partOne()).isEqualTo(58)
        }
    }

    val states = listOf(
        """
        ...>...
        .......
        ......>
        v.....>
        ......>
        .......
        ..vvv..""".trimIndent(),
        """
        ..vv>..
        .......
        >......
        v.....>
        >......
        .......
        ....v..""".trimIndent(),
        """
        ....v>.
        ..vv...
        .>.....
        ......>
        v>.....
        .......
        .......""".trimIndent(),
        """
        ......>
        ..v.v..
        ..>v...
        >......
        ..>....
        v......
        .......""".trimIndent(),
        """
        >......
        ..v....
        ..>.v..
        .>.v...
        ...>...
        .......
        v......""".trimIndent()
    )

    val example = """
        Initial state:
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>
        
        After 1 step:
        ....>.>v.>
        v.v>.>v.v.
        >v>>..>v..
        >>v>v>.>.v
        .>v.v...v.
        v>>.>vvv..
        ..v...>>..
        vv...>>vv.
        >.v.v..v.v
        
        After 2 steps:
        >.v.v>>..v
        v.v.>>vv..
        >v>.>.>.v.
        >>v>v.>v>.
        .>..v....v
        .>v>>.v.v.
        v....v>v>.
        .vv..>>v..
        v>.....vv.
        
        After 3 steps:
        v>v.v>.>v.
        v...>>.v.v
        >vv>.>v>..
        >>v>v.>.v>
        ..>....v..
        .>.>v>v..v
        ..v..v>vv>
        v.v..>>v..
        .v>....v..
        
        After 4 steps:
        v>..v.>>..
        v.v.>.>.v.
        >vv.>>.v>v
        >>.>..v>.>
        ..v>v...v.
        ..>>.>vv..
        >.v.vv>v.v
        .....>>vv.
        vvv>...v..
        
        After 5 steps:
        vv>...>v>.
        v.v.v>.>v.
        >.v.>.>.>v
        >v>.>..v>>
        ..v>v.v...
        ..>.>>vvv.
        .>...v>v..
        ..v.v>>v.v
        v.v.>...v.
        
        ...
        
        After 10 steps:
        ..>..>>vv.
        v.....>>.v
        ..v.v>>>v>
        v>.>v.>>>.
        ..v>v.vv.v
        .v.>>>.v..
        v.v..>v>..
        ..v...>v.>
        .vv..v>vv.
        
        ...
        
        After 20 steps:
        v>.....>>.
        >vv>.....v
        .>v>v.vv>>
        v>>>v.>v.>
        ....vv>v..
        .v.>>>vvv.
        ..v..>>vv.
        v.v...>>.v
        ..v.....v>
        
        ...
        
        After 30 steps:
        .vv.v..>>>
        v>...v...>
        >.v>.>vv.>
        >v>.>.>v.>
        .>..v.vv..
        ..v>..>>v.
        ....v>..>v
        v.v...>vv>
        v.v...>vvv
        
        ...
        
        After 40 steps:
        >>v>v..v..
        ..>>v..vv.
        ..>>>v.>.v
        ..>>>>vvv>
        v.....>...
        v.v...>v>>
        >vv.....v>
        .>v...v.>v
        vvv.v..v.>
        
        ...
        
        After 50 steps:
        ..>>v>vv.v
        ..v.>>vv..
        v.>>v>>v..
        ..>>>>>vv.
        vvv....>vv
        ..v....>>>
        v>.......>
        .vv>....v>
        .>v.vv.v..
        
        ...
        
        After 55 steps:
        ..>>v>vv..
        ..v.>>vv..
        ..>>v>>vv.
        ..>>>>>vv.
        v......>vv
        v>v....>>v
        vvv...>..>
        >vv.....>.
        .>v.vv.v..
        
        After 56 steps:
        ..>>v>vv..
        ..v.>>vv..
        ..>>v>>vv.
        ..>>>>>vv.
        v......>vv
        v>v....>>v
        vvv....>.>
        >vv......>
        .>v.vv.v..
        
        After 57 steps:
        ..>>v>vv..
        ..v.>>vv..
        ..>>v>>vv.
        ..>>>>>vv.
        v......>vv
        v>v....>>v
        vvv.....>>
        >vv......>
        .>v.vv.v..
        
        After 58 steps:
        ..>>v>vv..
        ..v.>>vv..
        ..>>v>>vv.
        ..>>>>>vv.
        v......>vv
        v>v....>>v
        vvv.....>>
        >vv......>
        .>v.vv.v..
        """.trimIndent()
        .split(System.lineSeparator().repeat(2))
        .map(String::lines)
        .filter { it.size == 10 }
        .associate {
            Pair(
                it.first().substringAfter("After ").substringBefore(' ').toIntOrNull() ?: 0,
                it.drop(1)
            )
        }
}