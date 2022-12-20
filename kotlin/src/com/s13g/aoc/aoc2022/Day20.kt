package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.abs

/**
 * --- Day 20: Grove Positioning System ---
 * https://adventofcode.com/2022/day/20
 */
class Day20 : Solver {
  override fun solve(lines: List<String>): Result {
    val inputA = lines.map { Node(it.toLong()) }
    val inputB = lines.map { Node(it.toLong() * 811589153) }

    // Build the doubly linked list(s).
    val connect = { input: List<Node> ->
      for ((n1, n2) in input.windowed(2, 1)) {
        n1.next = n2
        n2.prev = n1
      }
      input.first().prev = input.last()
      input.last().next = input.first()
    }
    connect(inputA)
    connect(inputB)
    return resultFrom(mix(inputA, 1), mix(inputB, 10))
  }

  private fun mix(input: List<Node>, rounds: Int): Long {
    for (r in 1..rounds) {
      for (toPlace in input) {
        val num = abs(toPlace.value) % (input.size - 1)
        if (num == 0L) continue

        // Remove node from list first (important!).
        val oldPrev = toPlace.prev!!
        val oldNext = toPlace.next!!
        oldPrev.next = oldNext
        oldNext.prev = oldPrev

        // Find place to insert.
        var newPrev = toPlace
        if (toPlace.value > 0) for (n in 1..num) newPrev = newPrev.next!!
        else for (n in 1..num + 1) newPrev = newPrev.prev!!
        val newNext = newPrev.next!!

        // Insert into new place.
        newPrev.next = toPlace
        newNext.prev = toPlace
        toPlace.prev = newPrev
        toPlace.next = newNext
      }
    }
    val thousands = mutableListOf<Long>()
    var curr = input.first { it.value == 0L }
    for (i in 1..3000) {
      curr = curr.next!!
      if (i % 1000 == 0) thousands.add(curr.value)
    }
    return thousands.sum()
  }

  data class Node(
    val value: Long, var prev: Node? = null, var next: Node? = null
  ) {
    override fun toString() = "${prev!!.value}<[$value]>${next!!.value}"
  }
}