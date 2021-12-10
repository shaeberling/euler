package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.util.ArrayDeque

/**
 * --- Day 10: Syntax Scoring ---
 * https://adventofcode.com/2021/day/10
 */
class Day10 : Solver {
  override fun solve(lines: List<String>): Result {
    val result = lines.map { scanLine(it) }
    val partA = result.map { it.first }.sum()
    val bLines = result.map { it.second }.filter { it != 0L }.sorted().toList()
    val partB = bLines[(bLines.size / 2)]
    return Result("$partA", "$partB")
  }

  private fun scanLine(line: String): Pair<Long, Long> {
    val stack = ArrayDeque<Char>()
    for (ch in line) {
      if (ch == ']') {
        if (stack.last() == '[') stack.removeLast() else return Pair(57, 0)
      } else if (ch == ')') {
        if (stack.last() == '(') stack.removeLast() else return Pair(3, 0)
      } else if (ch == '}') {
        if (stack.last() == '{') stack.removeLast() else return Pair(1197, 0)
      } else if (ch == '>') {
        if (stack.last() == '<') stack.removeLast() else return Pair(25137, 0)
      } else {
        stack.add(ch)
      }
    }
    var result = 0L
    for (ch in stack.reversed()) {
      result *= 5
      if (ch == '(') result += 1
      if (ch == '[') result += 2
      if (ch == '{') result += 3
      if (ch == '<') result += 4
    }
    return Pair(0, result)
  }
}