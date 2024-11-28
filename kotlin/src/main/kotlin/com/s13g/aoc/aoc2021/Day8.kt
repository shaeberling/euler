package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 8: Seven Segment Search ---
 * https://adventofcode.com/2021/day/8
 */
class Day8 : Solver {
  override fun solve(lines: List<String>): Result {
    var partA = 0
    for (line in lines) {
      val parsed = parseLine(line)
      partA += parsed.output.map { it.length }
        .count { length -> length == 2 || length == 3 || length == 4 || length == 7 }
    }
    val partB = lines.map { parseLine(it) }.map { calcLine(it.input, it.output) }.sum()
    return Result("$partA", "$partB")
  }

  private fun calcLine(inputs: List<String>, outputs: List<String>): Int {
    assert(inputs.size == 10)  // Important constraints of the input, every position is unique.
    val inputCandidates = MutableList(inputs.size) { mutableSetOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) }
    val digitToCode = mutableMapOf<Int, String>()  // Digits for which we definitely know the code.

    // Called to lock in a digit. Remove from all candidate sets except the one, and set code.
    val lockDigit = { inputIdx: Int, digit: Int, code: String ->
      inputCandidates.forEach { it.remove(digit) }
      inputCandidates[inputIdx] = mutableSetOf(digit)
      digitToCode[digit] = code.sorted()
    }

    // If we compare a locked number with codes of given length, lock digit if overlap matches.
    val reduce = { num: Int, length: Int, input: String, i: Int, overlap: Int, digit: Int ->
      if (digitToCode.containsKey(num) && input.length == length) {
        if (digitToCode[num]!!.toSet().intersect(input.toSet()).size == overlap) {
          lockDigit(i, digit, input)
        }
      }
    }

    // Lock in the unique numbers first.
    for ((i, input) in inputs.withIndex()) {
      if (input.length == 2) lockDigit(i, 1, input)
      if (input.length == 3) lockDigit(i, 7, input)
      if (input.length == 4) lockDigit(i, 4, input)
      if (input.length == 7) lockDigit(i, 8, input)
    }

    // Reduce until we have exactly one candidate per input position.
    while (inputCandidates.count { it.size != 1 } != 0) {
      for ((i, input) in inputs.withIndex()) {
        reduce(1, 5, input, i, 2, 3)
        reduce(7, 6, input, i, 2, 6)
        reduce(4, 6, input, i, 4, 9)
        reduce(4, 5, input, i, 2, 2)
        reduce(6, 5, input, i, 5, 5)
      }
    }

    // Ensure all codes are set.
    for ((idx, digit) in inputCandidates.withIndex()) {
      digitToCode[digit.first()] = inputs[idx].sorted()
    }
    // Create map from code to digit and use it to construct final output result.
    val codeToDigit = digitToCode.map { it.value to it.key }.toMap()
    return outputs.map { it.sorted() }.map { codeToDigit[it] }.joinToString("").toInt()
  }

  private fun parseLine(str: String): Line {
    val split = str.split(" | ")
    return Line(split[0].split(" "), split[1].split(" "))
  }

  private data class Line(val input: List<String>, val output: List<String>)

  private fun String.sorted() = this.toCharArray().sorted().joinToString("")
}