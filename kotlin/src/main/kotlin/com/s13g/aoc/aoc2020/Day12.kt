package com.s13g.aoc.aoc2020

import com.s13g.aoc.*

/**
 * --- Day 12: Rain Risk ---
 * https://adventofcode.com/2020/day/12
 */
class Day12 : Solver {
  private val dirVec = hashMapOf('N' to XY(0, 1), 'S' to XY(0, -1), 'W' to XY(-1, 0), 'E' to XY(1, 0))
  private var dirs = listOf('N', 'E', 'S', 'W')
  override fun solve(lines: List<String>): Result {
    val input = lines.map { Pair(it[0], it.substring(1).toInt()) }
    return Result("${partA(input).manhattan()}", "${partB(input).manhattan()}")
  }

  private fun partA(input: List<Pair<Char, Int>>): XY {
    val pos = XY(0, 0)
    var facing = 'E'
    for (foo in input) {
      when (foo.first) {
        in dirVec -> for (n in 0 until foo.second) pos.addTo(dirVec[foo.first]!!)
        'F' -> for (n in 0 until foo.second) pos.addTo(dirVec[facing]!!)
        'L' -> facing = dirs[(dirs.indexOf(facing) - (foo.second / 90) + 360) % dirs.size]
        'R' -> facing = dirs[(dirs.indexOf(facing) + (foo.second / 90)) % dirs.size]
      }
    }
    return pos
  }

  private fun partB(input: List<Pair<Char, Int>>): XY {
    val pos = XY(0, 0)
    var waypoint = XY(10, 1)
    for (foo in input) {
      when (foo.first) {
        in dirVec -> for (n in 0 until foo.second) waypoint.addTo(dirVec[foo.first]!!)
        'F' -> for (n in 0 until foo.second) pos.addTo(waypoint)
        'L' -> waypoint = waypoint.rotate90(foo.second / 90, true)
        'R' -> waypoint = waypoint.rotate90(foo.second / 90, false)
      }
    }
    return pos
  }
}
