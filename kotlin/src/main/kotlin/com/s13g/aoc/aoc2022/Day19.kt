package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul
import com.s13g.aoc.resultFrom
import kotlin.math.max
import kotlin.math.min

/**
 * --- Day 19: Not Enough Minerals ---
 * https://adventofcode.com/2022/day/19
 */
class Day19 : Solver {
  enum class Bot { ORE, CLAY, OBS, GEODE }

  private val cache = mutableMapOf<String, Int>()

  override fun solve(lines: List<String>): Result {
    val re =
      """Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.""".toRegex()

    val blueprints = mutableListOf<Blueprint>()
    for (line in lines) {
      val (id, oreOre, clayOre, obsOre, obsCLay, geoOre, geoObs) = re.find(line)!!.destructured
      blueprints.add(
        Blueprint(
          id.toInt(),
          mapOf(
            Bot.ORE to Cost(oreOre.toInt(), 0, 0),
            Bot.CLAY to Cost(clayOre.toInt(), 0, 0),
            Bot.OBS to Cost(obsOre.toInt(), obsCLay.toInt(), 0),
            Bot.GEODE to Cost(geoOre.toInt(), 0, geoObs.toInt())
          )
        )
      )
    }
    val partA = blueprints.sumOf { qualityLevel(it) }
    val partB = blueprints.subList(0, 3).map { maxGeo(it) }.mul()
    return resultFrom(partA, partB)
  }

  private fun maxGeo(bp: Blueprint): Int {
    println("[B] BP ${bp.id}")
    cache.clear()
    return maxGeodes(bp, 1, mapOf(Bot.ORE to 1), Bank(0, 0, 0, 0), 32)
  }

  private fun qualityLevel(bp: Blueprint): Int {
    println("[A] BP ${bp.id}")
    cache.clear()
    val maxG = maxGeodes(bp, 1, mapOf(Bot.ORE to 1), Bank(0, 0, 0, 0), 24)
    return maxG * bp.id
  }

  private fun maxGeodes(
    bp: Blueprint, time: Int, bots: Map<Bot, Int>, bank: Bank, totalTime: Int
  ): Int {
    val numOreBots = bots[Bot.ORE] ?: 0
    val numClayBots = bots[Bot.CLAY] ?: 0
    val numObsBots = bots[Bot.OBS] ?: 0
    val numGeoBots = bots[Bot.GEODE] ?: 0

    // Since we're going DFS, prune branches we've already been to.
    val gameKey = "$time-$numObsBots-$numClayBots-$numObsBots-$numGeoBots-$bank"
    if (gameKey in cache) return cache[gameKey]!!

    // Prune cases where we have too many robots than we need.
    if (numOreBots > bp.maxCostOre() ||
      numClayBots > bp.maxCostClay() ||
      numObsBots > bp.maxCostObs()
    ) {
      cache[gameKey] = 0
      return 0
    }

    var newOre = bank.ore + numOreBots
    var newClay = bank.clay + numClayBots
    var newObs = bank.obs + numObsBots
    val newGeodes = bank.geodes + numGeoBots

    if (time == totalTime) return newGeodes

    // Prune: We cannot spend all these resources, so cap them, which will dup
    // them to other branches and thus reduce the search space.
    val timeLeft = totalTime - time
    newOre = min(newOre, timeLeft * bp.maxCostOre())
    newClay = min(newClay, timeLeft * bp.maxCostClay())
    newObs = min(newObs, timeLeft * bp.maxCostObs())

    var maxGeodes = 0
    for (botType in Bot.values()) {
      // Check if we can afford building this robot.
      if (bp.costs[botType]!!.ore <= bank.ore &&
        bp.costs[botType]!!.clay <= bank.clay &&
        bp.costs[botType]!!.obsidian <= bank.obs
      ) {
        val newBots =
          bots.toMutableMap().plus(botType to (bots[botType] ?: 0) + 1)
        val newBank = Bank(
          newOre - bp.costs[botType]!!.ore,
          newClay - bp.costs[botType]!!.clay,
          newObs - bp.costs[botType]!!.obsidian,
          newGeodes
        )
        maxGeodes =
          max(maxGeodes(bp, time + 1, newBots, newBank, totalTime), maxGeodes)
      }
    }

    // The last option: We don't produce any robot.
    val newBank = Bank(newOre, newClay, newObs, newGeodes)
    maxGeodes =
      max(maxGeodes(bp, time + 1, bots, newBank, totalTime), maxGeodes)
    cache[gameKey] = maxGeodes
    return maxGeodes
  }

  data class Blueprint(val id: Int, val costs: Map<Bot, Cost>) {
    fun maxCostOre() = costs.values.maxOf { it.ore }

    fun maxCostClay() = costs.values.maxOf { it.clay }

    fun maxCostObs() = costs.values.maxOf { it.obsidian }
  }

  data class Cost(val ore: Int, val clay: Int, val obsidian: Int)
  data class Bank(
    val ore: Int,
    val clay: Int,
    val obs: Int,
    val geodes: Int
  )
}