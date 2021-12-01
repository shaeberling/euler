package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 1: Sonar Sweep ---
 * https://adventofcode.com/2021/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val m1 = lines.map { it.toLong() }.toList()
    val m2 = m1.mapIndexedNotNull { i, _ -> if (i < m1.size - 2) m1[i] + m1[i + 1] + m1[i + 2] else null }
    return resultFrom(m1.measure(), m2.measure())
  }

  private fun List<Long>.measure() = this.mapIndexed { i, v -> if (i == 0) false else v > this[i - 1] }.count { it }
}