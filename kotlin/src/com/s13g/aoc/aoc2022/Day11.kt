package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul
import com.s13g.aoc.resultFrom

/**
 * --- Day 11: Monkey in the Middle ---
 * https://adventofcode.com/2022/day/11
 */
class Day11 : Solver {
  override fun solve(lines: List<String>): Result {
    return resultFrom(
      solveFor(parse(lines), false),
      solveFor(parse(lines), true)
    )
  }

  private fun solveFor(monkeys: List<Monkey>, partB: Boolean): Long {
    val monkeyDivs = monkeys.map { it.divBy }.toList()
    for (i in 1..if (partB) 10_000 else 20) {
      for (monkey in monkeys) {
        for (item in monkey.items.toList()) {
          var worry = if (monkey.op.operator == "*") item * monkey.opNumOr(item)
          else item + monkey.opNumOr(item)
          monkey.numInspections++
          worry = if (partB) worry % monkeyDivs.mul() else worry / 3
          // Throw to other monkey.
          monkeys[if (worry % monkey.divBy == 0L) monkey.decisionMonkeys.first
          else monkey.decisionMonkeys.second].items.add(worry)
        }
        monkey.items.clear()
      }
    }
    return monkeys.map { it.numInspections }
      .sortedDescending().subList(0, 2).mul()
  }

  private fun parse(lines: List<String>): List<Monkey> {
    val result = mutableListOf<Monkey>()
    for ((id, mLines) in lines.windowed(6, 7).withIndex()) {
      val oSplit = mLines[2].substring(23).split(" ")
      val oNum = if (oSplit[1] == "old") Long.MAX_VALUE else oSplit[1].toLong()
      result.add(
        Monkey(
          id,
          mLines[1].substring(18).split(", ").map { it.toLong() }
            .toMutableList(),
          Op(oSplit[0], oNum),
          mLines[3].substring(21).toLong(),
          Pair(mLines[4].substring(29).toInt(), mLines[5].substring(30).toInt())
        )
      )
    }
    return result
  }

  data class Monkey(
    val id: Int,
    var items: MutableList<Long>,
    val op: Op,
    val divBy: Long,
    val decisionMonkeys: Pair<Int, Int>,
    var numInspections: Long = 0
  ) {
    fun opNumOr(other: Long) = op.num.takeIf { it != Long.MAX_VALUE } ?: other
  }

  data class Op(val operator: String, val num: Long)
}