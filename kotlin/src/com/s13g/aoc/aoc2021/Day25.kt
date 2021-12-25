package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

class Day25 : Solver {
  override fun solve(lines: List<String>): Result {
    val width = lines[0].length
    val height = lines.size
    val world = mutableMapOf<XY, Boolean>()

    for (y in lines.indices) {
      for (x in lines[y].indices) {
        if (lines[y][x] != '.') {
          world[XY(x, y)] = lines[y][x] == '>'
        }
      }
    }

    var steps = 0
    do {
      val eastMoved = move(true, world, width, height)
      val southMoved = move(false, world, width, height)
      steps++
    } while (eastMoved || southMoved)
    return Result("$steps", "Happy Holidays")
  }

  private fun move(
    east: Boolean,
    world: MutableMap<XY, Boolean>,
    width: Int,
    height: Int
  ): Boolean {
    val replacements = mutableMapOf<XY, XY>()
    for (pos in world.keys) {
      if (world[pos]!! != east) continue
      val newPos = if (east) XY((pos.x + 1) % width, pos.y)
      else XY(pos.x, (pos.y + 1) % height)
      if (!world.containsKey(newPos)) {
        replacements[pos] = newPos
      }
    }
    for (pos in replacements.keys) {
      world.remove(pos)
      world[replacements[pos]!!] = east
    }
    return replacements.isNotEmpty()
  }

  private data class XY(val x: Int, val y: Int)
}