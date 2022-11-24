package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 1: *** ---
 * https://adventofcode.com/2022/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val m1 = lines.map { it.toLong() }
    val m2 = m1.windowed(3) { it.sum() }
    return resultFrom(m1.measure(), m2.measure())
  }

  private fun List<Long>.measure() = this.windowed(2) { it[0] < it[1] }.count { it }
}