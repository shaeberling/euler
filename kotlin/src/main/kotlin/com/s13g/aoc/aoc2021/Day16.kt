package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul
import com.s13g.aoc.toBinary

/**
 * --- Day 16: Packet Decoder ---
 * https://adventofcode.com/2021/day/16
 */
class Day16 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = Data(lines[0].map { it.toString().toInt(16).toBinary(4) }.reduce { all, it -> all + it })
    val versionNumbers = mutableListOf<Int>()
    val partB = parse(input, versionNumbers)
    val partA = versionNumbers.sum()
    return Result("$partA", "$partB")
  }

  private fun parse(input: Data, versionNumbers: MutableList<Int>): Long {
    versionNumbers.add(input.take(3).toInt(2))
    val typeId = input.take(3).toInt(2)

    val value: Long
    if (typeId == 4) {  // Literal Packet
      var literalStr = ""
      while (true) {
        val lastGroup = input.take(1) == "0"
        literalStr += input.take(4)
        if (lastGroup) break;
      }
      value = literalStr.toLong(2)
    } else {  // Operator Packet
      val lengthTypeId = input.take(1)
      var totalLength = Int.MAX_VALUE
      var numSubPackets = Int.MAX_VALUE
      if (lengthTypeId == "0") {
        totalLength = input.take(15).toInt(2)
      } else {
        numSubPackets = input.take(11).toInt(2)
      }
      // Parse sub-packets
      val subPacketsStart = input.idx
      val subResults = mutableListOf<Long>()
      while ((input.idx - subPacketsStart) < totalLength && subResults.size < numSubPackets) {
        subResults.add(parse(input, versionNumbers))
      }
      value = when (typeId) {
        0 -> subResults.sum()
        1 -> subResults.mul()
        2 -> subResults.min()!!
        3 -> subResults.max()!!
        5 -> if (subResults[0] > subResults[1]) 1 else 0
        6 -> if (subResults[0] < subResults[1]) 1 else 0
        7 -> if (subResults[0] == subResults[1]) 1 else 0
        else -> error("Unknown typeId: $typeId")
      }
    }
    return value
  }

  private class Data(val input: String, var idx: Int = 0) {
    fun take(num: Int): String {
      idx += num
      return this.input.substring(idx - num, idx)
    }
  }
}