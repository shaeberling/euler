package com.s13g.aoc.aoc2020

import com.s13g.aoc.*
import java.math.BigInteger

/**
 * --- Day 13: ---
 * https://adventofcode.com/2020/day/13
 */
class Day13 : Solver {
  override fun solve(lines: List<String>): Result {
    val earliestTime = lines[0].toLong()
    val busIds = lines[1].split(',').filter { it != "x" }.map { it.toLong() }
    return Result("${partA(earliestTime, busIds)}", "${partB(lines[1])}")
  }
}

/** Part A, when brute force still worked :-) */
fun partA(earliest: Long, busIds: List<Long>): Long {
  val times = MutableList(busIds.size) { 0L }
  var currentWinTime = Long.MAX_VALUE
  var currentWinBus = 0L
  while (times.min()!! <= earliest) {
    for (b in busIds.indices) {
      times[b] += busIds[b]
      if (times[b] > earliest) {
        val diff = times[b] - earliest
        if (diff < currentWinTime - earliest) {
          currentWinBus = busIds[b]
          currentWinTime = times[b]
        }
      }
    }
  }
  return currentWinBus * (currentWinTime - earliest)
}

/**
 * This is an implementation after I read about the problem and how to solve it
 * efficiently.
 * Note: Loosely based on Chinese Remainder Theorem.
 */
fun partB(data: String): Long {
  // Parse (offset -> busId/period)
  val busTimes = data.split(',').withIndex().filter { it.value != "x" }.map { Pair(it.index, it.value.toLong()) }

  // The current time point we are looking at.
  var currentTime = 0L
  // The step size will grow as a multiple of busIds.
  var stepSize = 1L

  // Go through all the buses...
  for ((offset, busId) in busTimes) {
    // Move forward in time until the current busId schedule lines up and a bus
    // departs (including offset).,
    while ((currentTime + offset) % busId != 0L) {
      currentTime += stepSize
    }
    // Now that we found it, we know we can move forward in multiples of this
    // busId multiplied with all other previously found busses. No need to check
    // any steps in between since we know they won't work.
    stepSize *= busId
  }
  return currentTime
}

// For prosperity:
// This is the brute force solution I originally came up with. Works on the
// demo inputs but it will run forever on the real input. Good to understand
// the problem though.
fun partB_BruteForce(data: String): BigInteger {
  val busIds = mutableListOf<BigInteger>()
  val positions = mutableListOf<BigInteger>()
  val diffs = mutableListOf<BigInteger>()

  // First, parse the input.
  val split = data.split(',');
  for (b in split.indices) {
    if (split[b] != "x") {
      busIds.add(split[b].toBigInteger())
      positions.add(BigInteger.valueOf(b.toLong()))
      diffs.add(BigInteger.valueOf(b.toLong()))
    }
  }

  // We step through the schedule
  while (true) {
    // First lets check if all the buses are in the order we want them to be.
    // If so, we're done.
    var valid = true
    for (b in 1 until busIds.size) {
      if (positions[0].add(diffs[b]).mod(busIds[b]) != BigInteger.ZERO) {
        valid = false
        break
      }
    }
    if (valid) return positions[0]

    // Iterate bus positions.
    for (b in busIds.indices) {
      positions[b] = positions[b].add(busIds[b])
    }
  }
}

