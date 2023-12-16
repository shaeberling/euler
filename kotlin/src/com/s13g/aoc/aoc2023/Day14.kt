package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom

/**
 * --- Day 14: Parabolic Reflector Dish ---
 * https://adventofcode.com/2023/day/14
 */
class Day14 : Solver {
  override fun solve(lines: List<String>): Result {
    val grid = Grid(lines)
    return resultFrom(moveRocks(grid), spin(grid))
  }

  private fun spin(g: Grid): Int {
    val targetSpinNum = 1_000_000_000
    val history = mutableMapOf<Int, Int>()
    var cycle = 1
    while (cycle <= targetSpinNum) {
      Dir.values().forEach { dir -> moveRocks(g, dir) }
      // Find Loop
      if (g.hashCode() in history) {
        val loopLength = cycle - history[g.hashCode()]!!
        val numLoops = (targetSpinNum - cycle) / loopLength
        cycle += (numLoops * loopLength)
      }
      history[g.hashCode()] = cycle
      cycle++
    }
    return g.load()
  }


  private fun moveRocks(g: Grid, dir: Dir = Dir.N): Int {
    var rocksMoved: Int
    do {
      rocksMoved = 0
      for (y in g.yRange(dir)) {
        for (x in g.xRange(dir)) {
          if (g[x, y] == 'O' && g[x + dir.d.x, y + dir.d.y] == '.') {
            g[x + dir.d.x, y + dir.d.y] = 'O'
            g[x, y] = '.'
            rocksMoved++
          }
        }
      }
    } while (rocksMoved > 0)
    return g.load()
  }

  private fun Grid(lines: List<String>): Grid {
    val w = lines[0].length
    val h = lines.size
    val data = MutableList(w * h) { '.' }
    for ((y, line) in lines.withIndex()) {
      for ((x, ch) in line.withIndex()) {
        data[y * w + x] = ch
      }
    }
    return Grid(data, h, w)
  }

  private data class Grid(val data: MutableList<Char>, val h: Int, val w: Int)

  private fun Grid.xRange(dir: Dir) = when (dir) {
    Dir.N, Dir.S -> 0 until w
    Dir.W -> 1 until w
    Dir.E -> w - 2 downTo 0
  }

  private fun Grid.yRange(dir: Dir) = when (dir) {
    Dir.N -> 1 until h
    Dir.W, Dir.E -> 0 until h
    Dir.S -> h - 2 downTo 0
  }

  private operator fun Grid.get(x: Int, y: Int) = data[idx(x, y)]
  private operator fun Grid.set(x: Int, y: Int, ch: Char) {
    data[idx(x, y)] = ch
  }

  private fun Grid.idx(x: Int, y: Int) = y * w + x

  private fun Grid.load() =
    (0 until h).sumOf { x ->
      (0 until w).filter { y -> this[x, y] == 'O' }.sumOf { h - it }
    }

  private enum class Dir(val d: XY) {
    N(XY(0, -1)), W(XY(-1, 0)), S(XY(0, 1)), E(XY(1, 0))
  }


}

