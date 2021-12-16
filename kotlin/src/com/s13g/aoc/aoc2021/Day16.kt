package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.mul

/**
 * --- Day 16: Packet Decoder ---
 * https://adventofcode.com/2021/day/16
 */
class Day16 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines[0].map { it.toString().toInt(16) }.map { it.toBinary() }.reduce { all, it -> all + it }
    val versionNumbers = mutableListOf<Int>()
    val partB = parse(0, input, versionNumbers).second
    val partA = versionNumbers.sum()
    return Result("$partA", "$partB")
  }

  fun parse(idx_: Int, input: String, partA: MutableList<Int>): Pair<Int, Long> {
    var idx = idx_
    val version = input.substring(idx, idx + 3).toInt(2)
    partA.add(version)
    idx += 3
    val typeId = input.substring(idx, idx + 3).toInt(2)
    idx += 3

    var value = 0L
    if (typeId == 4) {
      // Literal Packet
      var literalStr = ""
      while (true) {
        val lastGroup = input[idx] == '0'
        idx++
        literalStr += input.substring(idx, idx + 4)
        idx += 4
        if (lastGroup) break;
      }
      value = literalStr.toLong(2)
    } else {
      // Operator Packet
      val lengthTypeId = input[idx]
      idx++

      var totalLength = Int.MAX_VALUE
      var numSubPackets = Int.MAX_VALUE
      if (lengthTypeId == '0') {
        totalLength = input.substring(idx, idx + 15).toInt(2)
        idx += 15
      } else {
        numSubPackets = input.substring(idx, idx + 11).toInt(2)
        idx += 11
      }
      // Parse sub-packets
      val subPacketsStart = idx
      var numSubs = 0
      val subResults = mutableListOf<Long>()
      while ((idx - subPacketsStart) < totalLength && numSubs < numSubPackets) {
        val ret = parse(idx, input, partA)
        idx = ret.first
        subResults.add(ret.second)
        numSubs++
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
    return Pair(idx, value)
  }

  private fun Int.toBinary(): String {
    return String.format("%4s", this.toString(2)).replace(" ".toRegex(), "0")
  }
}