package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 12: Hot Springs ---
 * https://adventofcode.com/2023/day/12
 */
class Day12 : Solver {
  private val cache = mutableMapOf<String, Long>()
  override fun solve(lines: List<String>): Result {
    val newLines = lines.map { expandLine(it) }
    val partA = lines.sumOf { countSolutionsNew(it) }
    val partB = newLines.sumOf { countSolutionsNew(it) }
    return resultFrom(partA, partB)
  }

  private fun countSolutionsNew(str: String): Long {
    cache.clear()
    val (part1, part2) = str.split(" ")
    val nums = part2.split(",").map { it.toInt() }
    val length = part1.length

    val positions = mutableMapOf<Char, Set<Int>>()
    val empties = mutableSetOf<Int>()
    str.forEachIndexed { i, c -> if (c == '.') empties.add(i) }
    val hashes = mutableSetOf<Int>()
    str.forEachIndexed { i, c -> if (c == '#') hashes.add(i) }
    val placeholders = mutableSetOf<Int>()
    str.forEachIndexed { i, c -> if (c == '?') placeholders.add(i) }
    positions['.'] = empties
    positions['#'] = hashes
    positions['?'] = placeholders

    // Place every number in their first possible location.
    return countSolutionsNew(0, nums, length, positions)
  }

  private fun countSolutionsNew(
    placeOnOrAfter: Int,
    toPlace: List<Int>,
    length: Int,
    setup: Map<Char, Set<Int>>
  ): Long {
    if (toPlace.isEmpty()) {
      // Ensure there is not hash piece later than this.
      return if ((placeOnOrAfter until length).count { it in setup['#']!! }
        > 0) 0 else 1
    }
    val cacheKey = "$placeOnOrAfter-${toPlace.size}"
    if (cacheKey in cache) return cache[cacheKey]!!

    val nextToPlace = toPlace.first()
    val newToPlace = toPlace.drop(1)
    var count = 0L

    for (start in placeOnOrAfter..(length - nextToPlace)) {
      // We cannot skip already placed pieces
      if ((start - 1) in setup['#']!!) break;

      // Check that the location just following is not a '# as it would not
      // properly separate this range.
      var valid = (start + nextToPlace) !in setup['#']!!
      if (start > 0 && (start - 1) in setup['#']!!) valid = false

      // Check that there is no confirmed dot within the range, prohibiting this
      // placement.
      if (valid) {
        for (n in start until start + nextToPlace) {
          if (n in setup['.']!!) {
            valid = false
            break
          }
        }
      }
      if (valid) {
        count += countSolutionsNew(
          start + nextToPlace + 1,
          newToPlace,
          length,
          setup
        )
      }
    }
    cache[cacheKey] = count
    return count
  }

  private fun expandLine(str: String): String {
    val (part1, part2) = str.split(" ")
    val newPart1 = (1..5).map { part1 }.joinToString("?")
    val newPart2 = (1..5).map { part2 }.joinToString(",")
    return "$newPart1 $newPart2"
  }

  private data class Range(val from: Int, val to: Int)
}