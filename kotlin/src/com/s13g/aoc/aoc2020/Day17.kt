package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 17: Conway Cubes ---
 * https://adventofcode.com/2020/day/17
 */
class Day17 : Solver {
  override fun solve(lines: List<String>): Result {
    val map = mutableMapOf<XYZW, Boolean>()
    for (y in lines.indices) {
      for (x in lines[y].indices) {
        map[XYZW(x, y)] = lines[y][x] == '#'
      }
    }
    var currentMap = map as Map<XYZW, Boolean>
    var currentMapB = map.toMap()
    for (x in 1..6) currentMap = runCycle(currentMap)
    for (x in 1..6) currentMapB = runCycle(currentMapB, true)

    return Result("${currentMap.values.count { it }}", "${currentMapB.values.count { it }}")
  }

  private fun runCycle(map: Map<XYZW, Boolean>, countW: Boolean = false): Map<XYZW, Boolean> {
    val result = mutableMapOf<XYZW, Boolean>()
    for (x in (map.minX() - 1)..(map.maxX() + 1)) {
      for (y in (map.minY() - 1)..(map.maxY() + 1)) {
        for (z in (map.minZ() - 1)..(map.maxZ() + 1)) {
          for (w in (if (countW) (map.minW() - 1) else 0)..(if (countW) (map.maxW() + 1) else 0)) {
            val pos = XYZW(x, y, z, w)
            val numNeighbors = countNeighbors(map, pos)
            if (map.isActive(pos) && (numNeighbors in 2..3)) result[pos] = true
            else result[pos] = !map.isActive(pos) && numNeighbors == 3
          }
        }
      }
    }
    return result
  }

  private fun countNeighbors(map: Map<XYZW, Boolean>, pos: XYZW): Int {
    var result = 0
    for (x in (pos.x - 1)..(pos.x + 1)) {
      for (y in (pos.y - 1)..(pos.y + 1)) {
        for (z in (pos.z - 1)..(pos.z + 1)) {
          for (w in (pos.w - 1)..(pos.w + 1)) {
            val pozz = XYZW(x, y, z, w)
            if (pos != pozz) {
              if (map.isActive(pozz)) result++
            }
          }
        }
      }
    }
    return result
  }

  private fun Map<XYZW, Boolean>.isActive(xyz: XYZW) = (xyz in this && this[xyz]!!)

  private fun Map<XYZW, Boolean>.minX() = this.keys.map { it.x }.min()!!
  private fun Map<XYZW, Boolean>.minY() = this.keys.map { it.y }.min()!!
  private fun Map<XYZW, Boolean>.minZ() = this.keys.map { it.z }.min()!!
  private fun Map<XYZW, Boolean>.minW() = this.keys.map { it.w }.min()!!

  private fun Map<XYZW, Boolean>.maxX() = this.keys.map { it.x }.max()!!
  private fun Map<XYZW, Boolean>.maxY() = this.keys.map { it.y }.max()!!
  private fun Map<XYZW, Boolean>.maxZ() = this.keys.map { it.z }.max()!!
  private fun Map<XYZW, Boolean>.maxW() = this.keys.map { it.w }.max()!!

  private data class XYZW(val x: Int, val y: Int, val z: Int = 0, val w: Int = 0)
}
