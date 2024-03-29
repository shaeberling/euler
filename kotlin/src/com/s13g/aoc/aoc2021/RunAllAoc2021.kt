package com.s13g.aoc.aoc2021

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = false

private const val DAY13_PART2 = """
####..##..#..#..##..#..#.###..####..##.
#....#..#.#.#..#..#.#.#..#..#....#.#..#
###..#....##...#....##...###....#..#...
#....#.##.#.#..#....#.#..#..#..#...#.##
#....#..#.#.#..#..#.#.#..#..#.#....#..#
#.....###.#..#..##..#..#.###..####..###
"""

/**
 * ---- Advent of Code 2021 ----
 * https://adventofcode.com/2021
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2021)
  runner.addProblem(1, Day1(), "1462", "1497")
  runner.addProblem(2, Day2(), "2215080", "1864715580")
  runner.addProblem(3, Day3(), "3549854", "3765399")
  runner.addProblem(4, Day4(), "33462", "30070")
  runner.addProblem(5, Day5(), "5169", "22083")
  runner.addProblem(6, Day6(), "383160", "1721148811504")
  runner.addProblem(7, Day7(), "329389", "86397080")
  runner.addProblem(8, Day8(), "237", "1009098")
  runner.addProblem(9, Day9(), "631", "821560")
  runner.addProblem(10, Day10(), "321237", "2360030859")
  runner.addProblem(11, Day11(), "1700", "273")
  runner.addProblem(12, Day12(), "4970", "137948")
  runner.addProblem(13, Day13(), "687", DAY13_PART2)
  runner.addProblem(14, Day14(), "3284", "4302675529689")
  runner.addProblem(15, Day15(), "755", "3016")
  runner.addProblem(16, Day16(), "1014", "1922490999789")
  runner.addProblem(17, Day17(), "5995", "3202")
  runner.addProblem(18, Day18(), "4033", "4864")
  runner.addProblem(19, Day19(), "338", "9862")
  runner.addProblem(20, Day20(), "5359", "12333")
  runner.addProblem(21, Day21(), "518418", "116741133558209")
  runner.addProblem(22, Day22(), "647062", "1319618626668022")
  runner.addProblem(23, Day23(), "15237", "47509")
  runner.addProblem(24, Day24(), "29599469991739", "17153114691118")
  runner.addProblem(25, Day25(), "534", "Happy Holidays")
  runner.run()

}
