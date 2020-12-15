package com.s13g.aoc.aoc2020

import com.s13g.aoc.*

/**
 * --- Day 14: Docking Data ---
 * https://adventofcode.com/2020/day/14
 */

val memRegex = """mem\[(\d+)\] = (\d+)$""".toRegex()

class Day14 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = parseInput(lines)

    // Part A: Use Mask to change the value. Leave address unchanged.
    val memA = mutableMapOf<Int, Long>()
    for (assignment in input) {
      val newValue = String(decTo36Bin(assignment.value).mapIndexed { idx, it ->
        when (assignment.mask[idx]) {
          '0' -> '0'
          '1' -> '1'
          else -> it
        }
      }.toCharArray()).toLong(2)
      memA[assignment.addr] = newValue
    }

    // Part B: Use mask to generate more addresses. Leave value unchanged.
    val memB = mutableMapOf<Long, Long>()
    for (assignment in input) {
      val newAddrStr = String(decTo36Bin(assignment.addr).mapIndexed { idx, it ->
        when (assignment.mask[idx]) {
          '0' -> it
          '1' -> '1'
          else -> 'X'
        }
      }.toCharArray())

      val addresses = mutableListOf<Long>()
      genAllFloatingAddresses(newAddrStr.toCharArray(), addresses)
      addresses.forEach { memB[it] = assignment.value.toLong() }
    }
    return Result("${memA.values.sum()}", "${memB.values.sum()}")
  }

  private fun genAllFloatingAddresses(str: CharArray, result: MutableList<Long>) {
    if (str.count { it == 'X' } == 0) result.add(String(str).toLong(2))

    for (s in str.indices) {
      if (str[s] == 'X') {
        // Generate two versions with the first X replaced with 0 and 1. Apply recursively.
        genAllFloatingAddresses(str.mapIndexed { i, c -> if (i == s) '0' else c }.toCharArray(), result)
        genAllFloatingAddresses(str.mapIndexed { i, c -> if (i == s) '1' else c }.toCharArray(), result)
        break;
      }
    }
  }

  /** Take an integer and produce a (if needed padded) 36 long binary representation of it. */
  private fun decTo36Bin(v: Int) = Integer.toBinaryString(v).padStart(36, '0')

  /** Parse input into list of MemAction which holds all the info needed. */
  private fun parseInput(lines: List<String>): List<MemAction> {
    val result = mutableListOf<MemAction>()
    var mask = ""
    for (line in lines) {
      // Lines are either a new mask or memory assignments.
      if (line.startsWith("mask")) {
        mask = line.substring(7)
      } else {
        val (addr, value) = memRegex.find(line)!!.destructured
        result.add(MemAction(mask, addr.toInt(), value.toInt()))
      }
    }
    return result
  }

  private class MemAction(val mask: String, val addr: Int, val value: Int)
}
