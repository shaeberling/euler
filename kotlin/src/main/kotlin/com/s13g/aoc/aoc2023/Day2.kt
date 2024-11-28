package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.max
import com.s13g.aoc.mul
import com.s13g.aoc.resultFrom

/**
 * --- Day 2: Cube Conundrum ---
 * https://adventofcode.com/2023/day/2
 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    val solutions = lines.mapIndexed { idx, line -> calculate(line, idx + 1) }
    return resultFrom(
      solutions.sumOf { it.first },
      solutions.sumOf { it.second })
  }

  private fun calculate(line: String, game: Int): Pair<Int, Int> {
    var partA = game
    val maxN = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
    val sets = line.split(": ")[1].split("; ").map { it.split(", ") }
    for (set in sets) {
      for (pair in set) {
        val split = pair.split(" ")
        val num = split[0].toInt()
        val color = split[1]
        maxN[color] = max(maxN[color]!!, num)
        if (color == "red" && num > 12) partA = 0
        if (color == "green" && num > 13) partA = 0
        if (color == "blue" && num > 14) partA = 0
      }
    }
    return Pair(partA, maxN.values.toList().mul())
  }
}