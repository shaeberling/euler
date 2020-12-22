package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.util.*

/**
 * --- Day 22: Crab Combat ---
 * https://adventofcode.com/2020/day/22
 */
class Day22 : Solver {
  override fun solve(lines: List<String>): Result {
    val playersA = parse(lines)
    // Make copy for Part B where we have to start over.
    val playersB = playersA.map { LinkedList(it) }

    // The game is going while none of them ran out of cards.
    while (playersA[0].isNotEmpty() && playersA[1].isNotEmpty()) {
      val top = Pair(playersA[0].removeAt(0), playersA[1].removeAt(0))
      if (top.first > top.second) {
        playersA[0].add(top.first)
        playersA[0].add(top.second)
      } else {
        playersA[1].add(top.second)
        playersA[1].add(top.first)
      }
    }
    val winnerA = if (playersA.isNotEmpty()) 0 else 1
    val resultA = playersA[winnerA].reversed().withIndex().map { (it.index + 1) * it.value }.sum()

    // Part 2
    val winnerB = recursiveCombat(playersB, 1)
    val resultB = playersB[winnerB].reversed().withIndex().map { (it.index + 1) * it.value }.sum()

    return Result("$resultA", "$resultB")
  }

  private fun recursiveCombat(players: List<MutableList<Long>>, gameNo: Int): Int {
    val history = Array(2) { mutableSetOf<Int>() }

    var roundNo = 1
    var roundWinner = -42
    while (players[0].isNotEmpty() && players[1].isNotEmpty()) {
      // Player 0 wins when history repeats.
      if (players[0].hashCode() in history[0] || players[1].hashCode() in history[1]) return 0
      history[0].add(players[0].hashCode())
      history[1].add(players[1].hashCode())
      // Top cards.
      val top = Pair(players[0].removeAt(0), players[1].removeAt(0))

      roundWinner = if (players[0].size >= top.first && players[1].size >= top.second) {
        // Play sub-game, recursive combat! Create copies so that our list is not changed.
        val subListA = LinkedList(players[0].subList(0, top.first.toInt()))
        val subListB = LinkedList(players[1].subList(0, top.second.toInt()))
        recursiveCombat(listOf(subListA, subListB), gameNo + 1)
      } else if (top.first > top.second) 0 else 1
      // Add cards to the round winners deck in the right order (winner's first).
      players[roundWinner].add(if (roundWinner == 0) top.first else top.second)
      players[roundWinner].add(if (roundWinner == 0) top.second else top.first)
      roundNo++
    }
    return roundWinner
  }

  private fun parse(lines: List<String>): List<MutableList<Long>> {
    val players = mutableListOf<LinkedList<Long>>(LinkedList(), LinkedList())
    var activePlayerParsing = 0
    for (line in lines) {
      if (line.startsWith("Player 1:") || line.isBlank()) continue
      if (line.startsWith("Player 2:")) activePlayerParsing = 1
      else players[activePlayerParsing].add(line.toLong())
    }
    return players
  }
}