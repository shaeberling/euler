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
import com.s13g.aoc.createProblem

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
  val runner = PuzzleRunner(ONLY_RUN_NEW, AOC19_ROOT,
      arrayOf(createProblem(2019, 1, "3394689", "5089160", Day1()),
          createProblem(2019, 2, "3850704", "6718", Day2()),
          createProblem(2019, 3, "303", "11222", Day3()),
          createProblem(2019, 4, "481", "299", Day4()),
          createProblem(2019, 5, "7157989", "7873292", Day5()),
          createProblem(2019, 6, "278744", "475", Day6()),
          createProblem(2019, 7, "398674", "39431233", Day7()),
          createProblem(2019, 8, "2480", result8B, Day8()),
          createProblem(2019, 9, "2662308295", "63441", Day9()),
          createProblem(2019, 10, "274", "305", Day10()),
          createProblem(2019, 11, "1709", result11B, Day11()),
          createProblem(2019, 12, "8960", "314917503970904", Day12()),
          createProblem(2019, 13, "298", "13956", Day13()),
          createProblem(2019, 14, "337862", "3687786", Day14()),
          createProblem(2019, 15, "210", "290", Day15()),
          /*createProblem(2019, 16, "73127523", "", Day16()),*/
          createProblem(2019, 17, "5680", "895965", Day17()),
//          createProblem(2019, 18, "", "", Day18()),
          createProblem(2019, 19, "169", "7001134", Day19()),
          /*createProblem(2019, 20, "", "", Day20()),*/
          createProblem(2019, 23, "24954", "17091", Day23()),
          createProblem(2019, 24, "28781019", "", Day24())))
  runner.run()

}