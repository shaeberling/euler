package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/**
 * --- Day 22: Reactor Reboot ---
 * https://adventofcode.com/2021/day/22
 */
class Day22 : Solver {
  val regex =
    """(\w+)+ x=([-]*\d+)..([-]*\d+),y=([-]*\d+)..([-]*\d+),z=([-]*\d+)..([-]*\d+)""".toRegex()

  override fun solve(lines: List<String>): Result {
    val partA = run(lines, true)
    val partB = run(lines, false)
    return Result("$partA", "$partB")
  }

  fun run(lines: List<String>, partA: Boolean): Long {
    // Note: We always want a world where no cube intersects.
    //       Also, all cubes are always "on".
    val world = mutableSetOf<Cuboid>()

    for (line in lines) {
      val (cube, on) = parseLine(line)
      // Only consider lines within the -50..50 space for partA.
      if (partA &&
        listOf(
          cube.from.x,
          cube.from.y,
          cube.from.z,
          cube.to.x,
          cube.to.y,
          cube.to.z
        ).map { abs(it) }.max()!! > 50
      ) continue

      // We cut away the overlapping pieces from all existing cubes.
      // This way if the new cube is on, we can add it completely.
      // If it's off, we don't add it and thus remove the intersection.
      for (existingCube in world.toList()) {
        if (cube.intersects(existingCube)) {
          world.remove(existingCube)
          world.addAll(existingCube.carve(cube))
        }
      }
      if (on) world.add(cube)
      // After each step there should be no intersecting cubes.
    }
    return world.map { it.size() }.sum()
  }


  private class Cuboid(val from: XYZ, val to: XYZ) {
    fun carve(other: Cuboid): Set<Cuboid> {
      // Generate 3x3x3 - 1 (max split cubes)
      val xes = listOf(from.x, other.from.x, to.x, other.to.x).sorted()
      val yes = listOf(from.y, other.from.y, to.y, other.to.y).sorted()
      val zes = listOf(from.z, other.from.z, to.z, other.to.z).sorted()

      val allTwentySeven = mutableSetOf<Cuboid>()
      for (ix in 1 until xes.size) {
        for (iy in 1 until yes.size) {
          for (iz in 1 until zes.size) {
            allTwentySeven.add(
              Cuboid(
                XYZ(xes[ix - 1], yes[iy - 1], zes[iz - 1]),
                XYZ(xes[ix], yes[iy], zes[iz])
              )
            )
          }
        }
      }
      // We now have 27 cuboids. First, remove all the ones that are outside
      // the initial cuboid or have zero size.
      val onlyInside =
        allTwentySeven.filter { this.intersects(it) && it.size() != 0L }.toSet()

      // Next, there should be a single cuboid that overlaps the 'other'.
      // Remove it from the set.
      return onlyInside.filter { !other.intersects(it) }.toSet()
    }

    fun intersects(other: Cuboid) = contains(other) || other.contains(this)

    fun contains(other: Cuboid) =
      (other.from.x < this.to.x && other.to.x > this.from.x) &&
          (other.from.y < this.to.y && other.to.y > this.from.y) &&
          (other.from.z < this.to.z && other.to.z > this.from.z)

    fun size(): Long = (to.x - from.x) * (to.y - from.y) * (to.z - from.z)
  }

  private fun parseLine(line: String): Pair<Cuboid, Boolean> {
    val (onOff, x1, x2, y1, y2, z1, z2) = regex.find(line)!!.destructured
    return Pair(
      Cuboid(
        XYZ(x1.toLong(), y1.toLong(), z1.toLong()),
        XYZ(x2.toLong() + 1, y2.toLong() + 1, z2.toLong() + 1)
      ), onOff == "on"
    )
  }

  private data class XYZ(val x: Long, val y: Long, val z: Long)
}