package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 6: Probably a Fire Hazard ---
 * https://adventofcode.com/2015/day/6
 */
class Day6 : Solver {
  private val SIZE = 1000
  private val REGEX = """^([\s\w]+) (\d+),(\d+) through (\d+),(\d+)$""".toRegex()
  override fun solve(lines: List<String>): Result {
    val gridA = Array(SIZE * SIZE) { false }
    val gridB = Array(SIZE * SIZE) { 0 }
    for (line in lines) {
      val (action, x1s, y1s, x2s, y2s) = REGEX.find(line)!!.destructured
      val (x1, y1, x2, y2) = listOf(x1s.toInt(), y1s.toInt(), x2s.toInt(), y2s.toInt())
      when (action) {
        "turn on" -> for (x in x1..x2) for (y in y1..y2) gridA[index(x, y)] = true
        "turn off" -> for (x in x1..x2) for (y in y1..y2) gridA[index(x, y)] = false
        "toggle" -> for (x in x1..x2) for (y in y1..y2) gridA[index(x, y)] = !gridA[index(x, y)]!!
      }
      when (action) {
        "turn on" -> for (x in x1..x2) for (y in y1..y2) gridB[index(x, y)]++
        "turn off" -> for (x in x1..x2) for (y in y1..y2) gridB[index(x, y)] = maxOf(gridB[index(x, y)] - 1, 0)
        "toggle" -> for (x in x1..x2) for (y in y1..y2) gridB[index(x, y)] += 2
      }
    }
    return Result("${gridA.count { it }}", "${gridB.sum()}")
  }

  private fun index(x: Int, y: Int) = y * SIZE + x
}