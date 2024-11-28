package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 14: Extended Polymerization ---
 * https://adventofcode.com/2021/day/14
 */

private typealias PairCounts = MutableMap<String, Long>

class Day14 : Solver {

  override fun solve(lines: List<String>): Result {
    val polymer = lines[0]
    val rules = lines.filter { it.contains("->") }.map { it.split(" -> ") }.associate { it[0] to it[1][0] }
    return Result("${calcRounds(polymer, rules, 10)}", "${calcRounds(polymer, rules, 40)}")
  }

  private fun calcRounds(polymer: String, rules: Map<String, Char>, numRounds: Int): Long {
    var pairCounts: PairCounts =
      polymer.windowed(2)
        .map { "${it[0]}${it[1]}" }
        .groupingBy { it }
        .eachCount()
        .map { it.key to it.value.toLong() }
        .toMap()
        .toMutableMap()
    for (r in 1..numRounds) {
      val pc2 = pairCounts.toMutableMap()
      for (p in pairCounts.keys) {
        if (rules.containsKey(p)) {
          pc2.increment("${p[0]}${rules[p]}", pairCounts[p]!!)
          pc2.increment("${rules[p]}${p[1]}", pairCounts[p]!!)
          pc2[p] = pc2[p]!! - pairCounts[p]!!
        }
      }
      pairCounts = pc2
    }

    // Count all individual characters.
    val counts = mutableMapOf<Char, Long>()
    for (pc in pairCounts) {
      for (ch in pc.key) {
        if (!counts.containsKey(ch)) counts[ch] = 0
        counts[ch] = counts[ch]!! + pc.value
      }
    }
    // First and last never get doubled, so add one of each for divide by two to work.
    counts[polymer.first()] = counts[polymer.first()]!! + 1
    counts[polymer.last()] = counts[polymer.last()]!! + 1

    val max = counts.map { it.value / 2 }.max()!!
    val min = counts.map { it.value / 2 }.min()!!
    return max - min
  }

  private fun PairCounts.increment(v: String, l: Long) {
    this.compute(v) { _, ll -> if (ll == null) l else ll + l }
  }
}