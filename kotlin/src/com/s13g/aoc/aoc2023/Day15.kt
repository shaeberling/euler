package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 15: Lens Library ---
 * https://adventofcode.com/2023/day/15
 */
class Day15 : Solver {
  override fun solve(lines: List<String>): Result {
    fun String.hash() = this.map { ch -> ch.code }.fold(0) { acc, c ->
      ((acc + c) * 17) % 256
    }

    val resultA = lines[0].split(",").sumOf { it.hash() }
    val boxes = MutableList(256) { LinkedHashMap<String, Int>() }

    val re = """^([a-z]+)(.)([0-9]*)$""".toRegex()
    lines[0].split(",").forEach { str ->
      val result = re.find(str)!!.groupValues
      val lens = result[1]
      val op = result[2]
      val fl = result[3].toIntOrNull() ?: 0
      val box = lens.hash()
      if (op == "=") boxes[box][lens] = fl
      else if (op == "-") boxes.forEach { it.remove(lens) }
    }

    val resultB = boxes.mapIndexed { b, box ->
      box.entries.mapIndexed { l, lens -> (b + 1) * (l + 1) * lens.value }
        .sum()
    }.sum()
    return resultFrom(resultA, resultB)
  }
}