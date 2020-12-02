package com.s13g.aoc.aoc2020

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = true

/**
 * --- Advent of Code 2020 ---
 * https://adventofcode.com/2020
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2020)
  runner.addProblem(1, Day1(), "787776", "262738554")
  runner.addProblem(2, Day2(), "396", "428")
  runner.run()
}