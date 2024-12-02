package com.s13g.aoc.aoc2024

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.abs

/**
 * --- Day 2: Red-Nosed Reports ---
 * https://adventofcode.com/2024/day/2
 */
class Day2 : Solver {
  override fun solve(lines: List<String>): Result {
    var input = lines.map { it.split(" ") }.map { it.map { it.toInt() } }
    var result1 = input.count { isSafe(it) }
    var result2 = input.count { isSafe2(it) }
    return Result("$result1", "$result2")
  }

  private fun isSafe(nums: List<Int>): Boolean {
    var goingUp = nums[0] < nums[1]
    for (i in 0 until nums.size - 1) {
      val nextI = i + 1
      if ((abs(nums[i] - nums[nextI]) !in 1..3) ||
        (nums[i] < nums[nextI] != goingUp)
      ) {
        return false
      }
    }
    return true
  }

  private fun isSafe2(nums: List<Int>): Boolean {
    if (isSafe(nums)) return true;
    // Remove every single element from the list and see if it's legal now.
    for (i in 0 until nums.size) {
      if (isSafe(removeElement(i, nums))) return true
    }
    return false
  }

  /** Returns a list that has the n-th element from the input removed. */
  private fun removeElement(n: Int, input: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0 until input.size) {
      if (i != n) result.add(input[i])
    }
    return result
  }
}