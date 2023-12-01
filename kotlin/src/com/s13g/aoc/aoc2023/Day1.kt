package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 1: Trebuchet?! ---
 * https://adventofcode.com/2023/day/1
 */
class Day1 : Solver {
  val digitsStr = "one,two,three,four,five,six,seven,eight,nine".split(",")

  override fun solve(lines: List<String>): Result {
    val result1 = lines.sumOf { getNum(it, false) }
    val result2 = lines.sumOf { getNum(it, true) }
    return Result(result1.toString(), result2.toString())
  }

  fun getNum(line: String, part2: Boolean): Int {
    val digits = line.indices
      .map { extractDigit(line.substring(it), part2) }
      .filter { it >= 0 }
      .toList()
    return digits.first() * 10 + digits.last()
  }

  fun extractDigit(str: String, part2: Boolean): Int {
    if (str[0].isDigit()) return str[0].digitToInt()
    for (d in digitsStr) {
      if (part2 && str.startsWith(d)) return digitsStr.indexOf(d) + 1
    }
    return -1
  }
}