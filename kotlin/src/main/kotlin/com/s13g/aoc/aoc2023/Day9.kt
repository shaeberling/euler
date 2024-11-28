package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 9: Mirage Maintenance ---
 * https://adventofcode.com/2023/day/9
 */
class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    val histories = lines.map { it.split(" ").map { x -> x.toLong() } }
    val partA = histories.sumOf { getNextNum(it) }
    val partB = histories.map { it.reversed() }.sumOf { getNextNum(it) }
    return resultFrom(partA, partB)
  }

  private fun getNextNum(hist: List<Long>): Long {
    val data = mutableListOf(hist)
    // Create all the new lines until all are 0.
    while (data.last().count { it != 0L } != 0) {
      val derivative = data.last().windowed(2, 1).map { it[1] - it[0] }.toList()
      data.add(derivative)
    }
    // Build back up by attaching the last digit upwards.
    return data.reversed().sumOf { it.last() }
  }
}