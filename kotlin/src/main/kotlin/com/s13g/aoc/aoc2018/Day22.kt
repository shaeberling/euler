package com.s13g.aoc.aoc2018

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2018/day/22 */
class Day22 : Solver {
  override fun solve(lines: List<String>): Result {
    val depth = lines[0].substringAfter("depth: ").toInt()
    val targetCoords = lines[1].substringAfter("target: ").split(',')
    val tX = targetCoords[0].toInt()
    val tY = targetCoords[1].toInt()

    val creator = RegionCreator(depth)

    val cavern = hashMapOf<XY, Region>()
    // Times to to make room for paths in solution B.
    for (y in 0..tY * 2) {
      for (x in 0..tX * 2) {
        cavern[XY(x, y)] =
            if ((x == 0 && y == 0) || (x == tX && y == tY)) creator.create(0)
            else if (x == 0) {
              creator.create(y * 48271)
            } else if (y == 0) {
              creator.create(x * 16807)
            } else {
              creator.create(cavern[XY(x - 1, y)]!!.erosion *
                  cavern[XY(x, y - 1)]!!.erosion)
            }
      }
    }
//    printMap(cavern, tX * 2, tY * 2)
    val solutionA = riskLevel(cavern, tX, tY)

    // For part B we only really care about the types
    val cavernB = cavern.mapValues { (k, v) -> v.type }
    val pathFinder = PathFinder(cavernB, XY(tX, tY))
    val solutionB = pathFinder.findShortest()
    return Result("$solutionA", "$solutionB")
  }
}

private fun riskLevel(cavern: Map<XY, Region>, tX: Int, tY: Int): Int {
  var riskLevel = 0
  for (y in 0..tY) {
    for (x in 0..tX) {
      riskLevel += when (cavern[XY(x, y)]!!.type) {
        Type.ROCKY -> 0
        Type.WET -> 1
        Type.NARROW -> 2
      }
    }
  }
  return riskLevel
}

private fun printMap(cavern: Map<XY, Region>, tX: Int, tY: Int) {
  for (y in 0..tY) {
    for (x in 0..tX) {
      val r = cavern[XY(x, y)]
      print(when (r!!.type) {
        Type.ROCKY -> "."
        Type.WET -> "="
        Type.NARROW -> "|"
      })
    }
    print("\n")
  }
}

private class RegionCreator(val depth: Int) {
  fun create(geoIdx: Int): Region {
    val erosion = (geoIdx + depth) % 20183
    val type = typeFromInt(erosion % 3)
    return Region(geoIdx, erosion, type)
  }
}

private data class Region(val geoIdx: Int, val erosion: Int, val type: Type)

private data class XY(val x: Int, val y: Int)

private enum class Type {
  ROCKY, WET, NARROW
}

private fun typeFromInt(n: Int) = when (n) {
  0 -> Type.ROCKY
  1 -> Type.WET
  2 -> Type.NARROW
  else -> throw Exception("Invalid type number")
}

private enum class Tool {
  TORCH, CLIMBING, NEITHER
}

/** The state with which a traveler entered a region. */
private data class EnterState(val pos: XY, val tool: Tool)

private class Traveler {
  val pos = mutableListOf<EnterState>()
  val time = 0

  fun currentPos() = pos.last().pos
  fun currentTool() = pos.last().tool
}

private class PathFinder(val cavern: Map<XY, Type>, val targetLoc: XY) {
  val shortest = hashMapOf<XY, Int>()
  val travelers = arrayListOf<Traveler>()

  fun findShortest(): Int {
    val firstTraveler = Traveler()
    firstTraveler.pos.add(EnterState(XY(0, 0), Tool.TORCH))
    travelers.add(firstTraveler)

    while (!isTargetFound()) {
      // TODO: Continue here....
    }
    return 42
  }

  private fun toolsAllowed(t: Type) = when (t) {
    Type.ROCKY -> arrayListOf(Tool.CLIMBING, Tool.TORCH)
    Type.WET -> arrayListOf(Tool.CLIMBING, Tool.NEITHER)
    Type.NARROW -> arrayListOf(Tool.TORCH, Tool.NEITHER)
  }

  private fun isTargetFound(): Boolean {
    for (t in travelers) {
      if (t.currentPos() == targetLoc) {
        return true
      }
    }
    return false
  }
}