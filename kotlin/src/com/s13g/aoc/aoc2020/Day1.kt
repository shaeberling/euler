package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 1: Report Repair ---
 * https://adventofcode.com/2020/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val (resultA, resultB) = run(lines.map { it.toLong() }.toHashSet())
    return Result("$resultA", "$resultB")
  }

  private fun run(input: Set<Long>, year: Int = 2020): Array<Long> {
    val result = arrayOf(0L, 0L)
    for (i in input) {
      if (input.contains(year - i)) result[0] = i * (year - i)
      for (j in input) {
        if (input.contains(year - (i + j))) result[1] = i * j * (year - (i + j))
      }
    }
    return result
  }
}