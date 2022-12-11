package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs
import kotlin.math.floor

/**
 * --- Day 10: Cathode-Ray Tube ---
 * https://adventofcode.com/2022/day/10
 */
class Day10 : Solver {
  private var regX = 1
  private var cycle = 0
  private val cycleVals = mutableListOf<Int>()
  private val crt = mutableListOf<StringBuilder>()

  private fun cycle() {
    val row = (floor(cycle / 40f)).toInt()
    val col = cycle % 40
    if (abs(col - regX.coerceIn(0, 40)) <= 1) crt[row].setCharAt(col, '#')
    cycle++
    if (cycle <= 220 && cycle % 40 == 20) cycleVals.add(cycle * regX)
  }

  override fun solve(lines: List<String>): Result {
    (1..6).forEach { _ -> crt.add(StringBuilder(".".repeat(40))) }
    for (line in lines) {
      val split = line.split(" ")
      val instr = split[0]
      if (instr == "addx") {
        for (i in 1..2) cycle()
        regX += split[1].toInt()
      } else cycle()
    }
    val part2 = "\n" + crt.joinToString("\n") { it.toString() }
    return Result(cycleVals.sum().toString(), part2)
  }
}