package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 13: Knights of the Dinner Table ---
 * https://adventofcode.com/2015/day/13
 */
class Day13 : Solver {
  override fun solve(lines: List<String>): Result {
    val diffs = mutableMapOf<Set<String>, Int>()
    val allNames = mutableSetOf<String>()
    for (splitLine in lines.map { it.split(" ") }) {
      val name = splitLine[0]
      val gain = splitLine[2] == "gain"
      val amount = splitLine[3].toInt() * if (gain) 1 else -1
      val nextTo = splitLine[10].substring(0, splitLine[10].length - 1)
      val pair = setOf(name, nextTo)
      if (pair !in diffs) {
        diffs[pair] = amount
      } else {
        diffs[pair] = diffs[pair]!! + amount
      }
      allNames.add(name)
    }
    val scoreA = generateAllCombos(allNames).maxOf { getScore(it, diffs) }
    val scoreB =
      generateAllCombos(allNames.plus("Sascha")).maxOf { getScore(it, diffs) }
    return resultFrom(scoreA, scoreB)
  }

  private fun getScore(order: List<String>, diffs: Map<Set<String>, Int>): Int {
    return order.plusElement(order.first()).windowed(2, 1)
      .sumOf { diffs[setOf(it[0], it[1])] ?: 0 }
  }

  private fun generateAllCombos(names: Set<String>): List<List<String>> {
    val result = mutableListOf<List<String>>()
    if (names.size == 1) {
      return result.plusElement(listOf(names.first()))
    }
    for (name in names) {
      val remaining = names.minus(name)
      for (combo in generateAllCombos(remaining)) {
        result.add(combo.plusElement(name))
      }
    }
    return result
  }
}