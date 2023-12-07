package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul
import com.s13g.aoc.resultFrom

/**
 * --- Day 6: Wait For It ---
 * https://adventofcode.com/2023/day/6
 */
class Day6 : Solver {
  override fun solve(lines: List<String>): Result {
    val inputA = lines[0].parseInput().zip(lines[1].parseInput())
    val inputB = listOf(
      Pair(
        lines[0].parseInput().joinToString("").toLong(),
        lines[1].parseInput().joinToString("").toLong()
      )
    )
    return resultFrom(solve(inputA), solve(inputB))
  }

  private fun solve(tsAndDs: List<Pair<Long, Long>>): Int {
    val wins = mutableListOf<Int>()
    for (tAndD in tsAndDs) {
      var numWins = 0
      for (n in 1..tAndD.first) {
        val duration = n + (tAndD.second / n)
        if (duration < tAndD.first) numWins++
      }
      wins.add(numWins)
    }
    return wins.mul()
  }

  private fun String.parseInput() =
    this.split(":")[1].split("\\s+".toRegex())
      .filter { it.isNotBlank() }.map { it.toLong() }
}