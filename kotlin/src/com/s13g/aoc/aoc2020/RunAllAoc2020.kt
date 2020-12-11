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
  runner.addProblem(3, Day3(), "282", "958815792")
  runner.addProblem(4, Day4(), "233", "111")
  runner.addProblem(5, Day5(), "816", "539")
  runner.addProblem(6, Day6(), "6532", "3427")
  runner.addProblem(7, Day7(), "378", "27526")
  runner.addProblem(8, Day8(), "1137", "1125")
  runner.addProblem(9, Day9(), "26134589", "3535124")
  runner.addProblem(10, Day10(), "2346", "6044831973376")
  runner.addProblem(11, Day11(), "2277", "2066", true)
//  runner.addProblem(12, Day12(), "", "", true)
//  runner.addProblem(13, Day13(), "", "", true)
//  runner.addProblem(14, Day14(), "", "", true)
//  runner.addProblem(15, Day15(), "", "", true)
//  runner.addProblem(16, Day16(), "", "", true)
//  runner.addProblem(17, Day17(), "", "", true)
//  runner.addProblem(18, Day18(), "", "", true)
//  runner.addProblem(19, Day19(), "", "", true)
//  runner.addProblem(20, Day20(), "", "", true)
//  runner.addProblem(21, Day21(), "", "", true)
//  runner.addProblem(22, Day22(), "", "", true)
//  runner.addProblem(23, Day23(), "", "", true)
//  runner.addProblem(24, Day24(), "", "", true)
//  runner.addProblem(25, Day25(), "", "", true)
  runner.run()
}
