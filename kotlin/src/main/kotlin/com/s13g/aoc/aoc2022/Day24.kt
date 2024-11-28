package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.resultFrom
import java.lang.RuntimeException

/**
 * --- Day 24: Blizzard Basin ---
 * https://adventofcode.com/2022/day/24
 */
class Day24 : Solver {
  enum class Type { UP, DOWN, LEFT, RIGHT, WALL }
  private data class Blizzard(val pos: XY, val type: Type)

  override fun solve(lines: List<String>): Result {
    val map = mutableListOf<Blizzard>()
    for ((y, line) in lines.withIndex()) {
      for (x in line.indices) {
        if (line[x] == '.') continue
        val dir = when (line[x]) {
          '>' -> Type.RIGHT
          '<' -> Type.LEFT
          '^' -> Type.UP
          'v' -> Type.DOWN
          '#' -> Type.WALL
          else -> throw RuntimeException("Uh oh")
        }
        map.add(Blizzard(XY(x, y), dir))
      }
    }
    val world = World(map, lines[0].length, lines.size)
    val end = XY(lines[0].length - 2, lines.size - 1)
    val partA = sim(setOf(XY(1, 0)), world, end)
    val partB1 = sim(setOf(end), partA.first, XY(1, 0))
    val partB2 = sim(setOf(XY(1, 0)), partB1.first, end)

    return resultFrom(
      partA.second, partA.second + partB1.second + partB2.second
    )
  }

  private fun sim(
    initPos: Set<XY>, initWorld: World, end: XY
  ): Pair<World, Int> {
    var world = initWorld
    val positions = initPos.toMutableSet()
    var minute = 0
    while (end !in positions) {
      val nextWorld = world.next()
      val posCopy = positions.toSet()
      positions.clear()
      for (pos in posCopy) {
        positions.addAll(setOf(
          pos, // Wait
          XY(pos.x - 1, pos.y),
          XY(pos.x + 1, pos.y),
          XY(pos.x, pos.y - 1),
          XY(pos.x, pos.y + 1)
        ).filter { nextWorld.isFree(it) })
      }
      world = nextWorld
      minute++
    }
    return Pair(world, minute)
  }

  private data class World(
    val map: List<Blizzard>,
    val width: Int,
    val height: Int
  ) {

    fun isFree(pos: XY) =
      map.count { it.pos == pos } == 0 &&
          pos.x in 0 until width &&
          pos.y in 0 until height

    fun next(): World {
      val result = mutableListOf<Blizzard>()

      for (item in map) {
        if (item.type == Type.UP) {
          val newY = if (item.pos.y > 1) item.pos.y - 1 else height - 2
          result.add(Blizzard(XY(item.pos.x, newY), Type.UP))
        } else if (item.type == Type.DOWN) {
          val newY = if (item.pos.y < (height - 2)) item.pos.y + 1 else 1
          result.add(Blizzard(XY(item.pos.x, newY), Type.DOWN))
        } else if (item.type == Type.LEFT) {
          val newX = if (item.pos.x > 1) item.pos.x - 1 else width - 2
          result.add(Blizzard(XY(newX, item.pos.y), Type.LEFT))
        } else if (item.type == Type.RIGHT) {
          val newX = if (item.pos.x < (width - 2)) item.pos.x + 1 else 1
          result.add(Blizzard(XY(newX, item.pos.y), Type.RIGHT))
        } else if (item.type == Type.WALL) {
          result.add(item)
        }
      }
      return World(result, width, height)
    }

    fun toMap() = map.associate { it.pos to it.type }.toMap()

    fun print(dude: XY) {
      val locMap = toMap()
      for (y in 0 until height) {
        for (x in 0 until width) {
          val pos = XY(x, y)
          if (dude == pos) print('E')
          else if (pos !in locMap) print('.')
          else if (locMap[pos] == Type.UP) print('^')
          else if (locMap[pos] == Type.DOWN) print('v')
          else if (locMap[pos] == Type.LEFT) print('<')
          else if (locMap[pos] == Type.RIGHT) print('>')
          else if (locMap[pos] == Type.WALL) print('#')
        }
        print("\n")
      }
      print("\n")
    }
  }
}