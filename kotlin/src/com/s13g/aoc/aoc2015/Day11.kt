package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 11: Corporate Policy ---
 * https://adventofcode.com/2015/day/11
 */
class Day11 : Solver {
  private val badChars = setOf('i', 'o', 'l').map { it.code }.toSet()

  override fun solve(lines: List<String>): Result {
    val password = lines[0].map { it.code }.toMutableList()
    while (!isPasswordLegal(password)) incrementPassword(password)
    val m1 = password.map { it.toChar() }.joinToString("")

    incrementPassword(password)
    while (!isPasswordLegal(password)) incrementPassword(password)
    val m2 = password.map { it.toChar() }.joinToString("")

    return Result(m1, m2)
  }

  private fun incrementPassword(password: MutableList<Int>) {
    for (i in password.lastIndex downTo 0) {
      password[i] += 1
      if (password[i] <= 'z'.code) return
      else password[i] = 'a'.code
    }
  }

  private fun isPasswordLegal(password: List<Int>) =
    atLeastOneIncreasingStraight(password)
        && doesNotContainBadChars(password)
        && atLeastTwoNonOverlapPairs(password)

  private fun atLeastOneIncreasingStraight(password: List<Int>) =
    password.windowed(3).any { it[2] == it[1] + 1 && it[1] == it[0] + 1 }

  private fun doesNotContainBadChars(password: List<Int>) =
    !password.any { badChars.contains(it) }

  private fun atLeastTwoNonOverlapPairs(password: List<Int>) =
    password.windowed(2).filter { it[0] == it[1] }.map { it[0] }
      .toSet().size >= 2
}