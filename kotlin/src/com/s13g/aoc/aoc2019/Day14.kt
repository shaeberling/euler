package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.lang.Long.min
import kotlin.math.ceil

/** https://adventofcode.com/2019/day/14 */
class Day14 : Solver {
  override fun solve(lines: List<String>): Result {
    val reactions = parseInput(lines)
    val resultA = produce("FUEL", 1, reactions)
    val resultB = howMuchFuelProduces(1000000000000L, reactions)
    return Result("$resultA", "$resultB")
  }

  private fun howMuchFuelProduces(maxOre: Long, reactions: Map<Amount, List<Amount>>): Long {
    // Binary search to find the answer.
    var minFuel = 0L
    var maxFuel = 10000000L
    while (maxFuel - minFuel > 1) {
      val fuel = ((maxFuel - minFuel) / 2L) + minFuel
      val ore = produce("FUEL", fuel, reactions)
      if (ore > maxOre) {
        maxFuel = fuel
      } else {
        minFuel = fuel
      }
    }
    return minFuel
  }

  /**
   * Produces the given number of the given element.
   *
   * @param name the name of the element to produce
   * @param amount the number of elements to produce
   * @param extra will add extra elements produced in here and will use them if
   *              applicable
   * @return The number of ore required to produce the elements.
   */
  private fun produce(name: String, amount: Long,
                      reactions: Map<Amount, List<Amount>>,
                      extra: MutableMap<String, Long> = mutableMapOf()): Long {
    // Base material
    if (name == "ORE") {
      return amount
    }

    // Check if we got extra from what we are asked to produce and take it.
    val takeFromExtra = min(extra[name] ?: 0, amount)
    val num = amount - takeFromExtra
    extra[name] = (extra[name] ?: 0) - takeFromExtra

    var actuallyProduced = 0L
    var lowestOreCost = Long.MAX_VALUE
    // There might be multiple ways to produce what we need. Find the cheapest.
    for ((output, inputs) in reactions.entries) {
      if (output.name == name) {
        val numRequired = ceil(num.toFloat() / output.num.toFloat()).toLong()
        var cost = 0L
        for (input in inputs) {
          val price =
              produce(input.name, input.num * numRequired, reactions, extra)
          cost += price
        }
        if (cost < lowestOreCost) {
          actuallyProduced = numRequired * output.num
          lowestOreCost = cost
        }
      }
    }
    extra[name] = (extra[name] ?: 0) + (actuallyProduced - num)
    return lowestOreCost
  }

  private fun parseInput(lines: List<String>): Map<Amount, List<Amount>> {
    val result = hashMapOf<Amount, List<Amount>>()
    lines.map { parseLine(it) }.forEach { result[it.first] = it.second }
    return result
  }

  private fun parseLine(line: String): Pair<Amount, List<Amount>> {
    val split = line.split(" => ")
    val product = parseAmount(split[1])
    val ingredients = split[0].split(", ").map { parseAmount(it) }
    return Pair(product, ingredients)
  }

  private fun parseAmount(str: String): Amount {
    val split = str.split(" ")
    return Amount(split[0].toLong(), split[1])
  }

  private data class Amount(val num: Long, val name: String)
}