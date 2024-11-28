package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 12: Passage Pathing ---
 * https://adventofcode.com/2021/day/12
 */
class Day12 : Solver {
  override fun solve(lines: List<String>): Result {
    val connections = lines.map { it.split('-') }.map { Pair(it[0], it[1]) }
    val system = mutableMapOf<String, Node>()
    for (conn in connections) {
      if (!system.containsKey(conn.first)) system[conn.first] = Node(conn.first)
      if (!system.containsKey(conn.second)) system[conn.second] = Node(conn.second)
      system[conn.first]!!.connections.add(system[conn.second]!!)
      system[conn.second]!!.connections.add(system[conn.first]!!)
    }

    val partA = countRoutes(system["start"]!!, setOf(), false)
    val partB = countRoutes(system["start"]!!, setOf(), true)
    return Result("$partA", "$partB")
  }

  private fun countRoutes(from: Node, noGos: Set<String>, smallException: Boolean): Int {
    if (noGos.contains(from.id) && (!smallException || from.id == "start")) return 0
    if (from.id == "end") return 1

    val newSmallException = if (noGos.contains(from.id)) false else smallException
    val newNoGos = noGos.toMutableSet()
    if (from.small) {
      newNoGos.add(from.id)
    }
    return from.connections.map { countRoutes(it, newNoGos, newSmallException) }.sum()
  }

  private data class Node(val id: String) {
    val small: Boolean = id[0].isLowerCase()
    val connections: MutableSet<Node> = mutableSetOf<Node>()
  }
}