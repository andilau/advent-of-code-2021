import days.Day1
import days.Day2
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
    )
        .map { (day, answers) ->
            DynamicTest.dynamicTest("${day.javaClass.simpleName} -> ${answers.first} / ${answers.second}") {
                assertThat(day.partOne()).isEqualTo(answers.first)
                assertThat(day.partTwo()).isEqualTo(answers.second)
            }
        }
}