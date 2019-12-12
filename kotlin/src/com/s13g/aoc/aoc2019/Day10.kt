package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

/** https://adventofcode.com/2019/day/10 */
class Day10 : Solver {
  override fun solve(lines: List<String>): Result {
    // Parse Input
    val field = hashSetOf<XY>()
    for ((y, line) in lines.withIndex()) {
      for ((x, c) in line.withIndex()) {
        if (c == '#') {
          field.add(XY(x, y))
        }
      }
    }

    // Find the asteroid with the best visibility.
    var mostSeen = 0
    var bestAsteroid = XY(0, 0)
    for (a in field) {
      val count = countVisible(a, field)
      if (count > mostSeen) {
        mostSeen = count
        bestAsteroid = a
      }
    }

    // Create a set without the selected asteroid.
    val fieldB = field.toMutableSet()
    fieldB.remove(bestAsteroid)

    // Add angle and dinstanceof every asteroid to selected one.
    addAuxData(bestAsteroid, fieldB)
    // Sort according to Part B rules.
    val twoHundred = sortB(fieldB.sortedWith(partBComparator))[199]
    // Select 200th to be destroyed.
    val solutionB = twoHundred.x * 100 + twoHundred.y
    return Result("$mostSeen", "$solutionB")
  }

  /** Sort primarily by angle, but then ensure same-angle items get sorted next-round. */
  private fun sortB(sorted: List<XY>): List<XY> {
    val angled = mutableListOf<Angled>()
    angled.add(Angled(sorted[0].angle, mutableListOf(sorted[0])))
    for (i in 1 until sorted.size) {
      if (sorted[i - 1].angle == sorted[i].angle) {
        angled.last().items.add(sorted[i])
      } else {
        angled.add(Angled(sorted[i].angle, mutableListOf(sorted[i])))
      }
    }

    val result = mutableListOf<XY>()
    while (result.size < sorted.size) {
      for (a in angled) {
        if (a.items.isNotEmpty()) {
          result.add(a.items.removeAt(0))
        }
      }
    }
    return result
  }

  private fun addAuxData(a: XY, field: Set<XY>) {
    for (b in field) {
      val dX = b.x - a.x
      val dY = b.y - a.y
      b.angle = angle(dX, dY)

      val adX = abs(dX)
      val adY = abs(dY)
      b.distance = sqrt((adX * adX + adY * adY).toDouble())
    }
  }

  private fun angle(dX: Int, dY: Int) = Math.PI - atan2(dX.toDouble(), dY.toDouble())

  /** Count all asteroids directly visible from 'a'. */
  private fun countVisible(a: XY, field: Set<XY>): Int {
    // Asteroids with the same angle only count as one (the others will be
    // hidden behind)
    val anglesFound = mutableSetOf<Double>()
    for (b in field) {
      if (a == b) continue
      anglesFound.add(angle(b.x - a.x, b.y - a.y))
    }
    return anglesFound.size
  }

  private data class Angled(val angle: Double, val items: MutableList<XY>)
  private data class XY(val x: Int, val y: Int, var angle: Double = 0.0, var distance: Double = 0.0)

  private val partBComparator = Comparator<XY> { a, b ->
    when {
      a.angle < b.angle -> -1
      a.angle > b.angle -> 1
      else ->
        when {
          a.distance < b.distance -> -1
          a.distance > b.distance -> 1
          else -> 0
        }
    }
  }
}