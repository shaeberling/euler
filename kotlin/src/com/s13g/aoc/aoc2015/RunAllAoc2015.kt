package com.s13g.aoc.aoc2015

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = true

/**
 * ---- Advent of Code 2015 ----
 * https://adventofcode.com/2015
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2015)
  runner.addProblem(1, Day1(), "138", "1771")
  runner.addProblem(4, Day4(), "117946", "3938038")
  runner.run()
}