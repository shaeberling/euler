package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul

/**
 * --- Day 1: Report Repair ---
 * https://adventofcode.com/2020/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val (resultA, resultB) = run(lines.map { it.toLong() }.toHashSet())
    return Result("$resultA", "$resultB")
  }

  private fun run(input: Set<Long>): Array<Long> {
    val result = arrayOf(0L, 0L)
    for (i in input) {
      if (input.contains(2020 - i)) result[0] = i * (2020 - i)
      for (j in input) {
        if (input.contains(2020 - (i + j))) result[1] = i * j * (2020 - (i + j))
      }
    }
    return result
  }

  private fun test(items: List<Long>) = if (items.sum() == 2020L) items.mul() else 0
}