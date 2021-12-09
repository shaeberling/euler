package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul

/**
 * --- Day 9: Smoke Basin ---
 * https://adventofcode.com/2021/day/9
 */
class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = PuzzleInput(lines)

    var partA = 0
    for (x in 0 until input.width) {
      for (y in 0 until input.height) {
        val pos = XY(x, y)
        val elevation = input.height(pos)
        val adjacent = pos.adjacent().map { input.height(it) }.toSet()
        if (elevation < adjacent.min()!!) {
          partA += elevation + 1
        }
      }
    }

    // Calculate which positions flow directly into which other.
    val flownInto = mutableMapOf<XY, MutableSet<XY>>()
    for (x in 0 until input.width) {
      for (y in 0 until input.height) {
        flownInto[XY(x, y)] = mutableSetOf()
      }
    }
    for (x in 0 until input.width) {
      for (y in 0 until input.height) {
        val pos = XY(x, y)
        val elevation = input.height(pos)
        val checkCandidate = { p: XY ->
          val neighbor = input.height(p)
          if (neighbor in (elevation + 1)..8) flownInto[pos]!!.add(p)
        }
        pos.adjacent().forEach { checkCandidate(it) }
      }
    }

    // Group basins together by recursively combining flows.
    val flownIntoResult = flownInto.keys.associateWith { allThatFlowInto(it, flownInto) }
    // Take top 3 sizes and multiply them together.
    val partB = flownIntoResult.map { it.value.size }.sortedDescending().take(3).mul()
    return Result("$partA", "$partB")
  }

  /** Recursively get a complete list of all that flows into 'pos'. */
  private fun allThatFlowInto(pos: XY, flownInto: Map<XY, MutableSet<XY>>): MutableSet<XY> {
    // First add itself, then recursively everything else that flows into it.
    val result = mutableSetOf(pos)
    for (flowsInto in flownInto[pos]!!) {
      result.addAll(allThatFlowInto(flowsInto, flownInto))
    }
    return result
  }
}

private data class XY(val x: Int, val y: Int)

private fun XY.adjacent() = setOf(XY(x - 1, y), XY(x + 1, y), XY(x, y + 1), XY(x, y - 1))

private class PuzzleInput(lines_: List<String>) {
  val lines = lines_
  val width = lines[0].length
  val height = lines.size

  fun height(pos: XY): Int {
    if (pos.x < 0 || pos.x >= width || pos.y < 0 || pos.y >= height) return Int.MAX_VALUE
    return lines[pos.y][pos.x].toString().toInt()
  }
}