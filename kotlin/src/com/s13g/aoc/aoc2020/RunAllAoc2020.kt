package com.s13g.aoc.aoc2020

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = false

/**
 * ---- Advent of Code 2020 ----
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
  runner.addProblem(11, Day11(), "2277", "2066")
  runner.addProblem(12, Day12(), "1533", "25235")
  runner.addProblem(13, Day13(), "4808", "741745043105674")
  runner.addProblem(14, Day14(), "8332632930672", "4753238784664")
  runner.addProblem(15, Day15(), "249", "41687")
  runner.addProblem(16, Day16(), "23122", "362974212989")
  runner.addProblem(17, Day17(), "338", "2440")
  runner.addProblem(18, Day18(), "131076645626", "109418509151782")
  runner.addProblem(19, Day19(), "208", "316")
  runner.addProblem(20, Day20(), "19955159604613", "1639")
  runner.addProblem(21, Day21(), "1685", "ntft,nhx,kfxr,xmhsbd,rrjb,xzhxj,chbtp,cqvc")
  runner.addProblem(22, Day22(), "33403", "29177")
  runner.addProblem(23, Day23(), "38925764", "131152940564")
  runner.addProblem(24, Day24(), "263", "3649")
  runner.addProblem(25, Day25(), "11576351", "")
  runner.run()
}
