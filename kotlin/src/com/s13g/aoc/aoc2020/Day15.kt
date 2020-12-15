package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 15: Rambunctious Recitation ---
 * https://adventofcode.com/2020/day/15
 */
class Day15 : Solver {

  override fun solve(lines: List<String>): Result {
    val input = lines[0].split(',').map { it.toInt() }
    return Result("${solveUntil(2020, input)}", "${solveUntil(30000000, input)}")
  }

  private fun solveUntil(limit: Int, input: List<Int>): Int {
    val positions = input
        .withIndex()
        .associate { Pair(it.value, mutableListOf(it.index)) }
        .toMutableMap()

    var lastNum = input.last()
    var chainSize = input.size
    while (chainSize < limit) {
      var age = 0
      if (lastNum in positions && positions[lastNum]!!.size > 1) {
        val l = positions[lastNum]!!.size - 1
        age = positions[lastNum]!![l] - positions[lastNum]!![l - 1]
      }
      // If diff was found, age is > 0, else 0. Add it to the list of positions.
      if (age !in positions) positions[age] = mutableListOf()
      positions[age]!!.add(chainSize)
      lastNum = age
      chainSize++
    }
    // Return the last number found.
    return lastNum
  }
}