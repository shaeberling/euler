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

class PuzzleRunner(private val fileRoot: String,
                   private val problems: Array<Problem>) {

    fun run() {
        for (p in problems) {
            println("Running '${p.title}' ...")
            val start = Instant.now()
            val solution = p.puzzle.solve(readAsString(Paths.get(fileRoot, p.inputFile)))
            val duration = Duration.between(start, Instant.now())

            println("[${p.title}] - ${duration.toMillis()} ms")
            println(" --> Solution A:  ${compareResult(p.solutionA, solution.a)}")
            println(" --> Solution B:  ${compareResult(p.solutionB, solution.b)}\n")
        }
    }

    fun compareResult(expected: String, actual: String): String {
        if (actual == expected) {
            return "OK '$actual'"
        } else {
            return "FAIL - Was '$actual' but expected '$expected'"
        }
    }
}

data class Problem(val title: String,
                   val inputFile: String,
                   val solutionA: String,
                   val solutionB: String,
                   val puzzle: Solver)