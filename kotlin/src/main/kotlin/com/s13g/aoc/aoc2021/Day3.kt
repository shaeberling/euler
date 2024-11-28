package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 3: Binary Diagnostic ---
 * https://adventofcode.com/2021/day/3
 */
class Day3 : Solver {
  override fun solve(lines: List<String>): Result {
    val ge = lines[0].indices.map { i -> lines.fold(Counter(0, 0)) { cnt, s -> cnt.add(s[i]) } }
                             .fold(GammaEpsi("", "")) { res, cnt -> res.add(cnt) }
    val partA = ge.g.toInt(2) * ge.e.toInt(2)
    val partB = processB(lines, 0 /* Most common */) * processB(lines, 1 /* Least common */)
    return resultFrom(partA, partB)
  }

  private fun processB(lines: List<String>, mostLeastIdx: Int): Int {
    val ratingValues = lines.toCollection(mutableListOf())
    for (i in lines[0].indices) {
      val cnt = ratingValues.fold(Counter(0, 0)) { cnt, s -> cnt.add(s[i]) }
      for (l in ratingValues.toCollection(mutableListOf())) {
        if (l[i] != cnt.hiLow()[mostLeastIdx] && ratingValues.size > 1) ratingValues.remove(l)
      }
    }
    return ratingValues[0].toInt(2)
  }

  private data class GammaEpsi(val g: String, val e: String) {
    fun add(cnt: Counter) = cnt.hiLow().let { GammaEpsi(this.g + it[0], this.e + it[1]) }
  }

  private data class Counter(val zeros: Int, val ones: Int) {
    fun add(c: Char) = if (c == '0') Counter(this.zeros + 1, this.ones) else Counter(this.zeros, this.ones + 1)
    fun hiLow() = if (this.zeros > this.ones) listOf('0', '1') else listOf('1', '0')
  }
}