package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.max
import kotlin.math.min

/**
 * --- Day 5: If You Give A Seed A Fertilizer ---
 * https://adventofcode.com/2023/day/5
 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    val seeds = lines[0].split(": ", " ").drop(1).map { it.toLong() }
    val seedsA = seeds.map { Range(it, it) }
    val seedsB = seeds.windowed(2, 2).map { Range(it[0], it[0] + it[1] - 1) }
    val maps = parseMaps(lines.drop(1))
    return resultFrom(
      solve(seedsA, maps.values),
      solve(seedsB, maps.values)
    )
  }

  private fun solve(
    seedRanges: List<Range>,
    sections: Collection<List<RangeMap>>
  ): Long {
    var lowestLocation = Long.MAX_VALUE
    for (seedRange in seedRanges) {
      // We start out with only one range, but it might become multiple later as
      // we split due to partially matching range mappings.
      val values = mutableListOf(seedRange)
      for (section in sections) {
        // For each section we start with our current values as unmatched. We
        // keep updating these as we go through the various range mappings.
        val unmatched = values.toMutableList()
        val matched = mutableListOf<Range>()
        for (mapping in section) {
          val unmatchedCopy = unmatched.toList()
          unmatched.clear()
          for (value in unmatchedCopy) {
            val (newMatched, newUnmatched) = intersect(
              value,
              mapping.src,
              mapping.dest
            )
            matched.addAll(newMatched)
            unmatched.addAll(newUnmatched)
          }
        }
        values.clear()
        values.addAll(matched)
        values.addAll(unmatched)
      }
      lowestLocation = minOf(lowestLocation, values.minOf { it.from })
    }
    return lowestLocation
  }
}


private fun parseMaps(lines: List<String>): Map<String, List<RangeMap>> {
  val result = mutableMapOf<String, MutableList<RangeMap>>()
  var currentName = ""
  for (line in lines) {
    if (line.isNotBlank() && !line[0].isDigit()) {
      currentName = line.split(" ")[0]
      result[currentName] = mutableListOf()
    } else if (line.isNotBlank()) {
      val (dest, src, len) = line.split(" ")
      result[currentName]!!.add(
        RangeMap(
          Range(dest.toLong(), dest.toLong() + len.toLong() - 1),
          Range(src.toLong(), src.toLong() + len.toLong() - 1)
        )
      )
    }
  }
  return result
}

private data class RangeMap(
  val dest: Range,
  val src: Range
)

private fun intersect(seed: Range, from: Range, to: Range): Pair<List<Range>, List<Range>> {
  // We need to separate the matched ranges from the unmatched ones since the
  // unmatched once will need to be further mapped on other mappings in the
  // same section.
  val matched = mutableListOf<Range>()

  // Define the overlapping/matching range.
  val resultFrom = max(seed.from, from.from)
  val resultTo = min(seed.to, from.to)

  // This will not be true if there is no overlap.
  if (resultTo >= resultFrom) {
    val rangeDiff = to.from - from.from
    matched.add(Range(resultFrom + rangeDiff, resultTo + rangeDiff))
  }

  // The non-overlapping sections get passed on as-is, unmatched.
  val unmatched = mutableListOf<Range>()
  if (seed.from < from.from) {
    unmatched.add(Range(seed.from, min(from.from - 1, seed.to)))
  }
  if (seed.to > from.to) {
    unmatched.add(Range(max(from.to + 1, seed.from), seed.to))
  }
  return Pair(matched, unmatched)
}

private data class Range(val from: Long, val to: Long)
