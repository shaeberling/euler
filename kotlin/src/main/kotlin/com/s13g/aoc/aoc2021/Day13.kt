package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 13: Transparent Origami ---
 * https://adventofcode.com/2021/day/13
 */
class Day13 : Solver {
  override fun solve(lines: List<String>): Result {
    val positions = lines.filter { it.contains(',') }
      .map { it.split(',') }
      .map { XY(it[0].toInt(), it[1].toInt()) }
      .toMutableSet()
    val folds = lines.filter { it.startsWith("fold") }
      .map { it.split('=') }
      .map { Fold(it[0].substring(11), it[1].toInt()) }

    var width = positions.map { it.x }.max()!!
    var height = positions.map { it.y }.max()!!

    var partA = 0
    for ((i, fold) in folds.withIndex()) {
      for (pos in positions.toSet()) {
        if ((fold.axis == "y" && pos.y > fold.v) || (fold.axis == "x" && pos.x > fold.v)) {
          positions.remove(pos)
          if (fold.axis == "y") positions.add(XY(pos.x, fold.v - (pos.y - fold.v)))
          if (fold.axis == "x") positions.add(XY(fold.v - (pos.x - fold.v), pos.y))
        }
      }
      if (i == 0) partA = positions.size
      width = positions.map { it.x }.max()!!
      height = positions.map { it.y }.max()!!
    }

    val partB = printGrid(positions, width, height)
    return Result("$partA", "\n$partB")
  }

  private data class XY(val x: Int, val y: Int)
  private data class Fold(val axis: String, val v: Int)

  private fun printGrid(pos: Set<XY>, width: Int, height: Int): String {
    var result = ""
    for (y in 0..height) {
      for (x in 0..width) {
        result += if (pos.contains(XY(x, y))) '#' else '.'
      }
      result += '\n'
    }
    return result
  }
}


