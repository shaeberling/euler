package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 1: Sonar Sweep ---
 * https://adventofcode.com/2021/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val measurements = lines.map { it.toLong() }.toList()
    val measurements2 = mutableListOf<Long>()
    for (i in 0 until measurements.size - 2) {
      measurements2.add(measurements[i] + measurements[i + 1] + measurements[i + 2])
    }
    return Result(measure(measurements).toString(), measure(measurements2).toString())
  }

  private fun measure(list: List<Long>): Int {
    var count = 0
    for (i in 1 until list.size) {
      if (list[i] > list[i - 1]) count++
    }
    return count
  }
}