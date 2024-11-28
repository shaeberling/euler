package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom
import kotlin.math.max
import kotlin.math.min


typealias Universe = List<String>

/**
 * --- Day 11: Cosmic Expansion ---
 * https://adventofcode.com/2023/day/11
 */
class Day11 : Solver {
  override fun solve(image: Universe): Result {
    val gals = image.galaxies()
    val expCols = image.expandingCols()
    val expRows = image.expandingRows()

    var sumA = 0L
    var sumB = 0L
    for (i in gals.indices) {
      for (j in gals.indices.drop(i + 1)) {
        sumA += shortestPathLength(gals[i], gals[j], expCols, expRows, 1)
        sumB += shortestPathLength(gals[i], gals[j], expCols, expRows, 999_999)
      }
    }
    return resultFrom(sumA, sumB)
  }

  private fun Universe.galaxies(): List<XY> {
    val result = mutableListOf<XY>()
    for ((y, line) in this.withIndex()) {
      for ((x, ch) in line.withIndex()) {
        if (ch == '#') result.add(XY(x, y))
      }
    }
    return result;
  }

  private fun shortestPathLength(
    a: XY,
    b: XY,
    expCols: Set<Int>,
    expRows: Set<Int>,
    expandBy: Int
  ): Long {
    var len = 0L
    for (p in shortestPath(a, b)) {
      len++
      if (p.x in expCols) len += expandBy
      if (p.y in expRows) len += expandBy
    }
    return len
  }

  private fun shortestPath(a: XY, b: XY): List<XY> {
    val path1 = (min(a.x, b.x) + 1..max(a.x, b.x)).map { XY(it, b.y) }
    val path2 = (min(a.y, b.y) + 1..max(a.y, b.y)).map { XY(b.x, it) }
    return path1.plus(path2)
  }

  private fun Universe.expandingCols() =
    this[0].indices.filter { c -> column(c).count { it != '.' } == 0 }.toSet()

  private fun Universe.expandingRows() =
    this.withIndex().filter { (_, line) -> line.count { it != '.' } == 0 }
      .map { (i, _) -> i }.toSet()

  private fun Universe.column(col: Int) = this.map { it[col] }.joinToString("")
}