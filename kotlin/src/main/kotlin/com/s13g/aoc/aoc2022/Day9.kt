package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.addTo
import com.s13g.aoc.resultFrom
import kotlin.math.abs
import kotlin.math.sign

/**
 * --- Day 9: Rope Bridge ---
 * https://adventofcode.com/2022/day/9
 */
class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    val input =
      lines.map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }.toList()
    return resultFrom(simSnakeOfSize(2, input), simSnakeOfSize(10, input))
  }

  private fun simSnakeOfSize(size: Int, instrs: List<Pair<String, Int>>): Int {
    val snake = (1..size).map { XY(0, 0) }.toList()
    val tailVisited = mutableSetOf(snake.last().copy())
    val deltas = mapOf(
      "R" to XY(1, 0),
      "L" to XY(-1, 0),
      "U" to XY(0, -1),
      "D" to XY(0, 1)
    )
    for (instr in instrs) {
      for (step in 1..instr.second) {
        snake.first().addTo(deltas[instr.first]!!)
        for (i in 1 until snake.size) move(snake[i - 1], snake[i])
        tailVisited.add(snake.last().copy())
      }
    }
    return tailVisited.size
  }

  private fun move(hPos: XY, tPos: XY) {
    if (maxOf(abs(hPos.x - tPos.x), abs(hPos.y - tPos.y)) >= 2)
      tPos.addTo(
        XY((hPos.x - tPos.x).sign, (hPos.y - tPos.y).sign)
      )
  }
}
