package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.pow

/**
 * --- Day 25: Full of Hot Air ---
 * https://adventofcode.com/2022/day/25
 */
class Day25 : Solver {
  val map = mapOf('0' to 0L, '1' to 1L, '2' to 2L, '-' to -1L, '=' to -2L)
  override fun solve(lines: List<String>): Result {
    return Result(lines.sumOf { toDecimal(it) }.toSnafu(), "")
  }

  private fun toDecimal(snafu: String) = snafu.reversed().withIndex()
    .sumOf { (5.0.pow(it.index) * it.value.snafuDigit()).toLong() }

  private fun Long.toSnafu(): String {
    var result = ""
    var rest = this
    while (rest != 0L) {
      val p = rest % 5
      if (p < 3) {
        result += map.map { it.value to it.key }.toMap()[p]!!
        rest -= p
      } else {
        rest += 5
        result += map.map { it.value to it.key }.toMap()[p - 5]!!
      }
      rest /= 5
    }
    return result.reversed()
  }

  private fun Char.snafuDigit() = map[this]!!
}