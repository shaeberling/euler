package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 25: Combo Breaker ---
 * https://adventofcode.com/2020/day/25
 */
class Day25 : Solver {
  override fun solve(lines: List<String>): Result {
    val pubKey1 = lines[0].toLong()
    val pubKey2 = lines[1].toLong()

    val loopSize1 = transform(7, pubKey1)
    val loopSize2 = transform(7, pubKey2)

    val encKey1 = loopIt(pubKey1, loopSize2)
    val encKey2 = loopIt(pubKey2, loopSize1)
    assert(encKey1 == encKey2)

    return Result("$encKey1", "")
  }

  private fun loopIt(subjectNo: Long, times: Long): Long {
    var value = 1L
    var loop = 0L
    for (i in 1..times) {
      value *= subjectNo
      value %= 20201227L
      loop++
    }
    return value
  }

  private fun transform(subjectNo: Long, key: Long): Long {
    var value = 1L
    var loop = 0L
    while (value != key) {
      value *= subjectNo
      value %= 20201227L
      loop++
    }
    return loop
  }
}