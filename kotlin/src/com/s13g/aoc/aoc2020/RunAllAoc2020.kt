package com.s13g.aoc.aoc2020

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = true

fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2020)
  runner.addProblem(1, Day1(), "", "")
  runner.run()
}