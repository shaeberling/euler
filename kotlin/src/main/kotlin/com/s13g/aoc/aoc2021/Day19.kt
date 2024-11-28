package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs
import kotlin.math.max

/**
 * --- Day 19: Beacon Scanner ---
 * https://adventofcode.com/2021/day/19
 */
class Day19 : Solver {
  override fun solve(lines: List<String>): Result {
    val scanners = parseInput(lines)

    // Will contain all of the aligned points, relative to scanner 0.
    // Start with all of scanner 0's beacons in default orientation
    val alignedComposite = mutableSetOf<XYZ>()
    alignedComposite.addAll(scanners[0])

    // List of scanner indices that have already been aligned.
    // Start with scanner as it is our reference.
    val aligned = mutableSetOf<Int>()
    aligned.add(0)

    // Contains the position for every aligned scanner (not idx-matched!).
    val scannerPositions = mutableListOf<XYZ>()
    scannerPositions.add(XYZ(0, 0, 0))

    // Loop until all scanners are aligned.
    while (aligned.size != scanners.size) {
      // Go through all already aligned points.
      for (alignedPoint in alignedComposite.toList()) {
        // Go through all unaligned scanners.
        for (b in scanners.indices) {
          if (aligned.contains(b)) continue
          // Compare all permutations from unaligned scanner (b) to the given rotation of the first.
          // We don't have to rotate the already aligned points.
          for (permB in permutateScanner(scanners[b])) {
            // Go through every point of b and see how all other points would be positioned if it
            // was the same as the alignedPoint.
            for (initialPointB in permB) {
              val delta = alignedPoint minus initialPointB
              var countMatches = 0
              val newPoints = mutableSetOf<XYZ>()
              for (pointB in permB) {
                val testPoint = pointB plus delta
                newPoints.add(testPoint)
                if (alignedComposite.contains(testPoint)) countMatches++
              }
              // If we matched at least 12, the scanner is now aligned.
              if (countMatches >= 12) {
                alignedComposite.addAll(newPoints)
                aligned.add(b)
                scannerPositions.add(delta)
              }
            }
          }
        }
      }
    }

    // Calculate largest Manhattan distance for partB between all scanners.
    var largestDistance = Long.MIN_VALUE
    for (a in scannerPositions.indices) {
      for (b in a until scannerPositions.size) {
        largestDistance = max(largestDistance, scannerPositions[a] distance scannerPositions[b])
      }
    }

    val partA = alignedComposite.size
    val partB = largestDistance
    return Result("$partA", "$partB")
  }

  /** Generate all 24 permutations given the list of initial beacons. */
  private fun permutateScanner(beacons: Set<XYZ>): List<Set<XYZ>> {
    val result = List(24) { mutableSetOf<XYZ>() }

    for (b in beacons) {
      result[0].add(XYZ(b.x, b.y, b.z))
      result[1].add(XYZ(b.x, -b.z, b.y))
      result[2].add(XYZ(b.x, -b.y, -b.z))
      result[3].add(XYZ(b.x, b.z, -b.y))
      result[4].add(XYZ(b.y, -b.x, b.z))
      result[5].add(XYZ(b.y, -b.z, -b.x))
      result[6].add(XYZ(b.y, b.x, -b.z))
      result[7].add(XYZ(b.y, b.z, b.x))
      result[8].add(XYZ(-b.x, -b.y, b.z))
      result[9].add(XYZ(-b.x, -b.z, -b.y))

      result[10].add(XYZ(-b.x, b.y, -b.z))
      result[11].add(XYZ(-b.x, b.z, b.y))
      result[12].add(XYZ(-b.y, b.x, b.z))
      result[13].add(XYZ(-b.y, -b.z, b.x))
      result[14].add(XYZ(-b.y, -b.x, -b.z))
      result[15].add(XYZ(-b.y, b.z, -b.x))
      result[16].add(XYZ(b.z, b.x, b.y))
      result[17].add(XYZ(b.z, b.y, -b.x))
      result[18].add(XYZ(b.z, -b.x, -b.y))
      result[19].add(XYZ(b.z, -b.y, b.x))

      result[20].add(XYZ(-b.z, -b.y, -b.x))
      result[21].add(XYZ(-b.z, b.x, -b.y))
      result[22].add(XYZ(-b.z, b.y, b.x))
      result[23].add(XYZ(-b.z, -b.x, b.y))
    }
    return result
  }

  private fun parseInput(lines: List<String>): List<Set<XYZ>> {
    val result = mutableListOf<MutableSet<XYZ>>()
    for (line in lines) {
      if (line.contains("---")) {
        result.add(mutableSetOf())
        continue
      }
      if (line.isBlank()) continue
      result.last().add(parseXYZ(line))
    }
    return result
  }

  private fun parseXYZ(str: String): XYZ {
    val values = str.split(',').map { it.toLong() }
    assert(values.size == 3)
    return XYZ(values[0], values[1], values[2])
  }

  private data class XYZ(val x: Long, val y: Long, val z: Long)

  private infix fun XYZ.plus(other: XYZ) = XYZ(x + other.x, y + other.y, z + other.z)
  private infix fun XYZ.minus(other: XYZ) = XYZ(x - other.x, y - other.y, z - other.z)
  private infix fun XYZ.distance(other: XYZ) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
}