package com.s13g.aoc.aoc2020

import com.s13g.aoc.*

/**
 * --- Day 11: Seating System ---
 * https://adventofcode.com/2020/day/11
 */
class Day11 : Solver {
  override fun solve(input: List<String>): Result {
    val dim = XY(input[0].length, input.size)
    val map = mutableMapOf<XY, Char>()

    for (y in input.indices) {
      for (x in input[y].indices) {
        map[XY(x, y)] = input[y][x]
      }
    }
    return Result("${dance(map, dim, 4, true).countOccupied()}", "${dance(map, dim, 5, false).countOccupied()}")
  }
}

// Iterate over the whole seating config and produce a new one as we go.
fun dance(input: Map<XY, Char>, dim: XY, minAdj: Int, isA: Boolean): Map<XY, Char> {
  var current = input.toMutableMap()
  while (true) {
    val newOne = current.toMutableMap()
    for (pos in input.keys) {
      val num = if (isA) current.numAdj(pos, 1) else current.numAdj(pos, dim.max())
      newOne[pos] = if (current[pos] == 'L' && num == 0) '#'
      else if (current[pos] == '#' && num >= minAdj) 'L'
      else current[pos]!!
    }
    if (current == newOne) return current
    current = newOne
  }
}

// Iterate over the eight directions for 'max' maximum steps. For part A that's '1'.
val dirs = listOf(XY(-1, -1), XY(0, -1), XY(1, -1), XY(-1, 0), XY(1, 0), XY(-1, 1), XY(0, 1), XY(1, 1))
fun Map<XY, Char>.numAdj(xy: XY, max: Int): Int {
  var count = 0
  for (dir in dirs) {
    val pos = xy.copy()
    for (i in 1..max) {
      pos.addTo(dir)
      if (this[pos] == '#') count++
      if (pos !in this || this[pos] != '.') break
    }
  }
  return count
}

fun Map<XY, Char>.countOccupied() = this.values.count { it == '#' }
