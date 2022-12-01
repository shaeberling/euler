package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 1: Calorie Counting ---
 * https://adventofcode.com/2022/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val cals = mutableListOf<Int>()
    var curr = 0
    for (line in lines) {
      if (line.isBlank()) {
        cals.add(curr)
        curr = 0
      } else {
        curr += line.toInt()
      }
    }
    val desc = cals.sorted().reversed()

    val m1 = desc[0]
    val m2 = desc.subList(0, 3).sum()

    return resultFrom(m1, m2)
  }
}