package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.pow

/**
 * --- Day 24: Planet of Discord ---
 * https://adventofcode.com/2019/day/24
 */
class Day24 : Solver {
  override fun solve(lines: List<String>): Result {
    val game = Game()
    game.parse(lines)

    val map = game.runUntilRepeat()
    val resultA = game.calcBioDiversity()

    return Result("$resultA", "n/a")
  }

  private class Game {
    val history = mutableSetOf<Map<XY, Boolean>>()
    var state = mutableMapOf<XY, Boolean>()
    var minute = 0

    fun runUntilRepeat(): Map<XY, Boolean> {
      while (true) {
        step()
        if (state in history) {
          return state
        }
        history.add(state)
      }
    }

    fun step() {
      minute++
      val cntAdj =
          fun(x: Int, y: Int) = isBugN(x - 1, y) +
              isBugN(x + 1, y) +
              isBugN(x, y - 1) +
              isBugN(x, y + 1)

      val newState = mutableMapOf<XY, Boolean>()
      for (y in 0..4) {
        for (x in 0..4) {
          val numAdj = cntAdj(x, y)
          if (isBug(x, y)) {
            newState[XY(x, y)] = numAdj == 1
          } else {
            newState[XY(x, y)] = numAdj == 1 || numAdj == 2
          }
        }
      }
      state = newState
    }

    fun parse(lines: List<String>) {
      state.clear()
      for (y in 0..4) {
        for ((x, c) in lines[y].withIndex()) {
          state[XY(x, y)] = c == '#'
        }
      }
      history.add(state)
    }

    fun calcBioDiversity(): Long {
      var result = 0.0
      for (y in 0..4) {
        for (x in 0..4) {
          if (isBug(x, y)) {
            result += 2.toDouble().pow(y * 5 + x)
          }
        }
      }
      return result.toLong()
    }

    fun print() {
      for (y in 0..4) {
        for (x in 0..4) {
          print(if (isBug(x, y)) "#" else ".")
        }
        println()
      }
    }

    private fun isBugN(x: Int, y: Int) = if (isBug(x, y)) 1 else 0
    private fun isBug(x: Int, y: Int) = state[XY(x, y)] ?: false
  }

  private data class XY(val x: Int, val y: Int)
}