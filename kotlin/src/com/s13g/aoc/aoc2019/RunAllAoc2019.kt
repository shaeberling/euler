/*
 * Copyright 2019 Sascha Haeberling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package com.s13g.aoc.aoc2019

import com.s13g.aoc.PuzzleRunner

private const val AOC19_ROOT = "../data/aoc/2019/"
private const val ONLY_RUN_NEW = false

const val result8B = """
####.#...####..#....#..#.
...#.#...##..#.#....#..#.
..#...#.#.###..#....####.
.#.....#..#..#.#....#..#.
#......#..#..#.#....#..#.
####...#..###..####.#..#."""

const val result11B = """
.###...##..#..#.####.#..#..##....##.#..#...
.#..#.#..#.#..#.#....#..#.#..#....#.#..#...
.#..#.#....#..#.###..####.#.......#.####...
.###..#.##.#..#.#....#..#.#.......#.#..#...
.#....#..#.#..#.#....#..#.#..#.#..#.#..#...
.#.....###..##..####.#..#..##...##..#..#...
"""

/** Runs all Kotlin AOC 2019 solvers. */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2019)
  runner.addProblem(1, Day1(), "3394689", "5089160")
  runner.addProblem(2, Day2(), "3850704", "6718")
  runner.addProblem(3, Day3(), "303", "11222")
  runner.addProblem(4, Day4(), "481", "299")
  runner.addProblem(5, Day5(), "7157989", "7873292")
  runner.addProblem(6, Day6(), "278744", "475")
  runner.addProblem(7, Day7(), "398674", "39431233")
  runner.addProblem(8, Day8(), "2480", result8B)
  runner.addProblem(9, Day9(), "2662308295", "63441")
  runner.addProblem(10, Day10(), "274", "305")
  runner.addProblem(11, Day11(), "1709", result11B)
  runner.addProblem(12, Day12(), "8960", "314917503970904")
  runner.addProblem(13, Day13(), "298", "13956")
  runner.addProblem(14, Day14(), "337862", "3687786")
  runner.addProblem(15, Day15(), "210", "290")
  runner.addProblem(16, Day16(), "73127523", "")
  runner.addProblem(17, Day17(), "5680", "895965")
//          runner.addProblem(18, Day18(), "", "")
  runner.addProblem(19, Day19(), "169", "7001134")
  /*runner.addProblem(20, Day20(), "", "")*/
  runner.addProblem(23, Day23(), "24954", "17091")
  runner.addProblem(24, Day24(), "28781019", "")
  runner.run()
}