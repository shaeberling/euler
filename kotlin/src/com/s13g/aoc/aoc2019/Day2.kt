package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/2 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    val vm = VM(lines[0].split(",").map { n -> n.toInt() })
    return Result("${vm.run(12, 2)}", "${runB(vm)}")
  }

  private fun runB(vm: VM): Int {
    for (noun in 0..99) {
      for (verb in 0..99) {
        if (vm.run(noun, verb) == 19690720) {
          return 100 * noun + verb
        }
      }
    }
    return -1
  }
}

private class VM(private val initV: List<Int>) {
  fun run(noun: Int, verb: Int): Int {
    val v = initV.toMutableList()
    v[1] = noun
    v[2] = verb

    var ip = 0
    while (v[ip] != 99) {
      if (v[ip] == 1) {
        v[v[ip + 3]] = v[v[ip + 1]] + v[v[ip + 2]]
      } else if (v[ip] == 2) {
        v[v[ip + 3]] = v[v[ip + 1]] * v[v[ip + 2]]
      }
      ip += 4
    }
    return v[0]
  }
}