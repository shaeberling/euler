package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/5 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    val regs = lines[0].split(",").map { n -> n.toInt() }
    val solutionA = VM(regs.toMutableList(), 1).run().toInt()
    val solutionB = VM(regs.toMutableList(), 5).run().toInt()
    return Result("$solutionA", "$solutionB")
  }

  private class VM(private val v: MutableList<Int>, private var input: Int = 0) {
    var output: String = ""
    private var ip = 0
    fun run(): String {
      while (true) {
        val instr = getInstr(ip)
        if (instr.op == 99) {
          break
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
          v[v[ip + 1]] = input
          ip += 2
        } else if (instr.op == 4) { // ADD OUTPUT
          output += get(v[ip + 1], instr.mode1)
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
      }
      return output
    }

    fun get(r: Int, mode: Int) = if (mode == 1) r else v[r]

    fun getInstr(ip: Int): Instr {
      fun cToI(c: Char) = c.toString().toInt()
      val padded = v[ip].toString().padStart(5, '0')
      val op = padded.substring(3).toInt()
      return Instr(op, cToI(padded[2]), cToI(padded[1]), cToI(padded[0]))
    }
  }
}

private data class Instr(val op: Int, val mode1: Int, val mode2: Int, val mode3: Int)