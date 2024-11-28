package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

private const val NUM_DIGITS = 6

/** https://adventofcode.com/2019/day/4 */
class Day4 : Solver {
  override fun solve(lines: List<String>): Result {
    val split = lines[0].split("-")
    val from = split[0].toInt()
    val to = split[1].toInt()

    var resultA = 0
    var resultB = 0
    for (n in from..to) {
      if (legal("$n", false)) {
        resultA++
      }
      if (legal("$n", true)) {
        resultB++
      }
    }
    return Result("$resultA", "$resultB")
  }

  private fun legal(s: String, partB: Boolean): Boolean {
    for (i in 0..NUM_DIGITS - 2) {
      if (s[i] > s[i + 1]) {
        return false
      }
    }
    for (i in 0..NUM_DIGITS - 2) {
      if (s[i] == s[i + 1] &&
          (!partB || (get(s, i - 1) != s[i] && s[i + 1] != get(s, i + 2)))) {
        return true
      }
    }
    return false
  }

  private fun get(a: String, n: Int) = when {
    n < 0 -> ' '
    n >= a.length -> ' '
    else -> a[n]
  }
}