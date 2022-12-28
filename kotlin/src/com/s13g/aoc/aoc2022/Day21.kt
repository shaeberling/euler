package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 21: Monkey Math ---
 * https://adventofcode.com/2022/day/21
 */
class Day21 : Solver {
  override fun solve(lines: List<String>): Result {
    val monkeysA = parse(lines)
    settle(monkeysA)
    val partA = monkeysA["root"]!!.value

    val monkeysB = parse(lines).toMutableMap()
    // Make it unsolvable by making it a dependency on itself.
    monkeysB["humn"] = Monkey("humn", Long.MAX_VALUE, "humn", "+", "humn")
    settle(monkeysB)

    // Figure out which side is solved and which one has HUMN in it.
    val rootLhs = monkeysB["root"]!!.lhsId
    val rootRhs = monkeysB["root"]!!.rhsId
    var solveFor =
      if (monkeysB[rootLhs]!!.isSolved()) monkeysB[rootLhs]!!.value else monkeysB[rootRhs]!!.value
    var toSolve =
      if (monkeysB[rootLhs]!!.isSolved()) monkeysB[rootRhs]!! else monkeysB[rootLhs]!!

    while (toSolve.id != "humn") {
      val lhs = monkeysB[toSolve.lhsId]!!
      val rhs = monkeysB[toSolve.rhsId]!!

      // If RHS is solved, simply reverse operation for the "solveFor" side.
      // If LHS is solved, we need to handle non-commutative operations - and /.
      if (rhs.isSolved()) {
        solveFor = doReverseOp(solveFor, toSolve.op, rhs.value)
        toSolve = lhs
      } else if (lhs.isSolved()) {
        if (toSolve.op in setOf("+", "*")) {
          solveFor = doReverseOp(solveFor, toSolve.op, lhs.value)
          toSolve = rhs
        } else if (toSolve.op == "-") {
          solveFor = (solveFor - lhs.value) * -1
          toSolve = rhs
        } else if (toSolve.op == "/") {
          // If this is a division but the unsolved part is the divisor, we have
          // to swap things around.
          val newSolveFor = lhs.value
          lhs.value = solveFor
          toSolve.op = reverseOp(toSolve.op)
          solveFor = newSolveFor
        } else throw RuntimeException("Unknown operation '${toSolve.op}'")
      } else throw RuntimeException("Neither side can be solved.")
//      println("$solveFor = ${buildGraph(toSolve.id, monkeysB)}")
    }
    return resultFrom(partA, solveFor)
  }

  private fun doReverseOp(lhs: Long, op: String, rhs: Long): Long {
    return when (op) {
      "+" -> lhs - rhs
      "*" -> lhs / rhs
      "-" -> lhs + rhs
      "/" -> lhs * rhs
      else -> throw RuntimeException("Waaa")
    }
  }

  private fun reverseOp(op: String): String {
    return when (op) {
      "+" -> "-"
      "*" -> "/"
      "-" -> "-"
      "/" -> "/"
      else -> throw RuntimeException("Unknown OP '$op'")
    }
  }

  private fun settle(monkeys: Map<String, Monkey>) {
    var complete = false
    while (!complete) {
      complete = true
      for (m in monkeys.values) {
        if (!m.isSolved() &&
          monkeys[m.lhsId]!!.isSolved() &&
          monkeys[m.rhsId]!!.isSolved()
        ) {
          val lhs = monkeys[m.lhsId]!!.value
          val rhs = monkeys[m.rhsId]!!.value
          m.value = when (m.op) {
            "+" -> lhs + rhs
            "*" -> lhs * rhs
            "-" -> lhs - rhs
            "/" -> lhs / rhs
            else -> throw RuntimeException("Waaa")
          }
          complete = false
        }
      }
    }
  }

  private fun parse(lines: List<String>) =
    lines.map { line ->
      val split = line.split(":")
      val id = split[0]
      var value = Long.MAX_VALUE
      var mathLeft = ""
      var op = ""
      var mathRight = ""
      val rhs = split[1].trim()
      try {
        value = rhs.toLong()
      } catch (ex: NumberFormatException) {
        val mathSplit = rhs.split(" ")
        mathLeft = mathSplit[0]
        op = mathSplit[1]
        mathRight = mathSplit[2]
      }
      Monkey(id, value, mathLeft, op, mathRight)
    }.associateBy { it.id }.toMap()

  private fun printGraph(id: String, monkeys: Map<String, Monkey>): String {
    if (id == "humn") return "HUMN"
    val m = monkeys[id]!!

    if (m.isSolved()) return m.value.toString()

    return "(${printGraph(m.lhsId, monkeys)} ${m.op} ${
      printGraph(
        m.rhsId,
        monkeys
      )
    })"
  }

  data class Monkey(
    val id: String,
    var value: Long,
    val lhsId: String = "",
    var op: String = "",
    val rhsId: String = "",
  ) {
    fun isSolved() = value != Long.MAX_VALUE
  }
}