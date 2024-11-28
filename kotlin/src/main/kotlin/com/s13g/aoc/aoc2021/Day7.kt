package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs
import kotlin.math.min

/**
 * --- Day 7: The Treachery of Whales ---
 * https://adventofcode.com/2021/day/7
 */
class Day7 : Solver {
  override fun solve(lines: List<String>): Result {
    val crabs = lines[0].split(",").map { it.toInt() }.sorted()
    val medianCrab = crabs[crabs.size / 2]
    val partA = crabs.map { c -> abs(c - medianCrab) }.sum()
    val costsB = (crabs.min()!!..crabs.max()!!).map { crabs.map { c -> (abs(c - it) * (abs(c - it) + 1)) / 2 }.sum() }
    return Result("$partA", "${costsB.min()}")
  }
}