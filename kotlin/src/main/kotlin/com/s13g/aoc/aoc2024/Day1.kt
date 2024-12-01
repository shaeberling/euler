package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/**
 * --- Day 1: Historian Hysteria ---
 * https://adventofcode.com/2024/day/1
 */
class Day1 : Solver {
  override fun solve(lines: List<String>): Result {
    val re = """(\d+) {3}(\d+)""".toRegex()

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    for (line in lines) {
      val (n1, n2) = re.find(line)!!.destructured
      list1.add(n1.toInt())
      list2.add(n2.toInt())
    }
    val list1s = list1.sorted()
    val list2s = list2.sorted()

    var totalDistance = 0
    var similarityScore = 0
    for (i in 0 until list1s.size) {
      totalDistance += abs(list1s[i] - list2s[i])
      similarityScore += (list1s[i] * list2s.count { it == list1s[i] })
    }
    return Result("$totalDistance", "$similarityScore")
  }
}