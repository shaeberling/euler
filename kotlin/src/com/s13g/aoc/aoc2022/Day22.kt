package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.addTo
import com.s13g.aoc.resultFrom

/**
 * --- Day 22: Monkey Map ---
 * https://adventofcode.com/2022/day/22
 */
class Day22 : Solver {

  private val tileSize = 50  // 50 for the real input.
  private val deltas = listOf(XY(1, 0), XY(0, 1), XY(-1, 0), XY(0, -1))

  private enum class Dir { LEFT, RIGHT, NOTHING }
  private enum class Tile { WALL, NONE }

  private val mappings = genMappings()

  private fun genMappings(): Set<AreaMapping> {
    // Dir: 0=RIGHT, 1=DOWN, 2=LEFT, 3=UP
    val forward = setOf(
      AreaMapping( // A
        Area(XY(100, 50), XY(100, 99)),
        Area(XY(100, 50), XY(199, 50)),
        XY(0, 1), null, 0, 3
      ),
      AreaMapping( // B
        Area(XY(150, 0), XY(150, 49)),
        Area(XY(100, 100), XY(100, 149)),
        null, XY(0, -1), 0, 2
      ),
      AreaMapping( // C
        Area(XY(50, 150), XY(99, 150)),
        Area(XY(50, 150), XY(50, 199)),
        null, XY(1, 0), 1, 2
      ),
      AreaMapping( // D
        Area(XY(100, -1), XY(149, -1)),
        Area(XY(0, 200), XY(49, 200)),
        XY(1, 0), null, 3, 3
      ),
      AreaMapping( // E
        Area(XY(50, -1), XY(99, -1)),
        Area(XY(-1, 150), XY(-1, 199)),
        null, XY(1, 0), 3, 0
      ),
      AreaMapping( // F
        Area(XY(49, 0), XY(49, 49)),
        Area(XY(-1, 100), XY(-1, 149)),
        null, XY(0, -1), 2, 0
      ),
      AreaMapping( // G
        Area(XY(49, 50), XY(49, 99)),
        Area(XY(0, 99), XY(49, 99)),
        XY(0, 1), null, 2, 1
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
    start: XY,
    map: Map<XY, Tile>,
    steps: List<Step>,
    partA: Boolean
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
    // Walk it back one step (opposite direction)
    val oppDir = (dir + 2) % 4
    val origPos = pos.add(deltas[oppDir])
    val dX = origPos.x % tileSize
    val dY = origPos.y % tileSize
    for (am in mappings) {
      if (am.dirFrom == dir && am.from.isInside(pos)) {
        var newX = am.to.from.x
        var newY = am.to.from.y
        if (am.xFrom != null) {
          if (am.xFrom.x != 0) {
            newX += (if (am.xFrom.x == 1) dX else tileSize - dX - 1)
          } else if (am.xFrom.y != 0) {
            newX += if (am.xFrom.y == 1) dY else tileSize - dY - 1
          }
        }
        if (am.yFrom != null) {
          if (am.yFrom.x != 0) {
            newY += (if (am.yFrom.x == 1) dX else tileSize - dX - 1)
          } else if (am.yFrom.y != 0) {
            newY += if (am.yFrom.y == 1) dY else tileSize - dY - 1
          }
        }
        return Pair(XY(newX, newY), am.dirTo)
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

  private data class Step(
    val num: Int,
    val dir: Dir
  )

  private data class Area(val from: XY, val to: XY) {
    fun isInside(pos: XY) = pos.x in from.x..to.x && pos.y in from.y..to.y
  }

  private data class AreaMapping(
    val from: Area,
    val to: Area,
    val xFrom: XY?,
    val yFrom: XY?,
    val dirFrom: Int,
    val dirTo: Int,
  ) {
    fun inverse(): AreaMapping {
      var newXFrom: XY? = null
      var newYFrom: XY? = null
      if (xFrom != null) {
        if (xFrom.x != 0) newXFrom = XY(xFrom.x, 0)
        if (xFrom.y != 0) newYFrom = XY(xFrom.y, 0)
      }
      if (yFrom != null) {
        if (yFrom.x != 0) newXFrom = XY(0, yFrom.x)
        if (yFrom.y != 0) newYFrom = XY(0, yFrom.y)
      }
      return AreaMapping(
        to,
        from,
        newXFrom,
        newYFrom,
        (dirTo + 2) % 4,
        (dirFrom + 2) % 4
      )
    }
  }
}