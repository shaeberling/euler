package com.s13g.aoc.aoc2020

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = false

/**
 * --- Advent of Code 2020 ---
 * https://adventofcode.com/2020
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2020)
  runner.addProblem(1, Day1(), "787776", "262738554")
  runner.addProblem(2, Day2(), "396", "428")
  runner.addProblem(3, Day3(), "282", "958815792")
  runner.addProblem(4, Day4(), "233", "111")
  runner.addProblem(5, Day5(), "816", "539")
  runner.run()
}
