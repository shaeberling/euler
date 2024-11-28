package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/5 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    val regs = lines[0].split(",").map { it.toInt() }
    val solutionA = createVm(regs.toMutableList(), arrayListOf(1)).run()
    val solutionB = createVm(regs.toMutableList(), arrayListOf(5)).run()
    return Result("$solutionA", "$solutionB")
  }
}

