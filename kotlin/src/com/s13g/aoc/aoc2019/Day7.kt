package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.lang.Integer.max

/** https://adventofcode.com/2019/day/7 */
class Day7 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines[0].split(",").map { it.toInt() }
    var maxThrusterA = 0
    for (phasing in genPerms(0, mutableListOf())) {
      maxThrusterA = max(maxThrusterA, runA(phasing, input))
    }

    var maxThrusterB = 0
    for (phasing in genPerms(0, mutableListOf(), 5)) {
      maxThrusterB = max(maxThrusterB, runB(phasing, input))
    }
    return Result("$maxThrusterA", "$maxThrusterB")
  }

  private fun genPerms(n: Int, list: MutableList<Int>,
                       offset: Int = 0): MutableList<List<Int>> {
    if (n > 4) return mutableListOf(list)
    val result = mutableListOf<List<Int>>()
    for (x in offset..offset + 4) {
      if (x in list) continue
      val listX = ArrayList(list)
      listX.add(x)
      result.addAll(genPerms(n + 1, listX, offset))
    }
    return result
  }

  private fun runA(phasing: List<Int>, program: List<Int>): Int {
    val vms =
        (0..4).map { createVm(program.toMutableList(), mutableListOf(phasing[it])) }
    var lastOutput = 0
    for (vm in vms) {
      vm.addInput(lastOutput)
      lastOutput = vm.run()
    }
    return lastOutput
  }

  private fun runB(phasing: List<Int>, program: List<Int>): Int {
    val vms =
        (0..4).map { createVm(program.toMutableList(), mutableListOf(phasing[it])) }
    for (i in 0..4) {
      vms[i].sendOutputTo(vms[(i + 1) % vms.size])
    }
    vms[0].addInput(0)

    while (!vms[4].isHalted) {
      for (vm in vms) {
        vm.step()
      }
    }
    return vms[4].lastOutput.toInt()
  }
}