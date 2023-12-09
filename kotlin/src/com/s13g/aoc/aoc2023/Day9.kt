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
    val partA = histories.sumOf { getNextNum(it).first }
    val partB = histories.sumOf { getNextNum(it).second }
    return resultFrom(partA, partB)
  }

  private fun getNextNum(hist: List<Long>): Pair<Long, Long> {
    val data = mutableListOf(hist)

    // Create all the new lines until all are 0.
    while (data.last().count { it != 0L } != 0) {
      val derivative = data.last().windowed(2, 1).map { it[1] - it[0] }.toList()
      data.add(derivative)
    }

    // Build back up by attaching the last digit upwards.
    val lastNum = data.reversed().sumOf { it.last() }
    // For part b, do the same thing for the beginning.
    var firstNum = 0L
    for (list in data.reversed()) {
      firstNum = list.first() - firstNum
    }
    return Pair(lastNum, firstNum)
  }
}