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

private const val ONLY_RUN_NEW = true

/** Runs all Kotlin AOC 2018 solvers. */
fun main() {
  val runner = PuzzleRunner(ONLY_RUN_NEW, 2018)
  runner.addProblem(1, Day1(), "416", "56752")
  runner.addProblem(2, Day2(), "8715", "fvstwblgqkhpuixdrnevmaycd")
  runner.addProblem(19, Day19(), "960", "10750428")
  runner.addProblem(21, Day21(), "3941014", "13775890")
  runner.addProblem(22, Day22(), "11810", "")
  runner.addProblem(24, Day24(), "19295", "12084")
  runner.run()
}