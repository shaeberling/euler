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

  private fun mapGearsToNums(parts: List<Part>): Map<XY, List<Part>> {
    val result = mutableMapOf<XY, MutableList<Part>>()
    for (part in parts) {
      for (sym in part.syms.filter { it.value == '*' }) {
        result.putIfAbsent(sym.key, mutableListOf())
        result[sym.key]!!.add(part)
      }
    }
    return result
  }

  private fun extractParts(schematic: Schematic): MutableList<Part> {
    val result = mutableListOf<Part>()

    class TempResult {
      var buf = ""
      var symbols = mutableMapOf<XY, Char>()
      fun addToResult() { result.add(Part(buf.toInt(), symbols)) }
      fun isPart() = symbols.isNotEmpty()
    }
    var tr: TempResult

    for ((y, line) in schematic.lines.withIndex()) {
      tr = TempResult()
      for ((x, ch) in line.withIndex()) {
        if (ch.isDigit()) {
          tr.buf += ch
          tr.symbols.putAll(schematic.getSurroundingSymbols(x, y))
        }
        if (!ch.isDigit() || x == line.indices.last) {
          if (tr.buf.isNotBlank() && tr.isPart()) {
            tr.addToResult()
          }
          tr = TempResult()
        }
      }
    }
    return result
  }

  data class Part(val num: Int, val syms: Map<XY, Char>)

  data class Schematic(val lines: List<String>)

  private fun Schematic.getSurroundingSymbols(x: Int, y: Int): Map<XY, Char> {
    val result = mutableMapOf<XY, Char>()
    for (yy in (y - 1).coerceAtLeast(0)..(y + 1).coerceAtMost(lines.size - 1)) {
      for (xx in (x - 1).coerceAtLeast(0)..(x + 1).coerceAtMost(lines[yy].length - 1)) {
        val ch = lines[yy][xx]
        if (!ch.isDigit() && ch != '.') {
          result[XY(xx, yy)] = ch
        }
      }
    }
    return result
  }
}