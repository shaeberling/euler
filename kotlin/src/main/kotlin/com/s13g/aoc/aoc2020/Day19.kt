package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 19: Monster Messages ---
 * https://adventofcode.com/2020/day/19
 */
class Day19 : Solver {
  private fun parseRule(str: String): Pair<Int, Val> {
    val split = str.split(": ")
    val ruleNo = split[0].toInt()
    return if (split[1].contains('"')) Pair(ruleNo, Val(emptyList(), split[1][1]))
    else Pair(ruleNo, Val(split[1].split(" | ").map { list -> list.split(' ').map { it.toInt() } }.toList()))
  }

  override fun solve(lines: List<String>): Result {
    // Parse the Input
    val rules = mutableMapOf<Int, Val>()
    val messages = mutableListOf<String>()
    for (line in lines) {
      if (line.contains(":")) parseRule(line).apply { rules[first] = second }
      else if (line.isNotBlank()) messages.add(line)
    }
    // Solve as is for Part 1: Find all matching messages.
    val resultA = messages.map { m -> m in rules[0]!!.eval("", m, rules) }.count { it }

    // Patch the rules, which will introduce loops in Part 2
    listOf("8: 42 | 42 8", "11: 42 31 | 42 11 31").forEach { parseRule(it).apply { rules[first] = second } }
    val resultB = messages.map { m -> m in rules[0]!!.eval("", m, rules) }.count { it }
    return Result("$resultA", "$resultB")
  }

  // A value either as a concrete value 'v' or needs to be evaluated through its reference lists.
  private data class Val(val ref: List<List<Int>>, val v: Char = ' ')

  private fun Val.eval(base: String, match: String, rules: Map<Int, Val>): Set<String> {
    if (v != ' ') return setOf("$base$v")  // Return early if this is a concrete value.
    val result = mutableSetOf<String>()
    for (list in ref) {  // Create all permutations using all the reference lists.
      var str = mutableListOf(base)
      for (r in list) {
        val bak = str
        str = mutableListOf()  // Empty str and fill it with all combinations.
        for (s in bak) {  // Only keep matching candidates.
          rules[r]!!.eval(s, match, rules).filter { match.startsWith(it) }.forEach { str.add(it) }
        }
      }
      result.addAll(str)
    }
    return result
  }
}