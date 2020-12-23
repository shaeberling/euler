package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.util.*

/**
 * --- Day 23: Crab Cups ---
 * https://adventofcode.com/2020/day/23
 */
class Day23 : Solver {
  override fun solve(lines: List<String>): Result {
    val cupsInput = LinkedList(lines[0].map { it.toString().toInt() })

    val cupsA = playGame(cupsInput, 100, 0)
    val cupsB = playGame(cupsInput, 10_000_000, 1_000_000)

    // Part 1: List all the nodes after 1 in the circle.
    var currentNode = cupsA[1]!!.next()
    var resultA = ""
    while (currentNode.value != 1) {
      resultA += currentNode.value
      currentNode = currentNode.next()
    }

    // Part 2: Multiply the two values after the 1-Node.
    val resultB = cupsB[1]!!.next().value.toLong() * cupsB[1]!!.next().next().value.toLong()
    return Result(resultA, "$resultB")
  }

  private fun playGame(cupsInput: List<Int>, numRounds: Int, addAdditional: Int): Map<Int, Node> {
    val nodeMap = createLinkedList(cupsInput, addAdditional)
    val head = nodeMap[cupsInput[0]]!!
    val cupsMin = cupsInput.min()!!
    val cupsMax = nodeMap.keys.max()!!

    var current = head
    for (round in 1..numRounds) {
      // The three cut-outs.
      val a = current.next()
      val b = current.next().next()
      val c = current.next().next().next()

      // Cutting them out of the linked list.
      current.next = c.next()

      // Determine the destination node based on the given rules.
      var destinationCup: Node
      var dValue = current.value - 1
      while (true) {
        if (dValue in nodeMap && dValue != a.value && dValue != b.value && dValue != c.value) {
          destinationCup = nodeMap[dValue]!!
          break
        }
        dValue--
        if (dValue < cupsMin) dValue = cupsMax
      }

      // Insert the three cut out cups into the circle at the determined place.
      val follower = destinationCup.next
      destinationCup.next = a
      c.next = follower

      // Move on to the next node in the circle for the next round.
      current = current.next()
    }
    return nodeMap
  }

  /** Creates a singly-linked list based on the input data. */
  private fun createLinkedList(cupsInput: List<Int>, listSize: Int): Map<Int, Node> {
    // Maps value to Node for quick access, as our linked list slow to traverse.
    val nodeMap = mutableMapOf<Int, Node>()

    val head = Node(cupsInput[0])
    nodeMap[cupsInput[0]] = head
    var tail = head

    for (i in 1..cupsInput.lastIndex) {
      val newNode = Node(cupsInput[i], head)
      tail.next = newNode
      nodeMap[cupsInput[i]] = newNode
      tail = newNode
    }

    // Part 2 asks us to make the list 1 million items long.
    for (v in nodeMap.keys.max()!! + 1..listSize) {
      val newNode = Node(v, head)
      tail.next = newNode
      nodeMap[v] = newNode
      tail = newNode
    }
    return nodeMap
  }

  private class Node(var value: Int, var next: Node? = null) {
    fun next() = next!!
  }
}