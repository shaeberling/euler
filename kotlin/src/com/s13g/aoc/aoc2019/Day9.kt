package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/9 */
class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    val program = lines[0].split(",").map { it.toLong() }.toMutableList()
    program.addAll(Array(1000) { 0L })

    val vmA = VM19(program, mutableListOf(1.toLong()))
    vmA.run()
    val vmB = VM19(program, mutableListOf(2.toLong()))
    vmB.run()
    return Result("${vmA.lastOutput}", "${vmB.lastOutput}")
  }
}