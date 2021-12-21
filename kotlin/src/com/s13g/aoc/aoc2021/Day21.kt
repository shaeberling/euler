package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.max
import kotlin.math.min

/**
 * --- Day 21: Dirac Dice ---
 * https://adventofcode.com/2021/day/21
 */
class Day21 : Solver {
  override fun solve(lines: List<String>): Result {
    var stateA = GameState(lines[0].substring(28).toInt(), lines[1].substring(28).toInt(), 0, 0, 0)
    val stateB = stateA.copy()

    val dieA = DiePartA(100)
    while (max(stateA.score1, stateA.score2) < 1000) {
      var newPos = stateA.getPos() + dieA.roll() + dieA.roll() + dieA.roll()
      while (newPos > 10) newPos -= 10
      val newScore = stateA.getScore() + newPos
      stateA = newState(newPos, newScore, stateA)
    }
    val partA = min(stateA.score1, stateA.score2) * dieA.timesRolled

    val wins = countWinsForPlayers(stateB)
    val partB = max(wins.first, wins.second)
    return Result("$partA", "$partB")
  }

  // DP aka memoization of all the branches of the tree (most are duplicates).
  private var cache = mutableMapOf<GameState, Pair<Long, Long>>()
  private fun countWinsForPlayers(state: GameState): Pair<Long, Long> {
    if (state.score1 >= 21) return Pair(1, 0)
    if (state.score2 >= 21) return Pair(0, 1)
    if (cache.containsKey(state)) return cache[state]!!

    var wins1 = 0L
    var wins2 = 0L
    for (dice1 in 1..3) {
      for (dice2 in 1..3) {
        for (dice3 in 1..3) {
          var newPos = state.getPos() + dice1 + dice2 + dice3
          while (newPos > 10) newPos -= 10
          val newScore = state.getScore() + newPos
          val newGameState = newState(newPos, newScore, state)
          val wins = countWinsForPlayers(newGameState)
          wins1 += wins.first
          wins2 += wins.second
        }
      }
    }
    val result = Pair(wins1, wins2)
    cache[state] = result
    return result
  }

  private class DiePartA(val sides: Int) {
    var value = 0
    var timesRolled = 0

    fun roll(): Int {
      timesRolled++
      return (value++ % sides) + 1
    }
  }

  private data class GameState(val pos1: Int, val pos2: Int, val score1: Int, val score2: Int, val turn: Int) {
    fun getPos() = if (turn == 0) pos1 else pos2
    fun getScore() = if (turn == 0) score1 else score2
  }

  private fun newState(pos: Int, score: Int, prev: GameState): GameState {
    return if (prev.turn == 0) GameState(pos, prev.pos2, score, prev.score2, 1)
    else GameState(prev.pos1, pos, prev.score1, score, 0)
  }
}