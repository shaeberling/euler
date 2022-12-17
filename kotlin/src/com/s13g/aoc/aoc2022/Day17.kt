package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom

/**
 * --- Day 17: Pyroclastic Flow ---
 * https://adventofcode.com/2022/day/17
 */
class Day17 : Solver {
  private val map = mutableSetOf<XY>()
  private var rockType = 0
  private val NUM_ROCK_TYPES = 5
  private var windIdx = 0

  private val history = mutableMapOf<String, HistoryPoint>()
  private var skippedHeight = 0L

  override fun solve(lines: List<String>): Result {
    var round = 1L
    var partA = 0L
    while (round <= 1_000_000_000_000) {
      val surfaceKey = getSurfaceKey()
      val historyKey = "$rockType-$windIdx-$surfaceKey"
      if (historyKey in history && skippedHeight == 0L && round > 2022) {
        val lastRound = history[historyKey]!!.round
        val lastHeight = history[historyKey]!!.height
        val roundDiff = round - lastRound
        val currentHeight = height()
        val heightDiff = currentHeight - lastHeight

        val roundsRemaining = (1_000_000_000_000 - round)
        val numRepeat = roundsRemaining / roundDiff
        round += (numRepeat * roundDiff)
        skippedHeight = heightDiff * numRepeat
      }
      history[historyKey] = HistoryPoint(round, height())

      val rock = sendNextRock(lines[0])
      map.addAll(rock)
      if (round == 2022L) partA = height()

      rockType = (rockType + 1) % NUM_ROCK_TYPES
      round++
    }

    val partB = height() + skippedHeight
    return resultFrom(partA, partB)
  }

  private fun height(): Long = (map.minOfOrNull { it.y } ?: 0) * -1L

  private fun getSurfaceKey(): String {
    val result = mutableListOf<Int>()
    for (col in 0..6) {
      result.add(map.filter { it.x == col }.minOfOrNull { it.y } ?: 0)
    }
    val min = result.min()
    return result.map { it - min }.joinToString(",")
  }

  private fun sendNextRock(wind: String): Set<XY> {
    val rock = createRock()
    val floor = floorLevel()
    val maxRockY = rock.maxOf { it.y }
    val dY = floor - maxRockY - 4
    rock.forEach { it.y += dY }
    rock.forEach { it.x += 2 }
//    printMap(rock.toSet())

    while (true) {  // Go through wind.
      val w = wind[windIdx]
      windIdx = (windIdx + 1) % wind.length
      if (w == '>' && rock.maxOf { it.x } < 6) {
        rock.forEach { it.x++ }
        if (rock.intersect(map).isNotEmpty()) rock.forEach { it.x-- }
      } else if (w == '<' && rock.minOf { it.x } > 0) {
        rock.forEach { it.x-- }
        if (rock.intersect(map).isNotEmpty()) rock.forEach { it.x++ }
      }

      rock.forEach { it.y++ }
      if (map.isEmpty() && rock.maxOf { it.y } == floorLevel()) {
        rock.forEach { it.y-- }
        return rock
      }

      if (rock.intersect(map).isNotEmpty()) {
        rock.forEach { it.y-- }
        return rock
      }
    }
  }

  private fun floorLevel(): Int {
    if (map.isEmpty()) return 0
    return map.minOf { it.y }
  }

  private fun createRock(): Set<XY> {
    return when (rockType) {
      0 -> setOf(XY(0, 0), XY(1, 0), XY(2, 0), XY(3, 0))
      1 -> setOf(XY(1, 0), XY(0, 1), XY(1, 1), XY(2, 1), XY(1, 2))
      2 -> setOf(XY(2, 0), XY(2, 1), XY(2, 2), XY(1, 2), XY(0, 2))
      3 -> setOf(XY(0, 0), XY(0, 1), XY(0, 2), XY(0, 3))
      4 -> setOf(XY(0, 0), XY(0, 1), XY(1, 0), XY(1, 1))
      else -> throw RuntimeException("Oh no")
    }
  }

  private fun printMap(falling: Set<XY>) {
    val mapPlus = map.plus(falling)
    val minY = mapPlus.minOf { it.y }
    val maxY = mapPlus.maxOf { it.y }

    for (y in minY..maxY) {
      for (x in -1..7) {
        val foo = XY(x, y)
        if (x == -1 || x == 7) print("|")
        else if (foo in falling) print("@")
        else print(if (XY(x, y) in map) "#" else ".")
      }
      print("\n")
    }
    print("\n")
  }

  data class HistoryPoint(val round: Long, val height: Long)
}