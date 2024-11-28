package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.min

/**
 * --- Day 10: Adapter Array ---
 * https://adventofcode.com/2020/day/10
 */
class Day10 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines.map { it.toLong() }.sorted().toMutableList()
    val rating = input.max()!! + 3L
    input.add(0, 0)
    input.add(rating)

    var countDiff1 = 0
    var countDiff3 = 0
    for (i in 0 until input.size - 1) {
      if (input[i + 1] - input[i] == 1L) countDiff1++
      if (input[i + 1] - input[i] == 3L) countDiff3++
    }
    val resultA = countDiff1 * countDiff3

    // Stores how many times a given element can be visited by the different branches.
    val numVisits = MutableList(input.size) { 0L }

    // We start with the first one initialized with 1, since we're here.
    numVisits[0] = 1
    for (i in 0 until input.size) {
      val base = numVisits[i]
      // Jumps up to 3 are OK so we look ahead and if there is a possible jump, add our current branches num to it since
      // there are now 'base' more ways to get here.
      for (x in i + 1..min(i + 3, input.size - 1)) {
        if (input[x] - input[i] <= 3) numVisits[x] += base
      }
    }

    // The solution of all the possible ways will be in the last element's counter.
    val resultB = numVisits.last()
    return Result("$resultA", "$resultB")
  }
}