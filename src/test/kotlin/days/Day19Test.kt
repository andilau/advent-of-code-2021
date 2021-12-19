package days

import days.Day19.Beacon
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.InputReader

@DisplayName("Day 19")
class Day19Test {
    val input = InputReader.getInputAsString(19)

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test fun `In total, there are 79 beacons`() {
            assertThat(Day19(input).partOne()).isEqualTo(79)
        }

        @Test fun `Beacons seen from different orientations should match`() {
            val scanner = Day19.Scanner(0,
                buildSet {
                    add(Beacon(-1, -1, 1))
                    add(Beacon(-2, -2, 2))
                    add(Beacon(-3, -3, 3))
                    add(Beacon(-2, -3, 1))
                    add(Beacon(5, 6, -4))
                    add(Beacon(8, 0, 7))
                })

            val test = setOf(
                Beacon(1, -1, 1),
                Beacon(2, -2, 2),
                Beacon(3, -3, 3),
                Beacon(2, -1, 3),
                Beacon(-5, 4, -6),
                Beacon(-8, -7, 0)
            )
            val toList: List<Collection<Beacon>> = scanner.rotate().toList()
            assertThat(toList).contains(test)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `The largest Manhattan distance between any two scanners should equal 3621`() {
            assertThat(Day19(input).partTwo()).isEqualTo(3621)
        }
    }
}