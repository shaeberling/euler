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
  runner.addProblem(5, Day5(), "238", "69")
  runner.addProblem(6, Day6(), "400410", "15343601")
  runner.addProblem(7, Day7(), "3176", "14710")
//  runner.addProblem(8, Day8(), "", "", true)
//  runner.addProblem(9, Day9(), "", "", true)
//  runner.addProblem(10, Day10(), "", "", true)
//  runner.addProblem(11, Day11(), "", "", true)
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