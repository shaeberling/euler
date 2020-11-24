package com.s13g.aoc.aoc2020

import com.s13g.aoc.PuzzleRunner
import com.s13g.aoc.createProblem

private const val AOC20_ROOT = "../data/aoc/2020/"
private const val ONLY_RUN_NEW = true

fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, AOC20_ROOT,
      arrayOf(createProblem(2020, 1, "", "", Day1())))
  runner.run()
}