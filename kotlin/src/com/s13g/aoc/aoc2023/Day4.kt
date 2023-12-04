package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

import com.s13g.aoc.`**`

/**
 * --- Day 4: Scratchcards ---
 * https://adventofcode.com/2023/day/4
 */
class Day4 : Solver {
  override fun solve(lines: List<String>): Result {
    val cards = lines.map { parseCard(it) }
    val partA = cards.map { it.numMatching() }.map { 2 `**` (it - 1) }.sum()
    return resultFrom(partA, solvePartB(cards))
  }

  private fun solvePartB(cards: List<Card>): Int {
    val cardCounts = cards.indices.groupBy { it }.mapValues { 1 }.toMutableMap()
    for ((idx, card) in cards.withIndex()) {
      for (i in 0 until card.numMatching()) {
        // Add the number that the idx's card exist to all the following n cards.
        cardCounts[idx + i + 1] = cardCounts[idx + i + 1]!! + cardCounts[idx]!!
      }
    }
    return cardCounts.values.sum()
  }

  private fun parseCard(line: String): Card {
    val numsStr = line.split(": ")[1]
    val (lhs, rhs) = numsStr.split(" | ")
    return Card(lhs.getListOfNums(), rhs.getListOfNums())
  }

  private data class Card(val winning: Set<Int>, val have: Set<Int>)

  private fun Card.numMatching() = have.intersect(winning).count()
  private fun String.getListOfNums() =
    trim().split(" ").filter { it.isNotBlank() }
      .map { it.trim().toInt() }.toSet()
}