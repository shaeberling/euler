package com.s13g.aoc.aoc2018

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2018/day/2 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    val counts = Counts()
    lines.map { s -> count(s) }.forEach { c -> counts.add(c) }
    return Result(counts.checksum(), solveB(lines))
  }

  private fun count(line: String): Counts {
    val counts = hashMapOf<Char, Int>()
    line.forEach { c -> counts[c] = (counts[c] ?: 0) + 1 }
    return Counts(if (counts.containsValue(2)) 1 else 0,
        if (counts.containsValue(3)) 1 else 0)
  }

  private fun solveB(lines: List<String>): String {
    for (i in lines.indices) {
      for (j in i + 1 until lines.size) {
        val result = findDiffByOne(lines[i], lines[j])
        if (result != null) return result
      }
    }
    return "not found"
  }

  private fun findDiffByOne(a: String, b: String): String? {
    var diff = 0
    var result = ""
    for (i in a.indices) {
      if (a[i] != b[i]) {
        diff++
      } else {
        result += a[i]
      }
    }
    return if (diff == 1) result else null
  }
}

private class Counts(var twos: Int = 0, var threes: Int = 0) {
  fun add(other: Counts) {
    twos += other.twos
    threes += other.threes
  }

  fun checksum(): String {
    return (twos * threes).toString()
  }
}