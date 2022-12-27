package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.addTo
import com.s13g.aoc.aocRange
import com.s13g.aoc.resultFrom

/**
 * --- Day 22: Monkey Map ---
 * https://adventofcode.com/2022/day/22
 */
class Day22 : Solver {
  private val deltas = listOf(XY(1, 0), XY(0, 1), XY(-1, 0), XY(0, -1))

  private enum class Dir { LEFT, RIGHT, NOTHING }
  private enum class Tile { WALL, NONE }

  private val mappings = genMappings()

  private fun genMappings(): Set<AreaMapping> {
    // Dir: 0=RIGHT, 1=DOWN, 2=LEFT, 3=UP
    val forward = setOf(
      AreaMapping( // A
        Area(XY(100, 50), XY(100, 99)),
        Area(XY(100, 50), XY(199, 50)), 0, 3
      ),
      AreaMapping( // B
        Area(XY(150, 0), XY(150, 49)),
        Area(XY(100, 149), XY(100, 100)), 0, 2
      ),
      AreaMapping( // C
        Area(XY(50, 150), XY(99, 150)),
        Area(XY(50, 150), XY(50, 199)), 1, 2
      ),
      AreaMapping( // D
        Area(XY(100, -1), XY(149, -1)),
        Area(XY(0, 200), XY(49, 200)), 3, 3
      ),
      AreaMapping( // E
        Area(XY(50, -1), XY(99, -1)),
        Area(XY(-1, 150), XY(-1, 199)), 3, 0
      ),
      AreaMapping( // F
        Area(XY(49, 0), XY(49, 49)),
        Area(XY(-1, 149), XY(-1, 100)), 2, 0
      ),
      AreaMapping( // G
        Area(XY(49, 50), XY(49, 99)),
        Area(XY(0, 99), XY(49, 99)), 2, 1
      )
    )
    return forward.plus(forward.map { it.inverse() })
  }

  override fun solve(lines: List<String>): Result {
    val map = parseMap(lines.subList(0, lines.size - 2))
    val steps = parseDirections(lines.last())
    val start = map.keys.filter { it.y == 0 }.minBy { it.x }
    return resultFrom(
      solveB(start, map, steps, true),
      solveB(start, map, steps, false)
    )
  }

  private fun solveB(
    start: XY, map: Map<XY, Tile>, steps: List<Step>, partA: Boolean
  ): Int {
    var pos = start
    val maxX = map.keys.maxOf { it.x }
    val maxY = map.keys.maxOf { it.y }
    var currentDir = 0  // 0=RIGHT, 1=DOWN, 2=LEFT, 3=UP
    for (step in steps) {
      for (mov in 1..step.num) {
        var newPos = pos.add(deltas[currentDir])
        var newDir = currentDir
        // We have fallen off the map! Figure out how to wrap around the cube.
        if (newPos !in map) {
          if (partA) { // Part A: Just wrap around the edge
            while (newPos !in map) {
              newPos.addTo(deltas[currentDir])
              if (newPos.x < 0) newPos = XY(maxX, newPos.y)
              if (newPos.x > maxX) newPos = XY(0, newPos.y)
              if (newPos.y < 0) newPos = XY(newPos.x, maxY)
              if (newPos.y > maxY) newPos = XY(newPos.x, 0)
            }
          } else {
            // Make sure that there is an area mapping!
            val transportResult = transportB(newPos, currentDir)
            newPos = transportResult.first
            newDir = transportResult.second
            // Make sure to always take one step after transport to new
            // location to step ONTO the cube surface again.
            newPos = newPos.add(deltas[newDir])
          }
        }

        if (map[newPos] != Tile.WALL) {
          pos = newPos
          currentDir = newDir
        } else {
          break // It's not a wall AND on the surface. Step complete!
        }
      }
      if (step.dir == Dir.LEFT) currentDir-- else if (step.dir == Dir.RIGHT) currentDir++
      if (currentDir < 0) currentDir += 4
      if (currentDir > 3) currentDir -= 4
    }
    return 1000 * (pos.y + 1) + 4 * (pos.x + 1) + currentDir
  }

  private fun transportB(pos: XY, dir: Int): Pair<XY, Int> {
    for (am in mappings) {
      if (am.dirFrom == dir && am.from.isInside(pos)) {
        val fromXs = aocRange(am.from.from.x, am.from.to.x).toList()
        val fromYs = aocRange(am.from.from.y, am.from.to.y).toList()
        val toXs = aocRange(am.to.from.x, am.to.to.x).toList()
        val toYs = aocRange(am.to.from.y, am.to.to.y).toList()

        val fromXIdx = fromXs.indexOf(pos.x)
        val fromYIdx = fromYs.indexOf(pos.y)

        // Check if X get mapped to X, or to Y.
        return if (fromXs.size == toXs.size) {
          Pair(XY(toXs[fromXIdx], toYs[fromYIdx]), am.dirTo)
        } else {
          Pair(XY(toXs[fromYIdx], toYs[fromXIdx]), am.dirTo)
        }
      }
    }
    throw RuntimeException("Mapping not found for pos=$pos")
  }

  private fun parseMap(lines: List<String>): Map<XY, Tile> {
    val result = mutableMapOf<XY, Tile>()
    for ((y, line) in lines.withIndex()) {
      for ((x, ch) in line.withIndex()) {
        if (ch == ' ') continue
        result[XY(x, y)] = if (ch == '#') Tile.WALL else Tile.NONE
      }
    }
    return result
  }

  private fun parseDirections(line: String): List<Step> {
    val result = mutableListOf<Step>()
    var num = ""
    for (ch in line) {
      if (ch.isDigit()) num += ch
      else {
        result.add(Step(num.toInt(), if (ch == 'L') Dir.LEFT else Dir.RIGHT))
        num = ""
      }
    }
    result.add(Step(num.toInt(), Dir.NOTHING))
    return result
  }

  private data class Step(val num: Int, val dir: Dir)

  private data class Area(val from: XY, val to: XY) {
    fun isInside(pos: XY) =
      pos.x in aocRange(from.x, to.x) && pos.y in aocRange(from.y, to.y)
  }

  private data class AreaMapping(
    val from: Area,
    val to: Area,
    val dirFrom: Int,
    val dirTo: Int,
  ) {
    fun inverse() = AreaMapping(to, from, (dirTo + 2) % 4, (dirFrom + 2) % 4)
  }
}