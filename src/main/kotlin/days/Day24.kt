package days

@AdventOfCodePuzzle(
    name = "Arithmetic Logic Unit",
    url = "https://adventofcode.com/2021/day/24",
    date = Date(day = 24, year = 2021)
)
class Day24(listing: List<String>) : Puzzle {
    private val monad = listing.filter(String::isNotEmpty).map { Instruction.parse(it) }

    override fun partOne() =
        findSolution(9 downTo 1, State(0, 0, 0, 0, 0)) ?: error("No solution")

    override fun partTwo() =
        findSolution(1..9, State(0, 0, 0, 0, 0)) ?: error("No solution")

    internal fun computeState(input: List<Int>): State {
        val numbers = input.toMutableList()
        return monad.fold(State(0, 0, 0, 0, 0)) { state, ins -> state.solveWithInput(ins, numbers) }
    }

    private fun findSolution(
        order: IntProgression,
        state: State,
        answer: Long = 0,
        done: MutableSet<State> = mutableSetOf()
    ): Long? {
        var computedState = state
        var ip = computedState.ip
        while (ip in monad.indices) {
            when (val instruction = monad[ip++]) {
                is Input -> {
                    // Already computed?
                    if (!done.add(computedState.copy(ip = ip))) return null

                    var res: Long? = null
                    for (candidate in order) {
                        res = findSolution(
                            order,
                            computedState.set(instruction.first, candidate).copy(ip = ip),
                            answer * 10 + candidate,
                            done
                        )
                        if (res != null) break
                    }
                    return res
                }
                is Add, is Mul, is Div, is Mod, is Eql -> computedState = computedState.solve(instruction)
            }
        }
        return if (computedState.z == 0) answer else null
    }

    data class State(val w: Int, val x: Int, val y: Int, val z: Int, val ip: Int) {
        fun get(parameter: Char): Int = when (parameter) {
            'w' -> w
            'x' -> x
            'y' -> y
            'z' -> z
            else -> error("Unknown parameter")
        }

        fun get(parameterOrValue: String): Int =
            parameterOrValue.toIntOrNull() ?: get(parameterOrValue[0])

        fun set(parameter: Char, value: Int) = when (parameter) {
            'w' -> copy(w = value)
            'x' -> copy(x = value)
            'y' -> copy(y = value)
            'z' -> copy(z = value)
            else -> error("Unknown parameter")
        }

        fun solveWithInput(instruction: Instruction, input: MutableList<Int>): State =
            when (instruction) {
                is Input -> set(instruction.first, input.removeFirst())
                else -> solve(instruction)
            }

        fun solve(instruction: Instruction): State =
            when (instruction) {
                is Input -> error("Cant handle input")
                is Add -> set(instruction.first, get(instruction.first) + get(instruction.second))
                is Mul -> set(instruction.first, get(instruction.first) * get(instruction.second))
                is Div -> set(instruction.first, get(instruction.first) / get(instruction.second))
                is Mod -> set(instruction.first, get(instruction.first) % get(instruction.second))
                is Eql -> set(instruction.first, if (get(instruction.first) == get(instruction.second)) 1 else 0)
            }
    }

    sealed interface Instruction {
        companion object {
            fun parse(line: String): Instruction {
                val (cmd, args) = line.split(' ', limit = 2)
                val arg1 = args.split(' ')
                return when (cmd) {
                    "inp" -> Input(arg1[0][0])
                    "add" -> Add(arg1[0][0], arg1[1])
                    "mul" -> Mul(arg1[0][0], arg1[1])
                    "div" -> Div(arg1[0][0], arg1[1])
                    "mod" -> Mod(arg1[0][0], arg1[1])
                    "eql" -> Eql(arg1[0][0], arg1[1])
                    else -> error("Unknown command: $cmd")
                }
            }
        }
    }

    data class Input(val first: Char) : Instruction
    data class Add(val first: Char, val second: String) : Instruction
    data class Mul(val first: Char, val second: String) : Instruction
    data class Div(val first: Char, val second: String) : Instruction
    data class Mod(val first: Char, val second: String) : Instruction
    data class Eql(val first: Char, val second: String) : Instruction
}