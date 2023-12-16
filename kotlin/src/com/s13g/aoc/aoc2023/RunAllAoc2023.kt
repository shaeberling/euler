package com.s13g.aoc.aoc2023

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = false

/**
 * ---- Advent of Code 2023 ----
 * https://adventofcode.com/2023
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2023)
  runner.addProblem(1, Day1(), "55834", "53221")
  runner.addProblem(2, Day2(), "2683", "49710")
  runner.addProblem(3, Day3(), "521515", "69527306")
  runner.addProblem(4, Day4(), "23678", "15455663")
  runner.addProblem(5, Day5(), "165788812", "1928058")
  runner.addProblem(6, Day6(), "1624896", "32583852")
  runner.addProblem(7, Day7(), "250453939", "248652697")
  runner.addProblem(8, Day8(), "20093", "22103062509257")
  runner.addProblem(9, Day9(), "2075724761", "1072")
  runner.addProblem(10, Day10(), "7012", "395")
  runner.addProblem(11, Day11(), "10077850", "504715068438")
  runner.addProblem(12, Day12(), "7633", "23903579139437")
  runner.addProblem(13, Day13(), "31265", "39359")
  runner.addProblem(14, Day14(), "113456", "118747")
  runner.addProblem(15, Day15(), "506269", "264021")
//  runner.addProblem(16, Day16(), "", "", true)
//  runner.addProblem(17, Day17(), 113456"", "", true)
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
