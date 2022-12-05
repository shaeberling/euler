package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 5: Supply Stacks ---
 * https://adventofcode.com/2022/day/5
 */
class Day5 : Solver {
  override fun solve(lines: List<String>): Result {
    val re = """move (\d+) from (\d+) to (\d+)$""".toRegex()
    val stacks = parseStacks(lines)

    for (line in lines.filter { it.contains("move") }) {
      val (num, from, to) = re.find(line)!!.destructured
      for (i in 0..1) {
        val rem =
          (1..num.toInt()).map { stacks[i][from.toInt() - 1].removeLast() }
            .toList()
        stacks[i][to.toInt() - 1].addAll(if (i == 0) rem else rem.asReversed())
      }
    }
    return Result(
      stacks[0].map { it.last() }.joinToString(""),
      stacks[1].map { it.last() }.joinToString("")
    )
  }

  private fun parseStacks(lines: List<String>): List<List<MutableList<Char>>> {
    val stacks = (0..8).map { mutableListOf<Char>() }.toList()
    for (row in 7 downTo 0) {
      (0..8)
        .map { stackNo -> Pair(stackNo, lines[row][stackNo * 4 + 1]) }
        .filter { it.second != ' ' }
        .forEach { stacks[it.first].add(it.second) }
    }
    return listOf(stacks, stacks.map { it.toMutableList() }.toList())
  }
}