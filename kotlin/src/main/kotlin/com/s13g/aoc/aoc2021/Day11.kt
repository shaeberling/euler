package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 11: Dumbo Octopus ---
 * https://adventofcode.com/2021/day/11
 */
class Day11 : Solver {
  override fun solve(lines: List<String>): Result {
    val board = Board(lines.map { it.map { ch -> ch.toString().toInt() }.toMutableList() })

    var partA = 0
    var partB = 0
    for (x in 1..Int.MAX_VALUE) {
      board.incAll()
      val flashes = board.countFlashesAndReset()
      if (x <= 100) partA += flashes
      if (flashes == board.height() * board.width() && partB == 0) partB = x
      if (x >= 100 && partB != 0) break
    }
    return Result("$partA", "$partB")
  }
}

private class Board(val input: List<MutableList<Int>>) {
  fun set(x: Int, y: Int, value: Int) {
    input[y][x] = value
  }

  fun at(x: Int, y: Int) = input[y][x]
  fun width() = input[0].size
  fun height() = input.size

  fun incAll() {
    for (yy in 0 until height()) {
      for (xx in 0 until width()) {
        inc(xx, yy)
      }
    }
  }

  fun inc(x: Int, y: Int) {
    if (x >= 0 && x < width() && y >= 0 && y < height()) {
      set(x, y, at(x, y) + 1)
      if (at(x, y) == 10) flash(x, y)
    }
  }

  fun flash(x: Int, y: Int) {
    for (yy in y - 1..y + 1) {
      for (xx in x - 1..x + 1) {
        inc(xx, yy)
      }
    }
  }

  fun countFlashesAndReset(): Int {
    var result = 0
    for (yy in 0 until height()) {
      for (xx in 0 until width()) {
        if (at(xx, yy) >= 10) {
          result++
          set(xx, yy, 0)
        }
      }
    }
    return result
  }
}