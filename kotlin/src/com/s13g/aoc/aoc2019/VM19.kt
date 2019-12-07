package com.s13g.aoc.aoc2019

/** Intcode computer for AoC 2019, used in multiple days */
class VM19(private val v: MutableList<Int>,
           private var input: MutableList<Int> = mutableListOf()) {
  var lastOutput = -1
  var outputVm: VM19? = null
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

  fun get(r: Int, mode: Int = 0) = if (mode == 1) r else v[r]

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

  fun sendOutputTo(vm: VM19) {
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