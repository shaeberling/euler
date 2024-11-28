package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 3: Rucksack Reorganization ---
 * https://adventofcode.com/2022/day/1
 */
class Day3 : Solver {
  override fun solve(lines: List<String>): Result {
    val scoreFun =
      { ch: Char ->
        if (ch.isLowerCase()) ch.toInt() - 'a'.toInt() + 1
        else ch.toInt() - 'A'.toInt() + 27
      }

    val m1 = lines.map { line ->
      Pair(
        line.substring(0, line.length / 2),
        line.substring(line.length / 2, line.length)
      )
    }.sumBy {
      scoreFun(it.first.toSet().intersect(it.second.toSet()).first())
    }

    val m2 = lines.windowed(3, 3).map {
      it[0].toSet().intersect(it[1].toSet().intersect(it[2].toSet())).first()
    }.sumBy { scoreFun(it) }
    return resultFrom(m1, m2)
  }
}