package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 2: Password Philosophy ---
 * https://adventofcode.com/2020/day/2
 */
class Day2 : Solver {
  private val regex = """(\d+)-(\d+) (.): (.*)""".toRegex()

  override fun solve(lines: List<String>): Result {
    val input = lines.map { parse(it) }
    val resultA = input.map { it.isLegalA() }.count { it }
    val resultB = input.map { it.isLegalB() }.count { it }
    return Result("$resultA", "$resultB")
  }

  private fun parse(row: String): Rule {
    val (a, b, ch, pass) = row.match(regex)
    return Rule(a.toInt(), b.toInt(), ch[0], pass)
  }

  private data class Rule(val a: Int, val b: Int, val ch: Char, val pass: String)

  private fun Rule.isLegalA() = pass.filter { it == ch }.count().let { it in a..b }
  private fun Rule.isLegalB() = (pass.length >= a && pass[a - 1] == ch) xor (pass.length >= b && pass[b - 1] == ch)
}

private fun String.match(r: Regex) = r.find(this)!!.destructured