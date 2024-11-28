package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.resultFrom
import kotlin.math.max

/**
 * --- Day 16: The Floor Will Be Lava ---
 * https://adventofcode.com/2023/day/16
 */
class Day16 : Solver {
  override fun solve(lines: List<String>): Result {
    val grid = Grid(lines)
    grid.project(XY(-1, 0), Dir.E)
    return resultFrom(grid.visited.size, getMaxValue(lines))
  }

  private fun getMaxValue(lines: List<String>): Int {
    var maxV = 0
    for (x in lines[0].indices) {
      val g1 = Grid(lines)
      g1.project(XY(x, -1), Dir.S)
      maxV = max(maxV, g1.visited.size)

      val g2 = Grid(lines)
      g2.project(XY(x, g2.h), Dir.N)
      maxV = max(maxV, g2.visited.size)
    }
    for (y in lines.indices) {
      val g1 = Grid(lines)
      g1.project(XY(-1, y), Dir.E)
      maxV = max(maxV, g1.visited.size)

      val g2 = Grid(lines)
      g2.project(XY(g2.w, y), Dir.W)
      maxV = max(maxV, g2.visited.size)
    }
    return maxV
  }

  private fun Grid(data: List<String>) = Grid(data, data[0].length, data.size)

  private data class Grid(
    val data: List<String>,
    val w: Int,
    val h: Int
  ) {
    val visited = mutableSetOf<XY>()
    val done = mutableSetOf<Pair<XY, Dir>>()
  }

  private fun Grid.project(xy: XY, dir: Dir) {
    var pos = xy.add(dir.d)
    if (pos.x < 0 || pos.x >= w || pos.y < 0 || pos.y >= h) return

    val key = Pair(pos, dir)
    if (key in done) return
    done.add(key)

    while (at(pos) == '.') {
      visited.add(pos)
      pos = pos.add(dir.d)
      if (pos.x < 0 || pos.x >= w || pos.y < 0 || pos.y >= h) return
    }
    visited.add(pos)

    // Continue without change.
    if (dir in setOf(Dir.W, Dir.E) && at(pos) == '-') project(pos, dir)
    if (dir in setOf(Dir.N, Dir.S) && at(pos) == '|') project(pos, dir)

    // Split out.
    if (dir in setOf(Dir.W, Dir.E) && at(pos) == '|') {
      project(pos, Dir.N)
      project(pos, Dir.S)
    } else if (dir in setOf(Dir.N, Dir.S) && at(pos) == '-') {
      project(pos, Dir.W)
      project(pos, Dir.E)
    }
    // Deflect.
    if (at(pos) == '\\') {
      when (dir) {
        Dir.N -> project(pos, Dir.W)
        Dir.E -> project(pos, Dir.S)
        Dir.S -> project(pos, Dir.E)
        Dir.W -> project(pos, Dir.N)
      }
    } else if (at(pos) == '/') {
      when (dir) {
        Dir.N -> project(pos, Dir.E)
        Dir.E -> project(pos, Dir.N)
        Dir.S -> project(pos, Dir.W)
        Dir.W -> project(pos, Dir.S)
      }
    }
  }

  private fun Grid.at(xy: XY) = data[xy.y][xy.x]

  private enum class Dir(val d: XY) {
    N(XY(0, -1)),
    E(XY(1, 0)),
    S(XY(0, 1)),
    W(XY(-1, 0))
  }
}
