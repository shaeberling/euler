package com.s13g.aoc.aoc2018

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2018/day/1 */
class Day1 : Solver {
  override fun solve(data: List<String>): Result {
    val resultA = data.sumBy { s -> Integer.parseInt(s) }
    return Result(resultA.toString(),
        getFirstDoubleSum(data.map { s -> Integer.parseInt(s) }))
  }

  fun getFirstDoubleSum(values: List<Int>): String {
    val sums = HashSet<Int>()
    var sum = 0
    while (true) {
      for (x in values) {
        sum += x
        if (sums.contains(sum)) {
          return sum.toString()
        }
        sums.add(sum)
      }
    }
  }
}