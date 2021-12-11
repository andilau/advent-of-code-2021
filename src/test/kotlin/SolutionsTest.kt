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
    )
        .map { (day, answers) ->
            DynamicTest.dynamicTest("${day.javaClass.simpleName} -> ${answers.first} / ${answers.second}") {
                assertThat(day.partOne()).isEqualTo(answers.first)
                assertThat(day.partTwo()).isEqualTo(answers.second)
            }
        }
}