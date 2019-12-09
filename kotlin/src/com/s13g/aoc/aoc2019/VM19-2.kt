package com.s13g.aoc.aoc2019

import java.lang.Exception

/** Intcode computer for AoC 2019, used in multiple days */
class `VM19-2`(private val program: MutableList<Int>,
               private var input: MutableList<Int> = mutableListOf()) {
  var lastOutput = -1
  var outputStr = ""
  var outputVm: `VM19-2`? = null
  var isHalted = false
  var relBase = 0
  var v = mutableMapOf<Int, Int>()

  private var ip = 0
  fun run(): Int {
    createMemory()
    while (true) {
      if (!step()) {
        break
      }
    }
    return lastOutput
  }

  fun createMemory() {
    for ((i, n) in program.withIndex()) {
      v[i] = n
    }
  }

  fun step(): Boolean {
    val instr = getInstr(ip)
    if (instr.op == 99 || isHalted) {
      isHalted = true
      return false
    }
    if (instr.op == 1) { // ADD
      v[g(ip + 3)] =
          get(g(ip + 1), instr.mode1) + get(g(ip + 2), instr.mode2)
      ip += 4
    } else if (instr.op == 2) { // MUL
      v[g(ip + 3)] =
          get(g(ip + 1), instr.mode1) * get(g(ip + 2), instr.mode2)
      ip += 4
    } else if (instr.op == 3) { // GET INPUT
      if (!hasNextInput()) return true
      v[g(ip + 1)] = getNextInput()
      ip += 2
    } else if (instr.op == 4) { // ADD OUTPUT
      onOutput(get(g(ip + 1), instr.mode1))
      ip += 2
    } else if (instr.op == 5) { // JUMP IF NOT ZERO
      if (get(g(ip + 1), instr.mode1) != 0) {
        ip = get(g(ip + 2), instr.mode2)
      } else {
        ip += 3
      }
    } else if (instr.op == 6) {  // JUMP IF ZERO
      if (get(g(ip + 1), instr.mode1) == 0) {
        ip = get(g(ip + 2), instr.mode2)
      } else {
        ip += 3
      }
    } else if (instr.op == 7) { // LESS THAN
      v[g(ip + 3)] =
          if (get(g(ip + 1), instr.mode1) < get(g(ip + 2), instr.mode2)) 1 else 0
      ip += 4
    } else if (instr.op == 8) { // EQUALS
      v[g(ip + 3)] =
          if (get(g(ip + 1), instr.mode1) == get(g(ip + 2), instr.mode2)) 1 else 0
      ip += 4
    } else if (instr.op == 9) { // ADJUST RELATIVE BASE
      relBase += get(g(ip + 1), instr.mode1)
      ip += 2
    }
    return true
  }

  private fun g(r: Int) = v[r] ?: 0

  fun get(r: Int, mode: Int = 0) = when (mode) {
    0 -> g(r)
    1 -> r
    2 -> g(r + relBase)
    else -> throw Exception("Unknown mode")
  }

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
    outputStr += "$out"
    outputVm?.addInput(out)
  }

  internal fun addInput(n: Int) {
    input.add(n)
  }

  fun sendOutputTo(vm: `VM19-2`) {
    outputVm = vm
  }

  private fun getInstr(ip: Int): Instr {
    fun cToI(c: Char) = c.toString().toInt()
    val padded = g(ip).toString().padStart(5, '0')
    val op = padded.substring(3).toInt()
    return Instr(op, cToI(padded[2]), cToI(padded[1]), cToI(padded[0]))
  }

  private data class Instr(val op: Int, val mode1: Int, val mode2: Int, val mode3: Int)
}