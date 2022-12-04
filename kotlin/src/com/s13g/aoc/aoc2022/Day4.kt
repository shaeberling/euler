package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 4: Camp Cleanup ---
 * https://adventofcode.com/2022/day/4
 */
class Day4 : Solver {

  override fun solve(lines: List<String>): Result {
    val re = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
    val intData = lines.map { re.find(it)!!.destructured }
      .map { (l0, l1, r0, r1) -> arrayOf(l0, l1, r0, r1) }
      .map { it.map { e -> e.toInt() } }

    val m1 = intData.count {
      (it[0] >= it[2] && it[1] <= it[3]) ||
          (it[2] >= it[0] && it[3] <= it[1])
    }
    val m2 = intData.count { it[2] <= it[1] && it[3] >= it[0] }
    return resultFrom(m1, m2)
  }
}