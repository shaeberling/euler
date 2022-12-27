package com.s13g.aoc.aoc2022

import com.s13g.aoc.PuzzleRunner

private const val ONLY_RUN_NEW = true

const val DAY_10_PART_2 = """
####.#..#.###..####.#....###....##.###..
#....#..#.#..#....#.#....#..#....#.#..#.
###..####.###....#..#....#..#....#.#..#.
#....#..#.#..#..#...#....###.....#.###..
##...#..#.#..#.#....#....#.#..#..#.#.#..
####.#..#.###..####.####.#..#..##..#..#."""


/**
 * ---- Advent of Code 2022 ----
 * https://adventofcode.com/2022
 */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2022)
  runner.addProblem(1, Day1(), "71124", "204639")
  runner.addProblem(2, Day2(), "9177", "12111")
  runner.addProblem(3, Day3(), "7903", "2548")
  runner.addProblem(4, Day4(), "475", "825")
  runner.addProblem(5, Day5(), "SHQWSRBDL", "CDTQZHBRS")
  runner.addProblem(6, Day6(), "1892", "2313")
  runner.addProblem(7, Day7(), "1743217", "8319096")
  runner.addProblem(8, Day8(), "1814", "330786")
  runner.addProblem(9, Day9(), "6367", "2536")
  runner.addProblem(10, Day10(), "12640", DAY_10_PART_2)
  runner.addProblem(11, Day11(), "55216", "12848882750")
  runner.addProblem(12, Day12(), "437", "430")
  runner.addProblem(13, Day13(), "6478", "21922")
  runner.addProblem(14, Day14(), "745", "27551")
  runner.addProblem(15, Day15(), "4876693", "11645454855041")
  runner.addProblem(16, Day16(), "1559", "2191")
  runner.addProblem(17, Day17(), "3119", "1536994219669")
  runner.addProblem(18, Day18(), "4604", "2604")
  runner.addProblem(19, Day19(), "1427", "4400")
  runner.addProblem(20, Day20(), "10831", "6420481789383")
  runner.addProblem(21, Day21(), "84244467642604", "3759569926192")
  runner.addProblem(22, Day22(), "93226", "37415")
  runner.addProblem(23, Day23(), "4236", "1023")
  runner.addProblem(24, Day24(), "260", "747")
  runner.addProblem(25, Day25(), "2-21=02=1-121-2-11-0", "Happy Holidays")
  runner.run()
}
