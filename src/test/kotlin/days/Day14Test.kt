package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 14")
class Day14Test {
    private val example = """
        NNCB
        
        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C""".trimIndent().lines()

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        private val resultWithStep = listOf(
            "NNCB",
            "NCNBCHB",
            "NBCCNBBBCBHCB",
            "NBBBCNCCNBBNBNBBCHBHHBCHB",
            "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB",
        )

        @TestFactory
        @DisplayName("Molecule distribution with cycle should match")
        fun `Molecule distribution with cycle should match`(): List<DynamicTest> =
            resultWithStep
                .map { it.moleculeDistribution() }
                .mapIndexed { cycle, distribution ->
                    DynamicTest.dynamicTest("Molecule distribution with cycle $cycle should match $distribution") {
                        assertThat(Day14(example).countByMolecule(times = cycle)).isEqualTo(distribution)
                    }
                }

        @TestFactory
        @DisplayName("Molecule count with cycle should match")
        fun `Molecule count with cycle should match`(): List<DynamicTest> =
            resultWithStep
                .map { it.moleculeCount() }
                .mapIndexed { cycle, count ->
                    DynamicTest.dynamicTest("Molecule count with cycle $cycle should match $count") {
                        assertThat(Day14(example).countMolecules(times = cycle)).isEqualTo(count)
                    }
                }

        private fun String.moleculeDistribution() = groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        private fun String.moleculeCount() = groupingBy { it }.eachCount().values.sum().toLong()

        @Test
        fun `Molecule count After 5 Insertion Cycles Should Equal 97`() {
            assertThat(Day14(example).countMolecules(5)).isEqualTo(97)
        }

        @Test
        fun `Molecule count After 10 Insertion Cycles Should Equal 3073`() {
            assertThat(Day14(example).countMolecules(10)).isEqualTo(3073)
        }

        @Test
        fun `Molecule distribution After 10 Insertion Cycles Should Match`() {
            val expected = listOf('B' to 1749L, 'C' to 298L, 'H' to 161L, 'N' to 865L).toMap()
            val actual = Day14(example).countByMolecule(10)
            assertThat(actual).isEqualTo(expected)
        }

        @Test
        internal fun `Most minus least common molecule occurrence after 10 cycles should equal 1588`() {
            assertThat(Day14(example).partOne()).isEqualTo(1588)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        internal fun `Most minus least common molecule occurrence after 40 cycles should equal 2_188_189_693_529`() {
            assertThat(Day14(example).partTwo()).isEqualTo(2_188_189_693_529)
        }
    }
}