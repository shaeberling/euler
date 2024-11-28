package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/**
 * --- Day 16: Flawed Frequency Transmission ---
 * https://adventofcode.com/2019/day/16
 */
class Day16 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines[0].map { "$it".toInt() }
    val patterns = genPatterns(listOf(0, 1, 0, -1), input.size)
    println("Patterns generated")

    var state = input
    for (n in 1..100) {
      state = phaseStep(state, patterns)
    }
    val resultA = state.subList(0, 8).map { "$it" }.reduce { acc, i -> acc + i }

    // Repeat input 10.000 times for PartB
//    val inputB = mutableListOf<Int>()
//    for (n in 1..10000) {
//      inputB.addAll(input)
//    }
//    var stateB = inputB.toList()
//    for (n in 1..100) {
//      stateB = phaseStep(stateB, patterns)
//    }

    return Result(resultA, "n/a")
  }

  private fun phaseStep(input: List<Int>, patterns: List<List<Int>>): List<Int> {
    val output = mutableListOf<Int>()
    for (i in input.indices) {
      output.add(calcItem(input, patterns[i]))
    }
    return output
  }

  private fun calcItem(input: List<Int>, pattern: List<Int>): Int {
    var result = 0
    for ((i, v) in input.withIndex()) {
      result += (v * pattern[(i + 1) % pattern.size])
    }
    return abs(result % 10)
  }

  private fun genPatterns(base: List<Int>, num: Int): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    for (n in 0..num) {
      val pattern = mutableListOf<Int>()
      for (x in base) {
        for (i in 0..n) {
          pattern.add(x)
        }
      }
      result.add(pattern)
    }
    return result
  }
}