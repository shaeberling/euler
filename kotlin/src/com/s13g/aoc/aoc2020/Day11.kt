package com.s13g.aoc.aoc2020

import com.s13g.aoc.*

/**
 * --- Day 11: Seating System ---
 * https://adventofcode.com/2020/day/11
 */
class Day11 : Solver {
  override fun solve(input: List<String>): Result {
    return Result("${dance(input, 4, true).countOccupied()}", "${dance(input, 5, false).countOccupied()}")
  }
}

// Iterate over the whole seating config and produce a new one as we go.
fun dance(input: List<String>, minAdj: Int, isA: Boolean): List<String> {
  var current = input.toMutableList()
  while (true) {
    val newOne = current.toMutableList()
    for (y in input.indices) {
      var newLine = ""
      for (x in input[y].indices) {
        val seat = current.seat(XY(x, y))
        val num = if (isA) current.numAdj(XY(x, y), 1) else current.numAdj(XY(x, y), 1000)
        newLine += if (seat == 'L' && num == 0) {
          '#'
        } else if (seat == '#' && num >= minAdj) {
          'L'
        } else current.seat(XY(x, y))
      }
      newOne[y] = newLine
    }
    if (current == newOne) return current
    current = newOne
  }
}

// Iterate over the eight directions for 'max' maximum steps. For part A that's '1'.
val dirs = listOf(XY(-1, -1), XY(0, -1), XY(1, -1), XY(-1, 0), XY(1, 0), XY(-1, 1), XY(0, 1), XY(1, 1))
fun List<String>.numAdj(xy: XY, max: Int): Int {
  var count = 0
  for (dir in dirs) {
    val pos = xy.copy()
    for (i in 1..max) {
      pos.addTo(dir)
      if (seat(pos) == '#') count++
      if (seat(pos) != '.' || seat(pos) == 'E') break
    }
  }
  return count
}

fun List<String>.seat(xy: XY) =
    if (xy.x < 0 || xy.y < 0 || xy.y >= this.size || xy.x >= this[xy.y].length) 'E' else this[xy.y][xy.x]

fun List<String>.countOccupied() = this.flatMap { it.toList() }.count { it == '#' }

