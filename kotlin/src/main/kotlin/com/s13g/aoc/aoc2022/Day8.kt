package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.addTo
import com.s13g.aoc.resultFrom
import kotlin.math.max

/**
 * --- Day 8: Treetop Tree House ---
 * https://adventofcode.com/2022/day/8
 */
class Day8 : Solver {
  override fun solve(lines: List<String>): Result {
    val (width, height) = arrayOf(lines[0].length, lines.size)
    var (numVisible, maxScore) = arrayOf(0, Int.MIN_VALUE)
    for (y in 0 until height) {
      for (x in 0 until width) {
        val treeVal = lines[y][x].code
        fun walk(xy: XY, delta: XY): Pair<Boolean, Int> {
          var free = true
          var n = 0

          while (xy.x in 0 until width && xy.y in 0 until height) {
            n++
            if (lines[xy.y][xy.x].code >= treeVal) {
              free = false
              break
            }
            xy.addTo(delta)
          }
          return Pair(free, n)
        }

        val (leftFree, numLeft) = walk(XY(x - 1, y), XY(-1, 0))
        val (rightFree, numRight) = walk(XY(x + 1, y), XY(+1, 0))
        val (upFree, numUp) = walk(XY(x, y - 1), XY(0, -1))
        val (downFree, numDown) = walk(XY(x, y + 1), XY(0, +1))

        if (leftFree || rightFree || upFree || downFree) numVisible++
        maxScore = max(maxScore, numLeft * numRight * numUp * numDown)
      }
    }
    return resultFrom(numVisible, maxScore)
  }
}