package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom
import kotlin.math.max
import kotlin.math.min

/**
 * --- Day 14: Regolith Reservoir ---
 * https://adventofcode.com/2022/day/14
 */
class Day14 : Solver {
  override fun solve(lines: List<String>): Result {
    val map = drawMap(lines.map { parseTrace(it) })
    return resultFrom(
      solvePart(map.toMutableMap(), false),
      solvePart(map.toMutableMap(), true)
    )
  }

  private fun drawMap(traces: List<List<XY>>): Map<XY, Int> {
    val map = mutableMapOf<XY, Int>()
    map[XY(500, 0)] = 2

    for (trace in traces) {
      var prev = trace.first()
      trace.subList(1, trace.size).forEach {
        for (y in min(prev.y, it.y)..max(prev.y, it.y)) {
          for (x in min(prev.x, it.x)..max(prev.x, it.x)) {
            map[XY(x, y)] = 1
          }
        }
        prev = it
      }
    }
    return map
  }

  private fun solvePart(map: MutableMap<XY, Int>, partB: Boolean): Int {
    val minX = map.keys.minOf { it.x }
    val maxX = map.keys.maxOf { it.x }
    val minY = map.keys.minOf { it.y }
    val maxY = map.keys.maxOf { it.y }
    val floorY = maxY + 2

    fun isFree(pos: XY): Boolean = (!partB || pos.y != floorY) && pos !in map

    // Simulate sand falling
    while (true) {
      var falling = true
      var newSand = XY(500, 0)
      while (falling) {
        falling = false

        val nextDown = XY(newSand.x, newSand.y + 1)
        if (isFree(nextDown)) {
          newSand = nextDown
          falling = true
        } else {
          // Left down
          val leftDown = XY(newSand.x - 1, newSand.y + 1)
          if (isFree(leftDown)) {
            newSand = leftDown
            falling = true
          } else {
            val rightDown = XY(newSand.x + 1, newSand.y + 1)
            if (isFree(rightDown)) {
              newSand = rightDown
              falling = true
            }
          }
        }
        if (!partB && newSand.y >= maxY) {
          printMap(map, minX, maxX, minY, maxY)
          return map.values.count { it == 3 }
        }
      }

      if (partB && newSand == XY(500, 0)) {
        printMap(map, minX, maxX, minY, maxY)
        return map.values.count { it == 3 } + 1
      }
      map[newSand] = 3
    }
  }

  private fun printMap(
    map: Map<XY, Int>,
    minX: Int,
    maxX: Int,
    minY: Int,
    maxY: Int
  ) {
    for (y in minY..maxY) {
      var line = ""
      for (x in minX..maxX) {
        line += if (map[XY(x, y)] == 1) "#"
        else if (map[XY(x, y)] == 2) "+"
        else if (map[XY(x, y)] == 3) "o"
        else "."
      }
      println(line)
    }
  }

  private fun parseTrace(line: String): List<XY> {
    return line.split(" -> ").map { it.split(",") }
      .map { XY(it[0].toInt(), it[1].toInt()) }
  }
}