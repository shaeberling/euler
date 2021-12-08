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
    //  0000
    // 1    2
    // 1    2
    //  3333
    // 4    5
    // 4    5
    //  6666

    // Unique lengths: 1(2), 7(3), 4(4), 8(7)
    val digitsToSegments = listOf(
      setOf(0, 1, 2, 4, 5, 6), setOf(2, 5), setOf(0, 2, 3, 4, 6),
      setOf(0, 2, 3, 5, 6), setOf(1, 2, 3, 5), setOf(0, 1, 3, 5, 6), setOf(0, 1, 3, 4, 5, 6),
      setOf(0, 2, 5), setOf(0, 1, 2, 3, 4, 5, 6), setOf(0, 1, 2, 3, 5, 6)
    )

    val segmentsToDigits = MutableList(7) { mutableSetOf<Int>() }
    for (d in digitsToSegments.indices) {
      for (segment in digitsToSegments[d]) {
        segmentsToDigits[segment].add(d)
      }
    }

    // Set initial candidates list based on length.
    val numSegmentsToDigits = MutableList<MutableSet<Int>>(7) { mutableSetOf() }
    digitsToSegments.withIndex().forEach { numSegmentsToDigits[it.value.size - 1].add(it.index) }
    val inputCandidates = MutableList(inputs.size) { setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) }
    for (i in inputs.indices) {
      inputCandidates[i] = numSegmentsToDigits[inputs[i].length - 1]
    }
    val segmentLetterCandidates = MutableList(7) { setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') }
    val finishedDigitLetters = MutableList(10) { setOf<Char>() }

    // Loop until every input has only a single candidate left.
    while (inputCandidates.count { it.size != 1 } != 0) {
      for (i in inputs.indices) {
        val input = inputs[i]
        val chars = input.toCharArray().toSet()

        // Custom rules to reduce the options based on overlapping segments.
        val reduce = { primDigit: Int, numSegs: Int, numOverlap: Int, result: Int ->
          if (finishedDigitLetters[primDigit].isNotEmpty() && input.length == numSegs) {
            if (chars.count { finishedDigitLetters[primDigit].contains(it) } == numOverlap) {
              inputCandidates[i] = setOf(result)
            } else {
              inputCandidates[i] = inputCandidates[i].minus(result)
            }
          }
        }
        reduce(1, 5, 2, 3)
        reduce(7, 6, 2, 6)
        reduce(4, 6, 4, 9)
        reduce(4, 5, 2, 2)

        for (o in inputCandidates.indices) {
          // If an input has only a single candidate left, it is set.
          if (inputCandidates[o].size == 1) {
            val digit = inputCandidates[o].first()
            val digitLetters = inputs[o].toCharArray().toSet()
            val segsNotInDigit = digitsToSegments[8].subtract(digitsToSegments[digit])
            for (seg in digitsToSegments[digit]) segmentLetterCandidates[seg] =
              segmentLetterCandidates[seg].intersect(digitLetters)
            for (seg in segsNotInDigit) segmentLetterCandidates[seg] =
              segmentLetterCandidates[seg].subtract(digitLetters)
            finishedDigitLetters[digit] = digitLetters
          }
        }
      }
    }

    val letterToSegment = mutableMapOf<Char, Int>()
    for ((segment, letters) in segmentLetterCandidates.withIndex()) {
      assert(letters.size == 1)
      letterToSegment[letters.first()] = segment
    }
    return decodeOutputPartB(outputs, letterToSegment, digitsToSegments)
  }

  private fun decodeOutputPartB(outs: List<String>, letterToSeg: Map<Char, Int>, digitsToSegs: List<Set<Int>>): Int {
    var result = ""
    for (out in outs) {
      val segments = out.map { ch -> letterToSeg[ch] }.toSet()
      digitsToSegs.withIndex().filter { segments == it.value }.map { it.index.toString() }.forEach { result += it }
    }
    return result.toInt()
  }

  private fun parseLine(str: String): Line {
    val split = str.split(" | ")
    return Line(split[0].split(" "), split[1].split(" "))
  }

  private data class Line(val input: List<String>, val output: List<String>)
}