package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/**
 * --- Day 5: Hydrothermal Venture ---
 * https://adventofcode.com/2021/day/5
 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = parse(lines)
    val area = mutableListOf<Point>()
    for (i in input) {
      val validForA = i.a.x == i.b.x || i.a.y == i.b.y
      val xDir = if (i.b.x != i.a.x) (i.b.x - i.a.x) / abs(i.b.x - i.a.x) else 0
      val yDir = if (i.b.y != i.a.y) (i.b.y - i.a.y) / abs(i.b.y - i.a.y) else 0
      val p = Point(i.a.x, i.a.y)
      area.add(Point(p.x, p.y, validForA))
      while (p.x != i.b.x || p.y != i.b.y) {
        p.x += xDir
        p.y += yDir
        area.add(Point(p.x, p.y, validForA))
      }
    }
    val partA = area.filter { it.partA }.groupBy { it }.count { it.value.size > 1 }
    val partB = area.groupBy { "${it.x},${it.y}" }.count { it.value.size > 1 }
    return Result("$partA", "$partB")
  }

  private data class Point(var x: Int, var y: Int, val partA: Boolean = false)
  private data class Line(val a: Point, val b: Point)

  private fun parse(lines: List<String>): List<Line> {
    val regex = """([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)""".toRegex()
    return lines.map { regex.find(it)!!.destructured }
      .map { (x1, y1, x2, y2) -> Line(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt())) }
  }
}
