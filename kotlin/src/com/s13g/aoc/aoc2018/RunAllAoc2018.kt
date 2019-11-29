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

package com.s13g.aoc.aoc2018

import com.s13g.aoc.Problem
import com.s13g.aoc.PuzzleRunner

private const val AOC18_ROOT = "../data/aoc/2018/"

/** Runs all Kotlin AOC 2018 solvers. */
fun main(args: Array<String>) {
  val runner = PuzzleRunner(AOC18_ROOT,
      arrayOf(Problem("AOC 2018.01", "day1.txt", "416", "56752", Day1()),
          Problem("AOC 2018.02", "day2.txt", "8715", "fvstwblgqkhpuixdrnevmaycd", Day2()),
          Problem("AOC 2018.24", "day24.txt", "19295", "12084", Day24())))
  runner.run()
}