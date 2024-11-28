package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.min

/** https://adventofcode.com/2019/day/6 */
class Day6 : Solver {
  private var map = mutableMapOf<String, Node>()

  override fun solve(lines: List<String>): Result {
    map =
        lines.map { l -> l.split(")") }
            .map { s -> Pair(s[1], Node(s[1], s[0])) }.toMap().toMutableMap()
    augmentData()
    val solutionA = map.keys.map { n -> countOrbits(n) }.sum()
    val solutionB = distance(map["YOU"]!!.orb, map["SAN"]!!.orb, "YOU")
    return Result("$solutionA", "$solutionB")
  }

  private fun countOrbits(node: String): Int {
    return if (node == "COM") 0 else 1 + countOrbits(map[node]!!.orb)
  }

  private fun augmentData() {
    map["COM"] = Node("COM")
    for (entry in map.filter { !it.value.orb.isBlank() }) {
      entry.value.conns.add(map[entry.value.orb]!!)
      map[entry.value.orb]!!.conns.add(entry.value)
    }
  }

  private fun distance(from: String, to: String, orig: String): Int? {
    if (from == to) return 0
    var result: Int? = null
    for (conn in map[from]!!.conns.filter { it.name != orig && !it.name.isBlank() }) {
      result = bMin(result, distance(conn.name, to, from))
    }
    if (result != null) result++
    return result
  }

  private fun bMin(a: Int?, b: Int?): Int? {
    val min = min(a ?: Int.MAX_VALUE, b ?: Int.MAX_VALUE)
    return if (min == Int.MAX_VALUE) null else min
  }

  data class Node(val name: String, val orb: String = "") {
    val conns = mutableSetOf<Node>()
  }
}