package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 8: Matchsticks ---
 * https://adventofcode.com/2015/day/8
 */
class Day8 : Solver {
  override fun solve(lines: List<String>): Result {

    val m1 = lines.map { Pair(it.length, decode(it)) }
      .sumBy { it.first - it.second }
    val m2 = lines.map { Pair(it.length, encode(it)) }
      .sumBy { it.second - it.first }

    return resultFrom(m1, m2)
  }

  private fun encode(str: String): Int {
    var result = 0
    var i = 0
    while (i < str.length) {
      if (str[i] == '"' || str[i] == '\\') {
        result += 1
      }
      result += 1
      i += 1
    }
    println("Length: ${str.length} -> $result")
    return result + 2
  }

  private fun decode(str: String): Int {
    var result = 0
    var i = 1
    while (i < str.length - 1) {
      if (str[i] == '\\') {
        i += 1
        if (str[i] == 'x') i += 2
      }
      result += 1
      i += 1
    }
    return result
  }
}