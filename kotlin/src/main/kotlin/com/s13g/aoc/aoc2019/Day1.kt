package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/1 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val fuelA = lines.sumBy { l -> calcFuel(l.toInt()) }
    val fuelB = lines.sumBy { l -> bFuel(l.toInt()) }
    return Result("$fuelA", "$fuelB")
  }

  private fun bFuel(mass: Int): Int {
    val fuel = calcFuel(mass)
    return if (fuel <= 0) 0 else fuel + bFuel(fuel)
  }

  private fun calcFuel(v: Int) = v / 3 - 2
}