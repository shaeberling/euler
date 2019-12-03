package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/** https://adventofcode.com/2019/day/3 */
class Day3 : Solver {
  override fun solve(lines: List<String>): Result {
    val dirs1 = traceWire(lines[0].split(","))
    val dirs2 = traceWire(lines[1].split(","))
    val intersections = dirs1.intersect(dirs2)

    val shortest = intersections.map { i -> i.manhattan() }.min()
    val combinedDistance = intersections.map { i ->
      dirs1.indexOf(i) + dirs2.indexOf(i)
    }.min()
    return Result("$shortest", "${combinedDistance!! + 2}")
  }

  private fun traceWire(dirs: List<String>): List<XY> {
    val result = mutableListOf<XY>()
    var pos = XY(0, 0)
    for (dir in dirs) {
      val dirVec = vecForDir(dir[0])
      val amount = dir.substring(1).toInt()
      for (i in 1..amount) {
        val newPos = pos.add(dirVec)
        result.add(newPos)
        pos = newPos
      }
    }
    return result
  }

  private fun vecForDir(d: Char) = when (d) {
    'R' -> XY(1, 0)
    'L' -> XY(-1, 0)
    'U' -> XY(0, -1)
    'D' -> XY(0, 1)
    else -> throw Exception("Invalid dir character")
  }
}

private data class XY(val x: Int, val y: Int) {
  fun add(xy: XY): XY {
    return XY(x + xy.x, y + xy.y)
  }

  fun manhattan(): Int {
    return abs(x) + abs(y)
  }
}