package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 5:  ---
 * https://adventofcode.com/2020/day/5
 */
class Day5 : Solver {

  override fun solve(lines: List<String>): Result {
    val seats = lines.map { calcSeat(it) }.map { it.first * 8 + it.second }.toSet()
    return Result("${seats.max()}", "${findSeat(seats)}")
  }

  // Solution for Part B
  private fun findSeat(occupiedSeats: Set<Int>): Int {
    for (x in 1..(127 * 7)) if (occupiedSeats.contains(x - 1) && !occupiedSeats.contains(x)) return x
    error("Cannot find free seat.")
  }
}

private fun calcSeat(line: String): Pair<Int, Int> {
  val r = Range(0, 127)
  val c = Range(0, 7)
  return Pair((0..6).map { line[it] }.map { r.process(it) }.last(),
      (7..9).map { line[it] }.map { c.process(it) }.last())
}

private data class Range(var from: Int, var to: Int)

private fun Range.process(c: Char): Int {
  if (c == 'F' || c == 'L') to = ((to - from) / 2) + from
  if (c == 'B' || c == 'R') from += ((to - from) / 2)
  return to
}
