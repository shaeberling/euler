/*
 * Copyright 2017 Sascha Haeberling
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

package com.s13g.aoc.aoc2015

import com.s13g.aoc.Solver
import com.s13g.aoc.Result

/** http://adventofcode.com/2015/day/1 */
class P1Floors : Solver {
  override fun solve(data: List<String>): Result {
    val resultA = data[0].sumBy { c -> if (c == '(') 1 else -1 }
    var resultB = 0
    var floor = 0
    for ((i, c) in data[0].withIndex()) {
      floor += if (c == '(') 1 else -1
      if (resultB == 0 && floor == -1) resultB = i + 1
    }
    return Result("$resultA", "$resultB")
  }
}