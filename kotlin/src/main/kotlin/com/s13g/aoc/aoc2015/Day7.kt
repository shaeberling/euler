package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 7: Some Assembly Required ---
 * https://adventofcode.com/2015/day/7
 */
class Day7 : Solver {
  private val wires = mutableMapOf<String, Long>()
  override fun solve(lines: List<String>): Result {
    val m1 = solveFor(lines, null)
    wires.clear()
    val m2 = solveFor(lines, m1)
    return resultFrom(m1, m2)
  }

  private fun solveFor(lines: List<String>, overrideB: Long?): Long {
    val input = lines.toMutableSet()
    while (input.isNotEmpty()) {
      for (line in lines) {
        if (line !in input) continue
        val split = line.split(" -> ")
        assert(split.size == 2)
        val lhs = split[0].split(" ")
        val output = split[1]

        if (output == "b" && overrideB != null) wires[output] = overrideB
        else if (lhs.size == 1) {  // Set value
          val v0 = value(lhs[0]) ?: continue
          wires[output] = v0
        } else if (lhs.size == 2) {  // Always "NOT"
          val v1 = value(lhs[1]) ?: continue
          wires[output] = v1.inv()
        } else if (lhs.size == 3) {  // Operation with two operands
          val l = value(lhs[0]) ?: continue
          val r = value(lhs[2]) ?: continue
          val op = lhs[1]

          wires[output] = when (op) {
            "OR" -> l or r
            "AND" -> l and r
            "RSHIFT" -> l.shr(r.toInt())
            "LSHIFT" -> l.shl(r.toInt())
            else -> throw java.lang.RuntimeException("Unknown instruction ${wires[output]}")
          }
        }
        if (wires[output]!! < 0) wires[output] = wires[output]!! + 65536
        if (wires[output]!! > 65535) wires[output] = wires[output]!! - 65536
        input.remove(line)
      }
    }
    return wires["a"]!!
  }

  fun value(id: String): Long? {
    val num = id.toLongOrNull()
    if (num != null) return num
    if (id in wires) return wires[id]!!
    return null
  }
}
