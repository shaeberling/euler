package com.s13g.aoc.aoc2020


import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 9: Encoding Error ---
 * https://adventofcode.com/2020/day/9
 */
class Day9 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines.map { it.toLong() }
    val resultA = partA(25, input)
    val resultB = partB(resultA, input)
    return Result("$resultA", "$resultB")
  }
}

fun partA(preamble: Int, input: List<Long>): Long {
  for (i in preamble until input.size) {
    if (!isValid(i, input, preamble)) return input[i]
  }
  error("Cannot find solution for Part A")
}

fun isValid(i: Int, input: List<Long>, preamble: Int): Boolean {
  for (x in i - preamble until i) {
    for (y in x until i) {
      if (input[x] + input[y] == input[i]) return true
    }
  }
  return false
}

fun partB(toFind: Long, input: List<Long>): Long {
  for (i in input.indices) {
    val collect = mutableListOf<Long>()
    var sum = 0L
    for (j in i until input.size) {
      sum += input[j]
      collect.add(input[j])
      if (sum == toFind) return collect.min()!! + collect.max()!!
    }
  }
  error("Cannot find solution for Part B")
}