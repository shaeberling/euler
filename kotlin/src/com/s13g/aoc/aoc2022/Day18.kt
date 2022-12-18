package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 18: Boiling Boulders ---
 * https://adventofcode.com/2022/day/18
 */
class Day18 : Solver {
  private val deltas = setOf(
    XYZ(-1, 0, 0),
    XYZ(1, 0, 0),
    XYZ(0, -1, 0),
    XYZ(0, 1, 0),
    XYZ(0, 0, -1),
    XYZ(0, 0, 1)
  )

  override fun solve(lines: List<String>): Result {
    val cubes = lines.map { it.split(",") }
      .map { XYZ(it[0].toInt(), it[1].toInt(), it[2].toInt()) }.toSet()
    val (minX, maxX) = listOf(cubes.minOf { it.x }, cubes.maxOf { it.x })
    val (minY, maxY) = listOf(cubes.minOf { it.y }, cubes.maxOf { it.y })
    val (minZ, maxZ) = listOf(cubes.minOf { it.z }, cubes.maxOf { it.z })

    val allAir = mutableSetOf<XYZ>()
    for (x in minX..maxX) {
      for (y in minY..maxY) {
        for (z in minZ..maxZ) {
          XYZ(x, y, z).let { if (it !in cubes) allAir.add(it) }
        }
      }
    }
    val trappedAir = mutableSetOf<XYZ>()
    val alreadyLookedAt = mutableSetOf<XYZ>()
    for (air in allAir) {
      if (air in alreadyLookedAt) continue

      val group = mutableSetOf(air)
      floodFill(air, group, cubes)
      alreadyLookedAt.addAll(group)

      val trapped =
        !group.any { a -> a.x !in minX..maxX || a.y !in minY..maxY || a.z !in minZ..maxZ }
      if (trapped) trappedAir.addAll(group)
    }

    val sidesA = cubes.sumOf { numSidesFree(it, cubes, emptySet()) }
    val sidesB = cubes.sumOf { numSidesFree(it, cubes, trappedAir) }
    return resultFrom(sidesA, sidesB)
  }

  private fun floodFill(c: XYZ, group: MutableSet<XYZ>, cubes: Set<XYZ>) {
    if (c.x < cubes.minOf { it.x } || c.x > cubes.maxOf { it.x } ||
      c.y < cubes.minOf { it.y } || c.y > cubes.maxOf { it.y } ||
      c.z < cubes.minOf { it.z } || c.z > cubes.maxOf { it.z })
      return

    val sideCubes = deltas.map { XYZ(it.x + c.x, it.y + c.y, it.z + c.z) }
    val sideAirNew = sideCubes.subtract(cubes).subtract(group)
    group.addAll(sideAirNew)
    sideAirNew.forEach { floodFill(it, group, cubes) }
  }

  private fun numSidesFree(c: XYZ, all: Set<XYZ>, trappedAir: Set<XYZ>) =
    deltas.map { XYZ(it.x + c.x, it.y + c.y, it.z + c.z) }
      .minus(trappedAir)
      .minus(all).count()

  data class XYZ(val x: Int, val y: Int, val z: Int)
}