package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 5: Doesn't He Have Intern-Elves For This? ---
 * https://adventofcode.com/2015/day/5
 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    var resultA = 0
    for (line in lines) {
      val numVowels = line.count { it in setOf('a', 'e', 'i', 'o', 'u') }
      val doubles = line.zipWithNext().count { it.first == it.second }
      val badies = line.zipWithNext().count { it in setOf(Pair('a', 'b'), Pair('c', 'd'), Pair('p', 'q'), Pair('x', 'y')) }
      if (numVowels > 2 && doubles > 0 && badies == 0) resultA++
    }
    val resultB = lines.count { hasNonOverlappingPairs(it) && hasGapRepeat(it) }
    return Result("$resultA", "$resultB")
  }

  private fun hasNonOverlappingPairs(line: String): Boolean {
    for (i in 0..line.lastIndex - 2) {
      if (line.lastIndexOf(line.substring(i, i + 2)) > i + 1) return true
    }
    return false
  }

  private fun hasGapRepeat(line: String): Boolean {
    for ((i, c) in line.withIndex()) {
      if (i + 2 < line.length && line[i + 2] == c) return true
    }
    return false
  }
}