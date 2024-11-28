package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 2: I Was Told There Would Be No Math ---
 * https://adventofcode.com/2015/day/2
 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    // Parse the LxWxD dimensions
    val dimensions = lines.map { s -> s.split("x").map { it.toInt() } }
    // Calculate the areas of the three face-types
    val areas =
      dimensions.map { d -> arrayOf(d[0] * d[1], d[1] * d[2], +d[2] * d[0]) }
    // Add two of each face-type plus the slack (smallest area) for solution 1.
    val totalAreaSize =
      areas.sumOf { a -> 2 * a[0] + 2 * a[1] + 2 * a[2] + a.min() }

    // For solution 2, sort the edge lengths, take the two shortest add their
    // perimeter, then add the volume of the box for the bow.
    val ribbonNeeded = dimensions.map { d -> d.sorted() }
      .sumOf { dims -> 2 * dims[0] + 2 * dims[1] + dims[0] * dims[1] * dims[2] }

    return Result("$totalAreaSize", "$ribbonNeeded")
  }
}