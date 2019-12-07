package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.lang.Integer.max

/** https://adventofcode.com/2019/day/5 */
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
        (0..4).map { VM(program.toMutableList(), mutableListOf(phasing[it])) }
    var lastOutput = 0
    for (vm in vms) {
      vm.addInput(lastOutput)
      lastOutput = vm.run()
    }
    return lastOutput
  }

  private fun runB(phasing: List<Int>, program: List<Int>): Int {
    val vms =
        (0..4).map { VM(program.toMutableList(), mutableListOf(phasing[it])) }
    for (i in 0..4) {
      vms[i].sendOutputTo(vms[(i + 1) % vms.size])
    }
    vms[0].addInput(0)

    while (!vms[4].isHalted) {
      for (vm in vms) {
        vm.step()
      }
    }
    return vms[4].lastOutput
  }

  // TODO: Make this a re-usable class for all Days that make use of it.
  private class VM(private val v: MutableList<Int>, private var input: MutableList<Int>) {
    var lastOutput = -1
    var outputVm: VM? = null
    var isHalted = false

    private var ip = 0
    fun run(): Int {
      while (true) {
        if (!step()) {
          break
        }
      }
      return lastOutput
    }

    fun step(): Boolean {
      val instr = getInstr(ip)
      if (instr.op == 99 || isHalted) {
        isHalted = true
        return false
      }
      if (instr.op == 1) { // ADD
        v[v[ip + 3]] =
            get(v[ip + 1], instr.mode1) + get(v[ip + 2], instr.mode2)
        ip += 4
      } else if (instr.op == 2) { // MUL
        v[v[ip + 3]] =
            get(v[ip + 1], instr.mode1) * get(v[ip + 2], instr.mode2)
        ip += 4
      } else if (instr.op == 3) { // GET INPUT
        if (!hasNextInput()) return true
        v[v[ip + 1]] = getNextInput()
        ip += 2
      } else if (instr.op == 4) { // ADD OUTPUT
        onOutput(get(v[ip + 1], instr.mode1))
        ip += 2
      } else if (instr.op == 5) { // JUMP IF NOT ZERO
        if (get(v[ip + 1], instr.mode1) != 0) {
          ip = get(v[ip + 2], instr.mode2)
        } else {
          ip += 3
        }
      } else if (instr.op == 6) {  // JUMP IF ZERO
        if (get(v[ip + 1], instr.mode1) == 0) {
          ip = get(v[ip + 2], instr.mode2)
        } else {
          ip += 3
        }
      } else if (instr.op == 7) { // LESS THAN
        v[v[ip + 3]] =
            if (get(v[ip + 1], instr.mode1) < get(v[ip + 2], instr.mode2)) 1 else 0
        ip += 4
      } else if (instr.op == 8) { // EQUALS
        v[v[ip + 3]] =
            if (get(v[ip + 1], instr.mode1) == get(v[ip + 2], instr.mode2)) 1 else 0
        ip += 4
      }
      return true
    }

    private fun get(r: Int, mode: Int) = if (mode == 1) r else v[r]

    private fun hasNextInput(): Boolean {
      return input.isNotEmpty()
    }

    private fun getNextInput(): Int {
      val result = input[0]
      input = input.drop(1).toMutableList()
      return result
    }

    private fun onOutput(out: Int) {
      lastOutput = out
      outputVm?.addInput(out)
    }

    internal fun addInput(n: Int) {
      input.add(n)
    }

    fun sendOutputTo(vm: VM) {
      outputVm = vm
    }

    private fun getInstr(ip: Int): Instr {
      fun cToI(c: Char) = c.toString().toInt()
      val padded = v[ip].toString().padStart(5, '0')
      val op = padded.substring(3).toInt()
      return Instr(op, cToI(padded[2]), cToI(padded[1]), cToI(padded[0]))
    }

    private data class Instr(val op: Int, val mode1: Int, val mode2: Int, val mode3: Int)
  }
}