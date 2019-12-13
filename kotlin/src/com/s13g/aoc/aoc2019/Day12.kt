package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/** https://adventofcode.com/2019/day/12 */
class Day12 : Solver {
  override fun solve(lines: List<String>): Result {
    val planetsA = lines.map { parse(it) }
    for (n in 1..1000) {
      step(planetsA)
    }
    val energyA = planetsA.map { it.energy() }.sum()

    val planetsB = lines.map { parse(it) }
    val loopX = findLoop(planetsB) { Pair(it.pos.x, it.vel.x) }
    val loopY = findLoop(planetsB) { Pair(it.pos.y, it.vel.y) }
    val loopZ = findLoop(planetsB) { Pair(it.pos.z, it.vel.z) }
    val resultB = lcm(loopX.toLong(), lcm(loopY.toLong(), loopZ.toLong()))

    return Result("$energyA", "$resultB")
  }

  /** Find the first loop on the given axis. */
  private fun findLoop(planets: List<Planet>, get: (Planet) -> Pair<Int, Int>): Int {
    val history = hashSetOf<String>()
    while (true) {
      step(planets)
      val stateXStr = "${planets.map { get(it) }.toList()}"
      if (stateXStr in history) {
        return history.size
      }
      history.add(stateXStr)
    }
  }

  /** Lowest common multiple. */
  private fun lcm(a: Long, b: Long): Long {
    return abs(a * b) / gcd(a, b)
  }

  /** Greatest common denominator using Euclid's method. */
  private fun gcd(a: Long, b: Long): Long {
    var t: Long;
    var aa = a
    var bb = b
    while (bb != 0L) {
      t = bb;
      bb = aa % bb;
      aa = t;
    }
    return aa;
  }

  private fun step(planets: List<Planet>) {
    for (planet in planets) {
      for (other in planets) {
        if (other == planet) continue
        planet.vel.x += comp(other.pos.x, planet.pos.x)
        planet.vel.y += comp(other.pos.y, planet.pos.y)
        planet.vel.z += comp(other.pos.z, planet.pos.z)
      }
    }
    planets.forEach { it.applyVelocity() }
  }

  private fun comp(a: Int, b: Int) = if (a > b) 1 else if (a < b) -1 else 0

  private fun parse(line: String): Planet {
    val split = line.split(", ")
    val x = split[0].split("=")[1].toInt()
    val y = split[1].split("=")[1].toInt()
    val z = split[2].split("=")[1].split(">")[0].toInt()
    return Planet(XYZ(x, y, z), XYZ(0, 0, 0))
  }

  private data class Planet(val pos: XYZ, val vel: XYZ) {
    fun energy() = pos.energy() * vel.energy()
    fun applyVelocity() {
      pos.x += vel.x
      pos.y += vel.y
      pos.z += vel.z
    }
  }

  private data class XYZ(var x: Int, var y: Int, var z: Int) {
    fun energy() = abs(x) + abs(y) + abs(z)
  }
}