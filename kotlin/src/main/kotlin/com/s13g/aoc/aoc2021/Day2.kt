package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 2: Dive! ---
 * https://adventofcode.com/2021/day/2
 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    val instructions = lines.map { it.split(" ") }.map { Instr(it[0], it[1].toLong()) }
    return resultFrom(calcA(instructions), calcB(instructions))
  }

  private fun calcA(instructions: List<Instr>): Long {
    var horPos = 0L
    var depth = 0L
    for (i in instructions) {
      if (i.instr == "forward") horPos += i.value
      if (i.instr == "down") depth += i.value
      if (i.instr == "up") depth -= i.value
    }
    return horPos * depth
  }

  private fun calcB(instructions: List<Instr>): Long {
    var horPos = 0L
    var depth = 0L
    var aim = 0L
    for (i in instructions) {
      if (i.instr == "forward") {
        horPos += i.value
        depth += i.value * aim
      }
      if (i.instr == "down") aim += i.value
      if (i.instr == "up") aim -= i.value
    }
    return horPos * depth
  }

  private data class Instr(val instr: String, val value: Long)
}