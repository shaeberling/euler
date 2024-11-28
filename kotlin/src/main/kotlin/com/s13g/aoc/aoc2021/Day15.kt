package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 15: Chiton ---
 * https://adventofcode.com/2021/day/15
 */
class Day15 : Solver {
  override fun solve(lines: List<String>): Result {
    val width = lines[0].length
    val height = lines.size
    val data = IntArray(lines.size * lines[0].length)

    for (y in lines.indices) {
      for (x in lines[y].indices) {
        data[width * y + x] = lines[y][x].toString().toInt()
      }
    }

    val partA = Board(data, width, height).calc()
    val partB = Board(expandForPartB(data, width, height), width * 5, height * 5).calc()
    return Result("$partA", "$partB")
  }

  private fun expandForPartB(data: IntArray, width: Int, height: Int): IntArray {
    val newData = IntArray(width * height * 5 * 5)
    var idx = 0

    for (y in 0 until height) {
      for (i in 0..4) {
        for (x in 0 until width) newData[idx++] = roundIt(data[y * width + x] + i)
      }
    }

    val newWidth = width * 5
    for (i in 1..4) {
      for (y in 0 until height) {
        for (x in 0 until newWidth) newData[idx++] = roundIt(newData[y * newWidth + x] + i)
      }
    }
    return newData
  }

  private fun roundIt(num: Int) = if (num >= 10) num - 9 else num

  private class Board(val data: IntArray, val width: Int, val height: Int) {
    // Index (width/height) and cheapest cost.
    val visited = mutableMapOf<Int, Int>()
    var activeRoutes = mutableSetOf<Route>()
    val goal = idxAt(width - 1, height - 1)

    fun calc(): Int {
      activeRoutes.add(Route(0, 0))
      visited[0] = 0

      while (activeRoutes.count { it.lastIdx != goal } > 0) {
        for (route in activeRoutes.toList()) {
          if (route.lastIdx == goal) continue
          activeRoutes.remove(route)
          if (costAt(route.lastIdx) < route.cost) continue

          val end = route.lastIdx
          val options = findNextOptions(end)
          for (opt in options) {
            val totalCost = data[opt] + route.cost
            if (costAt(opt) > totalCost) {
              activeRoutes.add(Route(opt, totalCost))
              visited[opt] = totalCost
            }
          }
        }
      }
      return activeRoutes.sortedBy { it.cost }.first().cost
    }

    fun costAt(pos: Int) = if (visited.containsKey(pos)) visited[pos]!! else Int.MAX_VALUE

    fun idxAt(x: Int, y: Int) = y * width + x

    fun findNextOptions(pos: Int): Set<Int> {
      val x = pos % width
      val y = pos / width

      val result = mutableSetOf<Int>()
      if (x > 0) result.add(idxAt(x - 1, y))
      if (x < width - 1) result.add(idxAt(x + 1, y))
      if (y > 0) result.add(idxAt(x, y - 1))
      if (y < height - 1) result.add(idxAt(x, y + 1))
      return result
    }

  }

  private data class Route(val lastIdx: Int, val cost: Int);
}