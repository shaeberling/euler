package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 10: Elves Look, Elves Say ---
 * https://adventofcode.com/2015/day/10
 */
class Day10 : Solver {
  override fun solve(lines: List<String>): Result {
    var m1 = 0
    var curr = lines[0]
    for (i in 1..50) {
      curr = step(curr)
      if (i == 40) m1 = curr.length
    }
    val m2 = curr.length

    return resultFrom(m1, m2)
  }

  private fun step(str: String): String {
    val counts = mutableListOf<Count>()
    for (ch in str) {
      if (counts.isNotEmpty() && counts.last().ch == ch) {
        counts.last().cnt++
      } else {
        counts.add(Count(ch, 1))
      }
    }
    return counts.map { "${it.cnt}${it.ch}" }.toList().joinToString("")
  }

  data class Count(val ch: Char, var cnt: Int)
}