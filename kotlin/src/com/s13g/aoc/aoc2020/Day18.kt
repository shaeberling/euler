package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 18: Operation Order ---
 * https://adventofcode.com/2020/day/18
 */
class Day18 : Solver {
  /** Either 'v' value or sub-group 'g' are allowed, but not both. */
  private data class Expr(val op: Char, val v: Long, val g: List<Expr> = emptyList())

  private fun Expr.doOp(other: Expr, prio: Char) =
      if (op == '+') solve(prio) + other.solve(prio) else solve(prio) * other.solve(prio)

  override fun solve(lines: List<String>): Result {
    val input = lines.map { Expr(' ', 0, parse(it.replace("\\s".toRegex(), ""))) }
    return Result("${input.map { it.solve(' ') }.sum()}", "${input.map { it.solve('+') }.sum()}")
  }

  /** Recursively parses the expression. */
  private fun parse(line: String): List<Expr> {
    val chain = mutableListOf<Expr>()
    var op = ' '
    var n = -1
    while (++n < line.length) {
      val s = line[n]
      when (s) {
        in listOf('+', '*') -> op = s
        '(' -> {
          val closeIdx = findMatchingClose(line.substring(n + 1))
          chain.add(Expr(op, 0, parse(line.substring(n + 1, n + 1 + closeIdx))))
          n += 1 + closeIdx
        }
        // Note: Input only contains single digit numbers.
        else -> chain.add(Expr(op, s.toString().toLong()))
      }
    }
    return chain
  }

  private fun Expr.solve(prio: Char) = if (this.g.isEmpty()) this.v else this.g.reduce(prio).v

  private fun List<Expr>.reduce(prio: Char): Expr {
    assert(this[0].op == ' ')
    var reduced = mutableListOf(this[0])
    if (prio != ' ') {
      for (n in 1 until this.size)
        if (this[n].op == prio) {
          reduced[reduced.lastIndex] = Expr(reduced.last().op, this[n].doOp(reduced.last(), prio))
        } else {
          reduced.add(this[n])
        }
    } else {
      reduced = this.toMutableList()
    }
    return reduced.reduce { acc, expr -> Expr(' ', expr.doOp(acc, prio)) }
  }

  private fun findMatchingClose(str: String): Int {
    var opened = 0
    for (n in str.indices) {
      if (str[n] == '(') opened++
      else if (str[n] == ')') if (--opened == -1) return n
    }
    error("Parsing error: Missing closing parenthesis.")
  }
}