package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.addTo
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

/**
 * --- Day 17:
 * https://adventofcode.com/2021/day/17
 */
class Day17 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines[0].substring(13)
      .split(',')
      .map {
        it.trim()
          .substring(2)
          .split("..")
      }
      .map { Pair(it[0].toInt(), it[1].toInt()) }
    val result = runSimulation(input[0].first, input[0].second, input[1].first, input[1].second)
    return Result("${result.first}", "${result.second}")
  }

  private fun runSimulation(x1: Int, x2: Int, y1: Int, y2: Int): Pair<Int, Int> {
    var highestY = 0
    var numHittingVelocities = 0
    // Based on our input, both x values are positive.
    for (dX in 0..300) {
      for (dY in -200..200) {
        val highY = simulate(dX, dY, x1, x2, y1, y2)
        if (highY != Int.MIN_VALUE) numHittingVelocities++
        highestY = max(highestY, highY)
      }
    }
    return Pair(highestY, numHittingVelocities)
  }

  private fun simulate(dX: Int, dY: Int, x1: Int, x2: Int, y1: Int, y2: Int): Int {
    val vel = XY(dX, dY)
    val pos = XY(0, 0)
    var maxY = Int.MIN_VALUE
    // Note, this only works if x values are positive and y values are negative. Check your input!
    while (pos.x <= max(x1, x2) && pos.y >= min(y1, y2)) {
      pos.addTo(vel)
      maxY = max(maxY, pos.y)
      if (pos.x in x1..x2 && pos.y in y1..y2) {
        return maxY
      }
      vel.x += -vel.x.sign
      vel.y -= 1
    }
    return Int.MIN_VALUE
  }
}