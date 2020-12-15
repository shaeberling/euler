package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.util.*

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
        .associate { Pair(it.value, TreeSet<Int>(listOf(it.index))) }
        .toMutableMap()

    var lastNum = input.last()
    var chainSize = input.size
    while (chainSize < limit) {
      var age = 0
      if (lastNum in positions && positions[lastNum]!!.size > 1) {
        val posSorted = positions[lastNum]!!
        val iter = posSorted.descendingIterator()
        age = iter.next() - iter.next()
      }
      // If diff was found, age is > 0, else 0. Add it to the list of positions.
      if (age !in positions) positions[age] = TreeSet()
      positions[age]!!.add(chainSize)
      lastNum = age
      chainSize++
    }
    // Return the last number found.
    return lastNum
  }
}