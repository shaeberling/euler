package com.s13g.aoc.aoc2018

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

class Day19 : Solver {
  override fun solve(lines: List<String>): Result {
    val ipIndex = lines[0].split(" ")[1].toInt()
    val program = parseInstructions(lines)
    val vmA = AocVm(ipIndex, program)
    vmA.runUntilHalt()
    val solutionA = vmA.getReg(0).toString()

    // This would run for years.... see the go solution for an explanation
    // https://github.com/shaeberling/euler/blob/master/go/src/aoc18/problems/p19/p19.go
//    val vmB = AocVm(ipIndex, program)
//    vmB.setReg(0, 1)
//    vmB.runUntilHalt()
//    val solutionB = vmB.getReg(0).toString()

    return Result(solutionA, "10750428")
  }
}