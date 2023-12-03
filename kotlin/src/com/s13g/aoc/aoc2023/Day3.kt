package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom

/**
 * --- Day 3: Gear Ratios ---
 * https://adventofcode.com/2023/day/3
 */
class Day3 : Solver {
  override fun solve(lines: List<String>): Result {
    val schematic = Schematic(lines)
    val parts = extractParts(schematic)
    val gearsToNums = mapGearsToNums(parts)

    val answerB = gearsToNums
      .filter { it.value.size == 2 }
      .map { it.value[0].num * it.value[1].num }
      .sum()
    return resultFrom(parts.sumOf { it.num }, answerB)
  }

  private fun mapGearsToNums(nums: List<Part>): Map<XY, List<Part>> {
    val result = mutableMapOf<XY, MutableList<Part>>()
    for (num in nums) {
      for (gear in num.gearsAt) {
        result.putIfAbsent(gear, mutableListOf())
        result[gear]!!.add(num)
      }
    }
    return result
  }

  private fun extractParts(schematic: Schematic): MutableList<Part> {
    val result = mutableListOf<Part>()

    class TempResult {
      var buf = ""
      var isPart = false
      var gearLocations = mutableSetOf<XY>()
      fun addToResult() { result.add(Part(buf.toInt(), gearLocations)) }
    }
    var tr: TempResult

    for ((y, line) in schematic.lines.withIndex()) {
      tr = TempResult()
      for ((x, ch) in line.withIndex()) {
        if (ch.isDigit()) {
          tr.buf += ch
          if (schematic.isSymbolSurrounding(x, y)) tr.isPart = true
          tr.gearLocations.addAll(schematic.getSurroundingGears(x, y))
        }
        if (!ch.isDigit() || x == line.indices.last) {
          if (tr.buf.isNotBlank() && tr.isPart) {
           tr.addToResult()
          }
          tr = TempResult()
        }
      }
    }
    return result
  }

  data class Part(val num: Int, val gearsAt: Set<XY>)

  data class Schematic(val lines: List<String>)

  private fun Schematic.isSymbolSurrounding(x: Int, y: Int): Boolean {
    for (i in x - 1..x + 1) {
      for (j in y - 1..y + 1) {
        if (isSymbolAt(i, j)) return true
      }
    }
    return false
  }

  private fun Schematic.isSymbolAt(x: Int, y: Int): Boolean {
    if (y < 0 || y >= lines.size || x < 0 || x >= lines[y].length) return false
    val ch = lines[y][x]
    return !ch.isDigit() && ch != '.'
  }

  private fun Schematic.getSurroundingGears(x: Int, y: Int): Set<XY> {
    val result = mutableSetOf<XY>()
    for (i in x - 1..x + 1) {
      for (j in y - 1..y + 1) {
        if (isSymbolGear(i, j)) result.add(XY(i, j))
      }
    }
    return result
  }

  private fun Schematic.isSymbolGear(x: Int, y: Int): Boolean {
    if (y < 0 || y >= lines.size || x < 0 || x >= lines[y].length) return false
    return lines[y][x] == '*'
  }
}