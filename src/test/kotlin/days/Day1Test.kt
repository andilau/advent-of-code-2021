package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 1")
class Day1Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        private val measurements = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
            """.trimIndent().lines().map(String::toInt)

        @Test
        fun measurementsShouldIncrease7Times() {
            assertThat(Day1(measurements).partOne()).isEqualTo(7)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        private val measurements = """
            199  A      
            200  A B    
            208  A B C  
            210    B C D
            200  E   C D
            207  E F   D
            240  E F G  
            269    F G H
            260      G H
            263        H
            """.trimIndent().lines()
            .map { it.substringBefore(" ") }
            .map(String::toInt)

        @Test
        fun measurementsWindowedWithSumsShouldIncrease5Times() {
            assertThat(Day1(measurements).partTwo()).isEqualTo(5)
        }
    }
}