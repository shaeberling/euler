package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.pow

/**
 * --- Day 5: Binary Boarding ---
 * https://adventofcode.com/2020/day/5
 */
class Day5 : Solver {

  override fun solve(lines: List<String>): Result {
    return solveFast(lines)
    // Note, if I would have been clever I would have realized that this is just a binary encoding.
    // In any case, I am leaving this solution up as I want to show what I did to solve the problem int he moment.
    //    val seats = lines.map { calcSeat(it) }.map { it.first * 8 + it.second }.toSet()
    //    return Result("${seats.max()}", "${findSeat(seats)}")
  }

  // Solution for Part B
  private fun findSeat(occupiedSeats: Set<Int>): Int {
    for (x in 1..(127 * 8)) if (occupiedSeats.contains(x - 1) && !occupiedSeats.contains(x)) return x
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

// Did this after I finally realized this is just binary encoding.
private fun solveFast(lines: List<String>): Result {
  val occ = mutableSetOf<Int>()
  for (line in lines) {
    var v = line.withIndex().map { if (it.value == 'B') 2.0.pow(6 - it.index).toInt() * 8 else 0 }.sum()
    v += line.withIndex().map { if (it.value == 'R') 2.0.pow(9 - it.index).toInt() else 0 }.sum()
    occ.add(v)
  }
  val b = (0..(127 * 8)).map { if (it !in occ && it - 1 in occ) it else Int.MAX_VALUE }.min()
  return Result("${occ.max()}", "$b")
}
