package days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("Day 16")
class Day16Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @TestFactory
        fun `Transmission in hex should equal binary form`(): List<DynamicTest> =
            listOf(
                "D2FE28" to "110100101111111000101000",
                "38006F45291200" to "00111000000000000110111101000101001010010001001000000000",
                "EE00D40C823060" to "11101110000000001101010000001100100000100011000001100000"
            ).map { (hex, binary) ->
                DynamicTest.dynamicTest("$hex => $binary") {
                    assertThat(Day16.hexToBin(hex)).isEqualTo(binary)
                }
            }

        @TestFactory
        fun `Versions Of Sub-packets Should Sum To `(): List<DynamicTest> =
            listOf(
                "D2FE28" to 6,
                "38006F45291200" to 9,
                "EE00D40C823060" to 14
            ).map { (input, expected) ->
                DynamicTest.dynamicTest("$input => $expected") {
                    assertThat(Day16(input).partOne()).isEqualTo(expected)
                }
            }

        @TestFactory
        fun `Versions Of Sub-packets Should Sum To`(): List<DynamicTest> = listOf(
            "8A004A801A8002F478" to 16,
            "620080001611562C8802118E34" to 12,
            "C0015000016115A2E0802F182340" to 23,
            "A0016C880162017C3686B18A3D4780" to 31
        )
            .map { (input, expected) ->
                DynamicTest.dynamicTest("$input => $expected") {
                    assertThat(Day16(input).partOne()).isEqualTo(expected)
                }

            }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `C200B40A82 finds the sum of 1 and 2, resulting in the value 3`() {
            assertThat(Day16("C200B40A82").partTwo()).isEqualTo(3)
        }

        @Test
        fun `04005AC33890 finds the product of 6 and 9, resulting in the value 54`() {
            assertThat(Day16("04005AC33890").partTwo()).isEqualTo(6 * 9)
        }

        @Test
        fun `880086C3E88112 finds the minimum of 7, 8, and 9, resulting in the value 7`() {
            assertThat(Day16("880086C3E88112").partTwo()).isEqualTo(7)
        }

        @Test
        fun `CE00C43D881120 finds the maximum of 7, 8, and 9, resulting in the value 9`() {
            assertThat(Day16("CE00C43D881120").partTwo()).isEqualTo(9)
        }

        @Test
        fun `D8005AC2A8F0 produces 1, because 5 is less than 15`() {
            assertThat(Day16("D8005AC2A8F0").partTwo()).isEqualTo(1)
        }

        @Test
        fun `F600BC2D8F produces 0, because 5 is not greater than 15`() {
            assertThat(Day16("F600BC2D8F").partTwo()).isEqualTo(0)
        }

        @Test
        fun `9C005AC2F8F0 produces 0, because 5 is not equal to 15`() {
            assertThat(Day16("9C005AC2F8F0").partTwo()).isEqualTo(0)
        }

        @Test
        fun `9C0141080250320F1802104A08 produces 1, because 1 + 3 = 2 x 2`() {
            assertThat(Day16("9C0141080250320F1802104A08").partTwo()).isEqualTo(1)
        }
    }
}