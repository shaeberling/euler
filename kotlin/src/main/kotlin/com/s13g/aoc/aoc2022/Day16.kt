package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.max

/**
 * --- Day 16: Proboscidea Volcanium ---
 * https://adventofcode.com/2022/day/16
 */
class Day16 : Solver {
  private val cache = mutableMapOf<String, Int>()
  override fun solve(lines: List<String>): Result {
    val valves = lines.map { parseLine(it) }.associateBy { it.id }
    val partA = maxAddtlPoints(valves["AA"]!!, emptySet(), 30, 0, valves)
    cache.clear()
    val partB = maxAddtlPoints(valves["AA"]!!, emptySet(), 26, 1, valves)
    return resultFrom(partA, partB)
  }

  private fun maxAddtlPoints(
    current: Valve,
    opened: Set<String>,
    timeLeft: Int,
    numOthers: Int,
    allValves: Map<String, Valve>,
  ): Int {
    if (timeLeft == 0) {
      // Note, this solution for Part 2 is from Jonathan Paulson. I tried to
      // come up with doing DP in another way, but couldn't get to the solution.
      // See https://www.youtube.com/watch?v=DgqkVDr1WX8 for his excellent
      // explanation.
      return if (numOthers <= 0) 0
      else maxAddtlPoints(
        allValves["AA"]!!,
        opened,
        26,
        numOthers - 1,
        allValves
      )
    }

    val cacheKey =
      "${current.id}-$timeLeft-${opened.sorted().joinToString(",")}-$numOthers"
    if (cacheKey in cache) return cache[cacheKey]!!

    // Can either open or not, then continue the other paths.
    // But you cannot open an already open valve.
    var maxValue = 0

    // Open this valve and stay if it can produce steam and isn't already open.
    if (current.rate > 0 && current.id !in opened) {
      maxValue = max(
        maxValue, maxAddtlPoints(
          current,
          opened.plus(current.id),
          timeLeft - 1,
          numOthers,
          allValves
        ) + (current.rate * (timeLeft - 1))
      )
    }

    // Don't open a valve and go to all the other places.
    maxValue = max(maxValue, current.leadTo.map { allValves[it]!! }
      .maxOf {
        maxAddtlPoints(
          it,
          opened,
          timeLeft - 1,
          numOthers,
          allValves
        )
      })

    cache[cacheKey] = maxValue
    return maxValue
  }

  private fun parseLine(line: String): Valve {
    val split = line.split(" ")
    val id = split[1]
    val rateStr = split[4].split("=")[1]
    val rate = rateStr.substring(0, rateStr.length - 1).toInt()

    val leadTo = line.split("to valves ", "to valve ")[1].split(", ").toSet()
    return Valve(id, rate, leadTo)
  }

  data class Valve(val id: String, val rate: Int, val leadTo: Set<String>)

}