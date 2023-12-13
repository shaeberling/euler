package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 13: Point of Incidence ---
 * https://adventofcode.com/2023/day/13
 */
class Day13 : Solver {
  override fun solve(lines: List<String>): Result {
    val inputs = mutableListOf(mutableListOf<String>())
    for (line in lines) {
      if (line.isBlank()) inputs.add(mutableListOf())
      else inputs.last().add(line)
    }
    return resultFrom(solve(inputs), solve(inputs, true))
  }

  private fun solve(inputs: List<List<String>>, part2: Boolean = false) =
    inputs.sumOf { getMirrorRow(it, part2) * 100 } +
        inputs.sumOf { getMirrorRow(rotate90(it), part2) }

  private fun getMirrorRow(input: List<String>, part2: Boolean): Int {
    for (i in 0..input.lastIndex - 1) {
      if (isValidMirrorPoint(i, input, part2)) return i + 1
    }
    return 0
  }

  private fun isValidMirrorPoint(
    p: Int,
    input: List<String>,
    part2: Boolean
  ): Boolean {
    var r0 = p
    var r1 = p + 1

    var diffs = 0
    while (r0 >= 0 && r1 <= input.lastIndex) {
      diffs += input[r0].numDiff(input[r1])
      r0--
      r1++
    }
    return if (!part2) diffs == 0 else diffs == 1
  }

  private fun rotate90(input: List<String>) =
    input[0].indices.map { col -> input.map { it[col] }.joinToString("") }

  private fun String.numDiff(other: String) =
    mapIndexed { i, ch -> ch != other[i] }.count { it }
}