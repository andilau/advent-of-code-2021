# Advent of Code 2021

My solutions for [Advent of Code 2021](https://adventofcode.com/2021) in the [Kotlin](https://kotlinlang.org/)
programming language. Advent of Code is an Advent calendar of small programming puzzles by [Eric Wastl](http://was.tl/).

## Solutions

- Day 1: [Sonar Sweep](https://adventofcode.com/2021/day/1) 🧹 [Day1.kt](https://github.com/andilau/advent-of-code-2021/blob/main/src/main/kotlin/days/Day1.kt)
- Day 2: [Dive!](https://adventofcode.com/2021/day/2) ⬇ [Day2.kt](https://github.com/andilau/advent-of-code-2021/blob/main/src/main/kotlin/days/Day2.kt)
- Day 3: [Binary Diagnostic](https://adventofcode.com/2021/day/3) 🩺 [Day3.kt](https://github.com/andilau/advent-of-code-2021/blob/main/src/main/kotlin/days/Day3.kt)
- Day 4: [Giant Squid](https://adventofcode.com/2021/day/4) 🦑 [Day4.kt](https://github.com/andilau/advent-of-code-2021/blob/main/src/main/kotlin/days/Day4.kt)

## Features

* Gradle setup so you can run a specific day or all days on the command line (see **Running**)
* Timings for each part of each day
* Input for each day automatically exposed in String and List form
* [Junit 5](https://junit.org/junit5/) and [AssertJ](https://assertj.github.io/doc/) test libraries included (see **Testing**)
* Starter .gitignore

## Running

Project is already setup with gradle. To run the app:

* Navigate to top-level directory on the command line
* Run `./gradlew run` to run all days
* Run `./gradlew run --args $DAY` where `$DAY` is an integer to run a specific day
* Run `./gradlew run --args "$DAY1 $DAY2 $ANOTHERDAY"` to run a subset of days

## Testing

Project includes JUnit and AssertJ and a stub unit test to get you going. To run all tests:

* Navigate to top-level directory on the command line
* Run `./gradlew test`
* Add `--info`, `--debug` or `--stacktrace` flags for more output

By default, instantiations of `Day` classes in tests will use the input files in `src/test/resources`, _not_ those
in `src/main/resources`. This hopefully gives you flexibility - you could either just copy the real input
into `src/test/resources` if you want to test the actual answers, or you could add a file of test data based on the
examples given on the Advent of Code description for the day. The stub `Day1Test` class shows a test of the
functionality of `Day1` where the test input differs from the actual input.

## Architecture

* Inputs go into `src/main/resources` and follow the naming convention `input_day_X.txt`
* Solutions go into `src/main/kotlin/days` and implement the `Puzzle` interface
* Solutions follow the naming convention `DayX`
* It is assumed all solutions will have two parts
* It is assumed that the puzzle input is provided through the primary constructor
* You can use the `InputReader` methods for reading input
* To get started simply replace `src/main/resources/input_day_1.txt` with the real input and the solutions in `Day1` with your own