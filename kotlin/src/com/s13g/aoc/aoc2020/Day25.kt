package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 25: Combo Breaker ---
 * https://adventofcode.com/2020/day/25
 */
class Day25 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines.map { it.toLong() }.map { Pair(it, transform(7, it)) }
    val encKey1 = loopIt(input[0].first, input[1].second)
    val encKey2 = loopIt(input[1].first, input[0].second)
    assert(encKey1 == encKey2)
    return Result("$encKey1", "")
  }

  private fun loopIt(subjectNo: Long, times: Long): Long {
    var value = 1L
    var loop = 0L
    for (i in 1..times) {
      value = (value * subjectNo) % 20201227L
      loop++
    }
    return value
  }

  private fun transform(subjectNo: Long, key: Long): Long {
    var value = 1L
    var loop = 0L
    while (value != key) {
      value = (value * subjectNo) % 20201227L
      loop++
    }
    return loop
  }
}