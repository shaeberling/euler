package com.s13g.aoc.aoc2023

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.lcm
import com.s13g.aoc.resultFrom


private typealias Network = Map<String, Node>

/**
 * --- Day 8: Haunted Wasteland ---
 * https://adventofcode.com/2023/day/8
 */
class Day8 : Solver {
  val re = """(\w+) = \((\w+), (\w+)\)$""".toRegex()
  override fun solve(lines: List<String>): Result {
    val instructions = lines[0]
    val network = parseNetwork(lines.drop(2))
    val partA = runNetwork("AAA", "ZZZ", instructions, network).first
    val partB = runNetworkB(instructions, network)
    return resultFrom(partA, partB)
  }

  private fun runNetworkB(instr: String, network: Network): Long {
    val nodes = network.keys.filter { it[2] == 'A' }.toSet()
    val loopLengthsInitial = mutableListOf<Pair<Int, String>>()

    // Detect loop length for each starting node.
    // Note: The loops hereafter are the same lengths. Looking for the loop
    //       length of each **Z node to another **Z node, results in the same
    //       length as from the initial **A node to this **Z node, thus making
    //       this fairly easy.
    nodes.forEach {
      loopLengthsInitial.add(runNetwork(it, "**Z", instr, network))
    }
    return lcm(loopLengthsInitial.map { it.first.toLong() })
  }

  private fun runNetwork(
    start: String,
    end: String,
    instr: String,
    network: Network
  ): Pair<Int, String> {
    var count = 0
    var idx = 0
    var node = start
    while (true) {
      count++
      node =
        if (instr[idx] == 'L') network[node]!!.left else network[node]!!.right
      if (node == end) return Pair(count, node)  // Part A
      if (end == "**Z" && node[2] == 'Z') return Pair(count, node)  // Part B
      if (++idx > instr.lastIndex) idx = 0
    }
  }

  private fun parseNetwork(lines: List<String>): Network =
    lines.map { re.find(it)!!.destructured }
      .associate { (node, left, right) -> node to Node(left, right) }
}

private data class Node(val left: String, val right: String)