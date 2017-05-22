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

import com.s13g.aoc.readAsString


/** Runs all Kotlin AOC 2015 solvers. */
fun main(args: Array<String>) {
    val AOC15_ROOT = "../data/aoc/2015/"
    val content = readAsString(AOC15_ROOT + "day1.txt")
    P1Floors(content).solve()
}