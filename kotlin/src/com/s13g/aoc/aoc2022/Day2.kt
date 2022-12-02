package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 2: Rock Paper Scissors ---
 * https://adventofcode.com/2022/day/2
 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    val beats = mapOf(1 to 3, 2 to 1, 3 to 2)
    val m1 = lines.sumBy { score1(it, beats) }
    val m2 = lines.sumBy { score2(it, beats) }
    return resultFrom(m1, m2)
  }

  private fun score1(play: String, beats: Map<Int, Int>): Int {
    val p1 = play[0].toInt() - 'A'.toInt() + 1
    val p2 = play[2].toInt() - 'X'.toInt() + 1
    return score(p1, p2, beats)
  }

  private fun score2(play: String, beats: Map<Int, Int>): Int {
    val p1 = play[0].toInt() - 'A'.toInt() + 1

    val p2 = if (play[2] == 'Y') p1
    else if (play[2] == 'X') beats[p1]!!
    else beats.filter { it.value == p1 }.map { it.key }.first()

    return score(p1, p2, beats)
  }

  private fun score(p1: Int, p2: Int, beats: Map<Int, Int>): Int =
    if (p1 == p2) 3 + p2
    else if (beats[p1] == p2) p2
    else 6 + p2
}