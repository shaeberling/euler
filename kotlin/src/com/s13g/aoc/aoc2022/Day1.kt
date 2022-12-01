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
    val elves = lines.joinToString("\n")
      .split("\n\n")
      .map { it -> it.split("\n").sumBy { it.toInt() } }
      .sorted().reversed()

    return resultFrom(elves[0], elves.subList(0, 3).sum())
  }
}