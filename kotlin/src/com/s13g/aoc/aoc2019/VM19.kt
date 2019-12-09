package com.s13g.aoc.aoc2019

import java.lang.Exception
import java.math.BigInteger

fun createVm(v: MutableList<Int>, input: MutableList<Int> = mutableListOf()): VM19 {
  val program = v.map { it.toBigInteger() }.toMutableList()
  val inp = input.map { it.toBigInteger() }.toMutableList()
  return VM19(program, inp)
}

/** Intcode computer for AoC 2019, used in multiple days */
class VM19(private val v: MutableList<BigInteger>,
           private var input: MutableList<BigInteger> = mutableListOf()) {
  var lastOutput = 0.toBigInteger()
  var outputStr = ""
  var outputVm: VM19? = null
  var isHalted = false
  var relBase = 0

  private var ip = 0
  fun run(): Int {
    while (true) {
      if (!step()) {
        break
      }
    }
    return lastOutput.toInt()
  }

  fun step(): Boolean {
    val instr = getInstr(ip)
    if (instr.op == 99 || isHalted) {
      isHalted = true
      return false
    }
    if (instr.op == 1) { // ADD
      put(v[ip + 3], instr.mode3,
          get(v[ip + 1], instr.mode1) + get(v[ip + 2], instr.mode2))
      ip += 4
    } else if (instr.op == 2) { // MUL
      put(v[ip + 3], instr.mode3,
          get(v[ip + 1], instr.mode1) * get(v[ip + 2], instr.mode2))
      ip += 4
    } else if (instr.op == 3) { // GET INPUT
      println("Input param mode: ${instr.mode1} relBase: $relBase")
      if (!hasNextInput()) return true
      put(v[ip + 1], instr.mode1, getNextInput())
      ip += 2
    } else if (instr.op == 4) { // ADD OUTPUT
      onOutput(get(v[ip + 1], instr.mode1))
      ip += 2
    } else if (instr.op == 5) { // JUMP IF NOT ZERO
      if (get(v[ip + 1], instr.mode1).toInt() != 0) {
        ip = get(v[ip + 2], instr.mode2).toInt()
      } else {
        ip += 3
      }
    } else if (instr.op == 6) {  // JUMP IF ZERO
      if (get(v[ip + 1], instr.mode1).toInt() == 0) {
        ip = get(v[ip + 2], instr.mode2).toInt()
      } else {
        ip += 3
      }
    } else if (instr.op == 7) { // LESS THAN
      put(v[ip + 3], instr.mode3,
          if (get(v[ip + 1], instr.mode1) < get(v[ip + 2], instr.mode2)) BigInteger.ONE else BigInteger.ZERO)
      ip += 4
    } else if (instr.op == 8) { // EQUALS
      put(v[ip + 3], instr.mode3,
          if (get(v[ip + 1], instr.mode1) == get(v[ip + 2], instr.mode2)) BigInteger.ONE else BigInteger.ZERO)
      ip += 4
    } else if (instr.op == 9) { // ADJUST RELATIVE BASE
      relBase += get(v[ip + 1], instr.mode1).toInt()
      ip += 2
    }
    return true
  }

  fun get(r: Int): Int {
    return get(r.toBigInteger()).toInt()
  }

  fun get(r: BigInteger, mode: Int = 0) = when (mode) {
    0 -> v[r.toInt()]
    1 -> r
    2 -> v[r.toInt() + relBase]
    else -> throw Exception("Unknown mode")
  }

  private fun put(r: BigInteger, mode: Int = 0, value: BigInteger) {
    when (mode) {
      0 -> v[r.toInt()] = value
      1 -> throw Exception("Illegal mode for put")
      2 -> v[r.toInt() + relBase] = value
      else -> throw Exception("Unknown mode")
    }
  }

  private fun hasNextInput(): Boolean {
    return input.isNotEmpty()
  }

  private fun getNextInput(): BigInteger {
    val result = input[0]
    input = input.drop(1).toMutableList()
    return result
  }

  private fun onOutput(out: BigInteger) {
    lastOutput = out
    outputStr += (if (outputStr.isBlank()) "" else ",") + "$out"
    outputVm?.addInput(out)
  }

  internal fun addInput(n: Int) {
    input.add(n.toBigInteger())
  }

  internal fun addInput(n: BigInteger) {
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