package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.addTo

/**
 * --- Day 24: Lobby Layout ---
 * https://adventofcode.com/2020/day/24
 */
class Day24 : Solver {
  // Ordered from shortest to longest key!
  private val directions = mapOf(
      "w" to XY(-2, 0),
      "e" to XY(2, 0),
      "ne" to XY(1, 1),
      "nw" to XY(-1, 1),
      "se" to XY(1, -1),
      "sw" to XY(-1, -1))

  override fun solve(lines: List<String>): Result {
    var tiles = mutableSetOf<XY>()
    for (line in lines) processLine(line).apply { if (this !in tiles) tiles.add(this) else tiles.remove(this) }
    val resultA = tiles.size

    for (i in 1..100) {
      val newTiles = mutableSetOf<XY>()
      val min = XY(tiles.map { it.x }.min()!!, tiles.map { it.y }.min()!!)
      val max = XY(tiles.map { it.x }.max()!!, tiles.map { it.y }.max()!!)
      for (x in min.x - 2..max.x + 2) {
        for (y in min.y - 2..max.y + 2) {
          val count = directions.values.count { XY(x + it.x, y + it.y) in tiles }
          val pos = XY(x, y)
          if ((pos in tiles && (count != 0 && count <= 2)) || (pos !in tiles && count == 2)) {
            newTiles.add(pos)
          }
        }
      }
      tiles = newTiles
    }
    return Result("$resultA", "${tiles.size}")
  }

  private fun processLine(line: String): XY {
    val pos = XY(0, 0)
    var idx = 0
    while (idx < line.length) {
      for (d in directions.keys) {  // LinkedHashMap keeps order of keys!
        if (line.startsWith(d, idx)) {
          pos.addTo(directions[d]!!)
          idx += d.length
          break;
        }
      }
    }
    return pos
  }
}
