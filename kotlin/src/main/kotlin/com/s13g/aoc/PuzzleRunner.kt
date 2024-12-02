/*
 * Copyright 2019 Sascha Häberling
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

package com.s13g.aoc

import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import kotlin.io.path.fileSize
import kotlin.io.path.notExists
import kotlin.io.path.pathString

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"

class PuzzleRunner(private val onlyRunNew: Boolean,
                   private val year: Int) {
  private val fileRoot = "src/main/resources/$year/"
  private var problems = mutableListOf<Problem>()

  fun run() {
    problems
        .filter { p -> !onlyRunNew || (p.solutionA == "" || p.solutionB == "" || p.forceRun) }
        .forEach { p ->
          println("Running $ANSI_YELLOW'${p.title}'$ANSI_RESET ...")
          val inputFile = Paths.get(fileRoot, p.inputFile).toAbsolutePath()
          if (inputFile.notExists() || inputFile.fileSize() == 0L) {
            println("${ANSI_YELLOW}Input file (${inputFile.pathString}) does not exist. Downloading ..$ANSI_RESET")
            fetchAdventOfCodeInput(year, p.day, inputFile)
            println("${ANSI_GREEN}Successfully fetched input.$ANSI_RESET")
          }

          val input = readAsString(Paths.get(fileRoot, p.inputFile))
          val start = Instant.now()
          val solution = p.puzzle.solve(input)
          val duration = Duration.between(start, Instant.now())

          println("[${p.title}] - ${duration.toMillis()} ms")
          println(" --> Solution A:  ${compareResult(p.solutionA, solution.a)}")
          println(" --> Solution B:  ${compareResult(p.solutionB, solution.b)}\n")
        }
  }

  fun addProblem(day: Int, solver: Solver, solutionA: String,
                 solutionB: String, forceRun: Boolean = false) {
    problems.add(Problem("$year.${day.toString().padStart(2, '0')}", day,
        "day$day.txt", solutionA, solutionB, solver, forceRun))

  }

  private fun compareResult(expected: String, actual: String) =
      if (actual == expected) {
        "${ANSI_GREEN}OK '$actual'$ANSI_RESET"
      } else if (expected.isBlank()) {
        "${ANSI_PURPLE}IN PROGRESS: '$actual'$ANSI_RESET"
      } else {
        "${ANSI_RED}FAIL - Was '$actual' but expected '$expected'$ANSI_RESET"
      }
}

data class Problem(val title: String,
                   val day: Int,
                   val inputFile: String,
                   val solutionA: String,
                   val solutionB: String,
                   val puzzle: Solver,
                   val forceRun: Boolean)