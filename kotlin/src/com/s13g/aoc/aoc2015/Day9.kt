package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.max
import kotlin.math.min

class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    val re = """(\w+) to (\w+) = (\d+)$""".toRegex()
    val distances = lines
      .map { re.find(it)!!.destructured }
      .map { (from, to, distance) ->
        Distance(
          setOf(from, to),
          distance.toInt()
        )
      }
      .toSet()
    val destinations = distances.map { it.cities }.flatten().toSet()
    val m1 = shortestLongestTravel("", destinations, distances, false)
    val m2 = shortestLongestTravel("", destinations, distances, true)

    return resultFrom(m1, m2)
  }

  private fun shortestLongestTravel(
    from: String,
    destinations: Set<String>,
    distances: Set<Distance>,
    longest: Boolean
  ): Int {
    if (destinations.isEmpty()) return 0

    var best = if (longest) Int.MIN_VALUE else Int.MAX_VALUE
    for (nextStop in destinations) {
      val trip = setOf(from, nextStop)
      val distCost = distances.find { it.cities == trip }?.cost ?: 0

      val cost = distCost + shortestLongestTravel(
        nextStop,
        destinations.minus(nextStop),
        distances,
        longest
      )
      best = if (longest) max(best, cost) else min(best, cost)
    }
    return best
  }

  data class Distance(val cities: Set<String>, val cost: Int)
}