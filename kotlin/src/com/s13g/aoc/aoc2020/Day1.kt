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
    val (resultA, resultB) = run(lines.map { it.toLong() })
    return Result("$resultA", "$resultB")
  }

  private fun run(input: List<Long>): Array<Long> {
    val result = arrayOf(0L, 0L)
    for (i in input.indices) {
      for (j in i + 1 until input.size) {
        if (result[0] == 0L) result[0] = test(listOf(input[i], input[j]))
        for (k in j + 1 until input.size) {
          if (result[1] == 0L) result[1] = test(listOf(input[i], input[j], input[k]))
        }
      }
    }
    return result
  }

  private fun test(items: List<Long>) = if (items.sum() == 2020L) items.mul() else 0
}