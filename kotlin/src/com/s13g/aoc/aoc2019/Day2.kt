package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/2 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    val p = lines[0].split(",").map { it.toInt() }
    return Result("${runWith(12, 2, p)}", "${runB(p)}")
  }

  private fun runB(program: List<Int>): Int {
    for (noun in 0..99) {
      for (verb in 0..99) {
        if (runWith(noun, verb, program) == 19690720) {
          return 100 * noun + verb
        }
      }
    }
    return -1
  }

  private fun runWith(noun: Int, verb: Int, program: List<Int>): Int {
    val p = program.toMutableList()
    p[1] = noun
    p[2] = verb
    val vm = VM19(p)
    vm.run()
    return vm.get(0)
  }
}