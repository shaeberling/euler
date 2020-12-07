package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

typealias Baggage = Map<String, Map<String, Int>>

/**
 * --- Day 7: Handy Haversacks ---
 * https://adventofcode.com/2020/day/7
 */
class Day7 : Solver {
  override fun solve(lines: List<String>): Result {
    val allBags = parseBags(lines)
    val resultA = allBags.keys.map { hasGoldenBag(it, allBags) }.filter { it }.count()
    val resultB = countWithin("shiny gold", allBags) - 1
    return Result("$resultA", "$resultB")
  }

  private val mainBagSplit = """(.+) bags contain (.+)""".toRegex()
  private val numBagRegex = """(^\d+) (.+) bag(|s)""".toRegex()
  private fun parseBags(lines: List<String>): Baggage {
    val allBags = mutableMapOf<String, MutableMap<String, Int>>()
    for (line in lines) {
      val (bagKey, bagValue) = mainBagSplit.find(line)!!.destructured
      allBags[bagKey] = mutableMapOf()
      if (bagValue.trim() != "no other bags.") {
        for (contained in bagValue.split(",")) {
          val (countStr, name) = numBagRegex.find(contained.trim())!!.destructured
          allBags[bagKey]!![name] = countStr.toInt()
        }
      }
    }
    return allBags
  }

  // Part 1
  private fun hasGoldenBag(col: String, bags: Baggage): Boolean {
    for (bag in bags[col] ?: emptyMap()) if (bag.key == "shiny gold" || hasGoldenBag(bag.key, bags)) return true
    return false;
  }

  // Part 2
  private fun countWithin(col: String, bags: Baggage): Int =
      (bags[col] ?: emptyMap()).map { countWithin(it.key, bags) * it.value }.sum() + 1
}
