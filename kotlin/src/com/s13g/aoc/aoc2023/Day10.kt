package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.resultFrom
import kotlin.math.ceil

typealias Routes = List<String>

/**
 * --- Day 10: Pipe Maze ---
 * https://adventofcode.com/2023/day/10
 */
class Day10 : Solver {
  override fun solve(lines: Routes): Result {
    val routes = lines.toMutableList()
    val start = routes.findStart()
    routes[start.y] = routes[start.y].replace('S', routes.getStartTile(start))
    val path = routes.walk(start)
    val innerTiles = routes.getInner(path)
//    routes.print(path, innerTiles)
    return resultFrom(ceil(path.size / 2.0).toInt(), innerTiles.size)
  }

  private fun Routes.getInner(path: Set<XY>): Set<XY> {
    val innerTiles = mutableSetOf<XY>()
    val walls = setOf('F', '|', '7')
    for (y in this.indices) {
      var inside = false
      for (x in this[y].indices) {
        val pos = XY(x, y)
        val tile = if (pos in path) this[y][x] else '.'
        if (tile == '.' && inside) innerTiles.add(pos)
        if (tile in walls) inside = !inside
      }
    }
    return innerTiles
  }

  private fun Routes.walk(start: XY): Set<XY> {
    val path = mutableSetOf<XY>()
    // Find a valid starting route (no need to find them all, there should
    // just be two that connect start to the rest of the loop.
    var (pos, nextIsNot) = if (isValidConnection(start, Dir.RIGHT)) {
      Pair(start.add(Dir.RIGHT.delta), Dir.LEFT)
    } else if (isValidConnection(start, Dir.LEFT)) {
      Pair(start.add(Dir.LEFT.delta), Dir.RIGHT)
    } else if (isValidConnection(start, Dir.UP)) {
      Pair(start.add(Dir.UP.delta), Dir.DOWN)
    } else {
      Pair(start.add(Dir.DOWN.delta), Dir.UP)
    }
    path.add(pos)

    var dir = validOutDirs(pos).toMutableSet().minus(nextIsNot).first()
    var totalLength = 1
    do {
      if (isValidConnection(pos, dir)) {
        pos = pos.add(dir.delta)
        dir = validOutDirs(pos).minus(dir.opposite()).first()
      }
      path.add(pos)
      totalLength++
    } while (pos != start)
    return path
  }

  private fun Routes.findStart(): XY {
    for (y in this.indices) {
      for (x in this.indices) {
        if (this[y][x] == 'S') return XY(x, y)
      }
    }
    error("Cannot find start")
  }

  private fun Routes.getStartTile(start: XY): Char {
    val outs = mutableSetOf<Dir>()
    if (at(start.add(XY(1, 0))) in setOf('7', 'J', '-')) outs.add(Dir.RIGHT)
    if (at(start.add(XY(-1, 0))) in setOf('F', 'L', '-')) outs.add(Dir.LEFT)
    if (at(start.add(XY(0, 1))) in setOf('7', 'F', '|')) outs.add(Dir.DOWN)
    if (at(start.add(XY(0, -1))) in setOf('L', 'J', '|')) outs.add(Dir.UP)
    assert(outs.size == 2)  // Puzzle input allows for only two connections.
    return outsOfTile.keys.associateBy { outsOfTile[it] }[outs]
      ?: error("Cannot define start tile.")
  }

  private fun Routes.validOutDirs(pos: XY) =
    outsOfTile[at(pos)] ?: error("Unsupported node")

  private fun Routes.isValidConnection(from: XY, dir: Dir) =
    at(from.add(dir.delta)) in dir.incoming

  private fun Dir.opposite() =
    Dir.values()[(Dir.values().indexOf(this) + 2) % 4]

  private fun Routes.at(pos: XY) =
    if (pos.y in this.indices && pos.x in this[pos.y].indices) this[pos.y][pos.x] else '.'

  private fun Routes.print(path: Set<XY>, inside: Set<XY>) {
    for (y in this.indices) {
      for (x in this[y].indices) {
        val pos = XY(x, y)
        val ch =
          if (pos in path) this[y][x] else if (pos in inside) 'I' else '.'
        print(ch)
      }
      print("\n")
    }
  }

  val outsOfTile = mapOf(
    'F' to setOf(Dir.RIGHT, Dir.DOWN),
    'J' to setOf(Dir.UP, Dir.LEFT),
    'L' to setOf(Dir.UP, Dir.RIGHT),
    '7' to setOf(Dir.LEFT, Dir.DOWN),
    '-' to setOf(Dir.LEFT, Dir.RIGHT),
    '|' to setOf(Dir.UP, Dir.DOWN)
  )

  enum class Dir(val delta: XY, val incoming: Set<Char>) {
    RIGHT(XY(1, 0), setOf('J', '7', '-')),
    DOWN(XY(0, 1), setOf('L', 'J', '|')),
    LEFT(XY(-1, 0), setOf('F', 'L', '-')),
    UP(XY(0, -1), setOf('F', '7', '|'))
  }
}