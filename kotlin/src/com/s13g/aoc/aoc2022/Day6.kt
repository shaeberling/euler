package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 6: Tuning Trouble ---
 * https://adventofcode.com/2022/day/6
 */
class Day6 : Solver {
  override fun solve(lines: List<String>): Result {
    return resultFrom(solveFor(lines[0], 4), solveFor(lines[0], 14))
  }

  fun solveFor(str: String, len: Int) = str.windowed(len).withIndex()
    .first { it.value.toSet().size == len }.index + len
}