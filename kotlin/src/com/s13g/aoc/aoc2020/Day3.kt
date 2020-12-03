package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul

/**
 * --- Day 3: Toboggan Trajectory ---
 * https://adventofcode.com/2020/day/3
 */
class Day3 : Solver {

  override fun solve(lines: List<String>): Result {
    val slopesB = listOf(Slope(1, 1), Slope(3, 1), Slope(5, 1), Slope(7, 1), Slope(1, 2))
    return Result("${solve(listOf(Slope(3, 1)), lines)}", "${solve(slopesB, lines)}")
  }

  private fun solve(slopes: List<Slope>, level: List<String>): Int {
    val numTrees = mutableListOf<Int>()
    for (slope in slopes) {
      var x = slope.dX
      var y = slope.dY
      var num = 0
      while (y < level.size) {
        if (level.isTree(x, y)) num++
        x += slope.dX
        y += slope.dY
      }
      numTrees.add(num)
    }
    return numTrees.mul()
  }

  private fun List<String>.isTree(x: Int, y: Int) = this[y][x % this[y].length] == '#'
  private data class Slope(val dX: Int, val dY: Int)
}

