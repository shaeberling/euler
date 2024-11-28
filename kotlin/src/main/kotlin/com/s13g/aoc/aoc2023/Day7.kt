package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 7: Camel Cards ---
 * https://adventofcode.com/2023/day/7
 */
class Day7 : Solver {
  override fun solve(lines: List<String>): Result {
    return resultFrom(
      CardScorer(false).cardScore(lines),
      CardScorer(true).cardScore(lines)
    )
  }


  private class CardScorer(val partB: Boolean) {
    fun cardScore(lines: List<String>): Int {
      val parsedInput =
        lines.map { it.split(" ") }
          .map { Pair(it[0], it[1].toInt()) }
          .sortedWith { h1, h2 -> compareHands(h1.first, h2.first) }
          .toList()
      return parsedInput.withIndex()
        .sumOf { (idx, hand) -> hand.second * (idx + 1) }
    }

    private fun compareHands(h1: String, h2: String): Int {
      val score1 = if (partB) handScoreB(h1) else handScore(h1)
      val score2 = if (partB) handScoreB(h2) else handScore(h2)

      if (score1 > score2) return 1
      else if (score1 < score2) return -1
      else {
        // Compare cards next if scores are the same.
        for (i in h1.indices) {
          if (h1[i].cardScore(partB) > h2[i].cardScore(partB)) return 1
          if (h1[i].cardScore(partB) < h2[i].cardScore(partB)) return -1
        }
      }
      error("Two hands are the same!")
    }

    private fun handScoreB(hand: String): Int {
      if (hand == "JJJJJ") return handScore("AAAAA")
      // We assume that it's most beneficial for all Jokers to always
      // be the same value to maximize outcome.

      // Determine joker card by looking at the most valuable card.
      val bestCard = hand.cardCounts().asIterable()
        .filter { it.key != 'J' }
        .sortedWith { c1, c2 ->
          // Choose card of highest frequency first.
          if (c1.value < c2.value) 1
          else if (c1.value > c2.value) -1
          else {
            // If the same (2 pairs), choose highest scoring card.
            if (c1.key.cardScore(partB) < c2.key.cardScore(partB)) 1
            else -1
          }
        }
      return handScore(hand.replace('J', bestCard.first().key))

    }

    private fun handScore(hand: String): Int {
      val counts = hand.cardCounts()
      if (counts.values.contains(5)) return 6
      if (counts.values.contains(4)) return 5
      if (counts.values.contains(3)) {
        if (counts.values.contains(2)) return 4 // full house
        else return 3
      }
      if (counts.values.count { it == 2 } == 2) return 2
      if (counts.values.count { it == 2 } == 1) return 1
      return 0 // All cards are different.
    }

    private fun Char.cardScore(partB: Boolean): Int {
      if (this.isDigit()) return this.digitToInt()

      if (this == 'T') return 10
      if (this == 'J') return if (partB) 1 else 11
      if (this == 'Q') return 12
      if (this == 'K') return 13
      if (this == 'A') return 14
      error("Unknown card: $this")
    }

    private fun String.cardCounts(): Map<Char, Int> {
      val counts = mutableMapOf<Char, Int>()
      for (card in this) {
        counts.putIfAbsent(card, 0)
        counts[card] = counts[card]!! + 1
      }
      return counts
    }
  }

}
