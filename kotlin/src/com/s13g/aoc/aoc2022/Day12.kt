package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom

/**
 * --- Day 12: Hill Climbing Algorithm ---
 * https://adventofcode.com/2022/day/12
 */
class Day12 : Solver {
  override fun solve(lines: List<String>): Result {
    return resultFrom(solve(lines, false), solve(lines, true))
  }

  fun solve(lines: List<String>, partB: Boolean): Int {
    val (start, end) = getStartEndPos(lines)
    val visited = mutableSetOf<XY>()
    var active = if (!partB) setOf(start) else setOf(end)
    var length = 0
    while ((!partB && !active.contains(end)) ||
      (partB && active.none { lines[it.y][it.x] == 'a' })
    ) {
      val old = active.toSet()
      active = active.filter { it !in visited }
        .flatMap { spawn(it, lines, partB, visited) }.toSet()
      visited.addAll(old)
      length++
    }
    return length
  }

  private fun spawn(
    from: XY,
    lines: List<String>,
    partB: Boolean,
    visited: Set<XY>
  ): List<XY> {
    val currentElevation = elevation(from, lines)
    val dirOptions = listOf(
      XY(from.x - 1, from.y), XY(from.x + 1, from.y),
      XY(from.x, from.y - 1), XY(from.x, from.y + 1)
    ).filter { it !in visited }
    val result = mutableListOf<XY>()
    for (dir in dirOptions) {
      if (dir.x in 0 until lines[0].length && dir.y in lines.indices) {
        val newElevation = elevation(dir, lines)
        if (!partB) {
          if (newElevation in 0..currentElevation + 1) result.add(dir)
        } else {
          if (newElevation in currentElevation - 1..25) result.add(dir)
        }
      }
    }
    return result
  }

  private fun elevation(pos: XY, lines: List<String>): Int {
    val ch = lines[pos.y][pos.x]
    if (ch == 'S') return 0
    if (ch == 'E') return 'z'.code - 'a'.code
    return ch.code - 'a'.code
  }

  private fun getStartEndPos(lines: List<String>): Pair<XY, XY> {
    var start = XY(0, 0)
    var end = XY(0, 0)
    for ((y, line) in lines.withIndex()) {
      for ((x, ch) in line.withIndex()) {
        if (ch == 'S') start = XY(x, y)
        if (ch == 'E') end = XY(x, y)
      }
    }
    return Pair(start, end)
  }
}