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
    val lastPositions = input
        .withIndex()
        .filter { it.index < input.size - 1 }
        .associate { Pair(it.value, it.index) }
        .toMutableMap()

    var lastNum = input.last()
    var chainSize = input.size - 1
    while (chainSize < limit - 1) {
      var age = 0
      if (lastNum in lastPositions) {
        age = chainSize - lastPositions[lastNum]!!
      }
      lastPositions[lastNum] = chainSize
      lastNum = age
      chainSize++
    }
    return lastNum
  }
}