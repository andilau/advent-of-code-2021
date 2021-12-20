package days

class Day20(input: List<String>) : Puzzle {
    val input2 = input.let { lines ->
        Input(
            lines.first(),
            lines.subList(2, lines.size).map { it.toCharArray() }
        )
    }

    override fun partOne(): Any {
        return part1(input2, 2)
    }

    override fun partTwo(): Any {
        return part1(input2, 50)
    }

    fun part1(input: Input, steps: Int): Int {
        val defaultChars = defaultChars(steps, input)
        var image = input.inputImage
        repeat(steps) { step ->
            image = enhance(image, input, defaultChars[step])
        }
        return image.sumOf { line -> line.count { it == '#' } }
    }

    class Input(
        val enhancement: String,
        val inputImage: List<CharArray>,
    )

    fun defaultChars(steps: Int, input: Input): List<Char> {
        var index = 0
        return (0 until steps + 1).map {
            val nextChar = input.enhancement[index]
            index = if (nextChar == '.') 0 else 511
            nextChar
        }.drop(1)
    }

    fun neighbors(r: Int, c: Int, image: List<CharArray>, defaultChar: Char): String {
        return (r - 1..r + 1).flatMap { row ->
            (c - 1..c + 1).map { col ->
                val ch = image.getOrNull(row)?.getOrNull(col) ?: defaultChar
                if (ch == '#') '1' else '0'
            }
        }.joinToString("")
    }

    fun enhance(image: List<CharArray>, input: Input, defaultChar: Char): List<CharArray> {
        var newImage = List(image.size + 2) { CharArray(image[0].size + 2) }
        for (row in newImage.indices) {
            for (col in newImage[0].indices) {
                val neighbors = neighbors(row - 1, col - 1, image, defaultChar)
                val index = neighbors.toInt(2)
                val newChar = input.enhancement[index]
                newImage[row][col] = newChar
            }
        }
        return newImage
    }



}