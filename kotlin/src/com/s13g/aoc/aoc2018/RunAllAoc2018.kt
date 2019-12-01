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

import com.s13g.aoc.PuzzleRunner
import com.s13g.aoc.createProblem

private const val AOC18_ROOT = "../data/aoc/2018/"
private const val ONLY_RUN_NEW = false

/** Runs all Kotlin AOC 2018 solvers. */
fun main(args: Array<String>) {
  val runner = PuzzleRunner(ONLY_RUN_NEW, AOC18_ROOT,
      arrayOf(createProblem(2018, 1, "416", "56752", Day1()),
          createProblem(2018, 2, "8715", "fvstwblgqkhpuixdrnevmaycd", Day2()),
          createProblem(2018, 19, "960", "10750428", Day19()),
          createProblem(2018, 21, "8715", "fvstwblgqkhpuixdrnevmaycd", Day21()),
          createProblem(2018, 24, "19295", "12084", Day24())))
  runner.run()
}