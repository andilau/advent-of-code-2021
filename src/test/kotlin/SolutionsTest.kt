import days.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import util.InputReader

@DisplayName("Solutions")
class SolutionsTest {

    @TestFactory
    fun testAdventOfCode() = listOf(
        Day1(InputReader.getInputAsListOfInt(1)) to Pair(1583, 1627),
        Day2(InputReader.getInputAsList(2)) to Pair(1_746_616L, 1_741_971_043L),
        Day3(InputReader.getInputAsList(3)) to Pair(3_429_254, 5_410_338),
        Day4(InputReader.getInputAsList(4)) to Pair(4662, 12_080),
        Day5(InputReader.getInputAsList(5)) to Pair(7414, 19_676),
        Day6(InputReader.getInputAsString(6)) to Pair(351_188L, 1595_779_846_729L),
        Day7(InputReader.getInputAsString(7)) to Pair(348_996, 98_231_647),
        Day8(InputReader.getInputAsList(8)) to Pair(534, 1_070_188),
        Day9(InputReader.getInputAsList(9)) to Pair(498, 1_071_000),
        Day10(InputReader.getInputAsList(10)) to Pair(387_363, 4_330_777_059),
        Day11(InputReader.getInputAsList(11)) to Pair(1634, 210),
        Day12(InputReader.getInputAsList(12)) to Pair(4241, 122134),
        Day13(InputReader.getInputAsList(13)) to Pair(706, Unit),
        Day14(InputReader.getInputAsList(14)) to Pair(2549L, 2_516_901_104_210),
        Day15(InputReader.getInputAsList(15)) to Pair(652, 2938),
        Day16(InputReader.getInputAsString(16)) to Pair(974, 180_616_437_720),
        Day17(InputReader.getInputAsString(17)) to Pair(25200, 3012),
        Day18(InputReader.getInputAsList(18)) to Pair(4347, 4721),
    )
        .map { (day, answers) ->
            DynamicTest.dynamicTest("${day.javaClass.simpleName} -> ${answers.first} / ${answers.second}") {
                assertThat(day.partOne()).isEqualTo(answers.first)
                assertThat(day.partTwo()).isEqualTo(answers.second)
            }
        }
}