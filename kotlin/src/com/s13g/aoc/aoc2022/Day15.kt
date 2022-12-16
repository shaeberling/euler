package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.manhattan
import com.s13g.aoc.resultFrom
import com.s13g.aoc.subtract
import kotlin.math.abs
import kotlin.math.max

/**
 * --- Day 15: Beacon Exclusion Zone ---
 * https://adventofcode.com/2022/day/15
 */
class Day15 : Solver {
  private val re =
    """x=(-?\d+), y=(-?\d+): .* x=(-?\d+), y=(-?\d+)$""".toRegex()

  override fun solve(lines: List<String>): Result {
    val sensors = lines.map { parse(it) }.toSet()
    val beaconPos = sensors.map { it.clBeacon }.toSet()

    val minX = sensors.minOf { it.pos.x - it.radius() }
    val maxX = sensors.maxOf { it.pos.x + it.radius() }

    var countA = 0
    for (x in minX..maxX) {
      val probe = XY(x, 2000000)
      if (isEmpty(probe, sensors) && (probe !in beaconPos)) countA++
    }
    return resultFrom(countA.toLong(), findBeaconFreq(sensors))
  }

  private fun findBeaconFreq(sensors: Set<Sensor>): Long {
    for (y in 0..4000000) {
      val pos = XY(0, y)
      while (pos.x <= 4000000) {
        // Which sensors can see us right now?
        val inRangeOf =
          sensors.filter { dist(it.pos, pos).manhattan() <= it.radius() }
        if (inRangeOf.isEmpty()) { // We found the gap!
          return pos.x.toLong() * 4000000L + pos.y.toLong()
        }
        // Skip forward by looking at the width of that sensor on our line.
        for (sensor in inRangeOf) {
          val diffY = abs(sensor.pos.y - pos.y)
          val width = sensor.radius() - diffY
          // Skip to this based on remaining width.
          pos.x = max(sensor.pos.x + width + 1, pos.y)
        }
      }
    }
    throw RuntimeException("Cannot find beacon")
  }

  private fun isEmpty(pos: XY, sensors: Set<Sensor>): Boolean {
    for (sensor in sensors) {
      if (pos.subtract(sensor.pos).manhattan() <= sensor.radius()) {
        return true
      }
    }
    return false
  }

  private fun dist(pos: XY, loc: XY) =
    XY(abs(loc.x - pos.x), abs(loc.y - pos.y))

  private fun parse(line: String): Sensor {
    val (posX, posY, bX, bY) = re.find(line)!!.destructured
    return Sensor(XY(posX.toInt(), posY.toInt()), XY(bX.toInt(), bY.toInt()))
  }
}

data class Sensor(val pos: XY, val clBeacon: XY) {
  fun radius() = clBeacon.subtract(pos).manhattan()
}