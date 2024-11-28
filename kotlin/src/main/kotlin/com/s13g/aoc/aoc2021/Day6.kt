package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 6: Lanternfish ---
 * https://adventofcode.com/2021/day/6
 */
class Day6 : Solver {
  private val dCache = mutableMapOf<Pair<Int, Int>, Long>()
  override fun solve(lines: List<String>): Result {
    val f = lines[0].split(",").map { it.toInt() }
    return Result("${f.map { growFishes(it, 0, 80) }.sum()}", "${f.map { growFishes(it, 0, 256) }.sum()}")
  }

  private fun growFishes(num: Int, iterations: Int, maxIter: Int): Long {
    val key = Pair(num, maxIter - iterations)
    if (iterations >= maxIter) return 1L
    if (dCache.containsKey(key)) return dCache[key]!!

    var newAmount = 0L
    if (num > 0) newAmount += growFishes(num - 1, iterations + 1, maxIter)
    if (num == 0) {
      newAmount += growFishes(6, iterations + 1, maxIter)
      newAmount += growFishes(8, iterations + 1, maxIter)
    }
    dCache[key] = newAmount
    return newAmount
  }
}