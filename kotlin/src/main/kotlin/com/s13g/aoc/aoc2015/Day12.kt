package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import java.lang.Exception

/**
 * --- Day 12: Distress Signal ---
 * https://adventofcode.com/2015/day/12
 */
class Day12 : Solver {
  override fun solve(lines: List<String>): Result {
    return resultFrom(lines[0].sumOfNums(), editForPartB(lines[0]).sumOfNums())
  }

  private fun editForPartB(str: String): String {
    var newJson = str
    while (true) {
      val redPos = newJson.indexOf(":\"red\"")
      if (redPos == -1) return newJson

      var leftObjStart = redPos
      var level = 0
      while (level != -1) {
        leftObjStart--
        if (newJson[leftObjStart] == '}') level++
        if (newJson[leftObjStart] == '{') level--
      }
      var rightObjEnd = redPos
      level = 0
      while (level != -1) {
        rightObjEnd++
        if (newJson[rightObjEnd] == '{') level++
        if (newJson[rightObjEnd] == '}') level--
      }
      newJson = newJson.substring(0, leftObjStart) +
          newJson.substring(rightObjEnd + 1, newJson.length)
    }
  }

  private fun String.isNumber() = try {
    this.toInt();true
  } catch (ex: Exception) {
    false
  }

  private fun String.sumOfNums() =
    this.split(',', ':', '[', ']', '{', '}')
      .filter { it.isNumber() }.sumOf { it.toInt() }
}