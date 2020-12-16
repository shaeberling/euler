package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul

/**
 * --- Day 16: Ticket Translation ---
 * https://adventofcode.com/2020/day/16
 */
val rangeRegEx = """(.+): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()

class Day16 : Solver {

  override fun solve(lines: List<String>): Result {
    val (wordRanges, yourTicket, nearbyTickets) = parseInput(lines)
    val allRanges = wordRanges.flatMap { listOf(it.value.range1, it.value.range2) }
    val resultA = nearbyTickets.map { tixErrorRate(it, allRanges) }.sum()
    val validTix = nearbyTickets.toMutableList().apply { add(yourTicket) }.filter { isValidTicket(it, allRanges) }
    val allPlaces = setOf(*Array(yourTicket.size) { it })
    val words = wordRanges.keys

    // Map of word to viable placements. We start out with all positions and then remove bad ones.
    val possiblePlaces = words.associateWith { allPlaces.toMutableSet() }
    for (word in words) {
      for (tix in validTix) {
        for (i in tix.indices) {
          // 'word' cannot be in position 'i' since it doesn't fit for ticket 'tix'.
          if (!wordRanges[word]!!.isWithin(tix[i])) possiblePlaces[word]!!.remove(i)
        }
      }
    }
    val placements = reducePlaces(possiblePlaces)
    val resultB = words.filter { it.startsWith("departure") }.map { yourTicket[placements[it]!!].toLong() }.mul()
    return Result("$resultA", "$resultB")
  }

  /** Take possible placements and determine actual order/placements. */
  private fun reducePlaces(placements: Map<String, MutableSet<Int>>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()

    // Repeat until we have final placements for all words.
    while (result.size != placements.size)
      for (word in placements.keys) {
        // If 'word' only has one possible location, put it there!
        if (placements[word]!!.size == 1) {
          val place = placements[word]!!.first()
          result[word] = place

          // Remove this position from all other words' possible placements.
          placements.values.forEach { it.remove(place) }
        }
      }
    return result
  }

  private fun isValidTicket(ticket: List<Int>, ranges: List<Range>): Boolean {
    for (n in ticket) {
      if (ranges.map { it.isWithin(n) }.count { it } == 0) return false
    }
    return true
  }

  private fun tixErrorRate(ticket: List<Int>, ranges: List<Range>) =
      ticket.map { t -> if (ranges.map { it.isWithin(t) }.count { it } == 0) t else 0 }.sum()

  private fun parseInput(lines: List<String>): Triple<Map<String, WordRange>, List<Int>, List<List<Int>>> {
    val wordRanges = mutableMapOf<String, WordRange>()
    val yourTicket = mutableListOf<Int>()
    val nearbyTickets = mutableListOf<List<Int>>()

    for (l in lines.indices) {
      val rangeMatch = rangeRegEx.find(lines[l])
      if (rangeMatch != null) {
        val (word, from1, to1, from2, to2) = rangeMatch.destructured
        wordRanges[word] = WordRange(Range(from1.toInt(), to1.toInt()), Range(from2.toInt(), to2.toInt()))
      }
      if ((l - 1) in lines.indices && lines[l - 1] == "your ticket:") {
        yourTicket.addAll(lines[l].split(',').map { it.toInt() })
      }
      if ((l - 1) in lines.indices && lines[l - 1] == "nearby tickets:") {
        lines.subList(l, lines.size).map { line -> nearbyTickets.add(line.split(',').map { it.toInt() }) }
        break
      }
    }
    return Triple(wordRanges, yourTicket, nearbyTickets)
  }

  private data class Range(val from: Int, val to: Int)

  private fun Range.isWithin(v: Int) = v in from..to

  private data class WordRange(val range1: Range, val range2: Range)

  private fun WordRange.isWithin(v: Int) = range1.isWithin(v) || range2.isWithin(v)
}
