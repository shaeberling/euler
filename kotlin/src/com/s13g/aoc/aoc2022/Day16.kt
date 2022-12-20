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

    return resultFrom(highestFlow("AA", valves, emptySet(), 0, 1), 0)
  }

  private fun highestFlow(
    currentId: String,
    allValves: Map<String, Valve>,
    active: Set<String>,
    steam: Int,
    time: Int
  ): Int {
    val cacheKey =
      "$currentId-$steam-$time-${active.sorted().joinToString { "," }}"
    if (cacheKey in cache) return cache[cacheKey]!!

    val current = allValves[currentId]!!
    val steamProduced = active.map { allValves[it]!! }.sumOf { it.rate }

    if (time == 31) return steam + steamProduced
    if (time > 31) {
      throw RuntimeException("Overshot")
    }

    // Can either open or not, then continue the other paths.
    // But you cannot open an already open valve.
    var maxWithOpenThis = 0

    if (current.rate > 0 && currentId !in active && time < 29) {
      val activeNew = active.plus(current.id).toSet()
      maxWithOpenThis = current.leadTo
        .maxOf {
          highestFlow(
            it,
            allValves,
            activeNew,
            steam + (steamProduced * 2),
            time + 2
          )
        }
    }

    val maxWithoutOpenThis = current.leadTo
      .maxOf {
        highestFlow(
          it,
          allValves,
          active,
          steam + steamProduced,
          time + 1
        )
      }

    val max = max(maxWithOpenThis, maxWithoutOpenThis)
    cache[cacheKey] = max
    return max
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