package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.XY
import com.s13g.aoc.add
import com.s13g.aoc.resultFrom
import java.util.*

/**
 * --- Day 23: Monkey Math ---
 * https://adventofcode.com/2022/day/23
 */
class Day23 : Solver {
  private val topRow = setOf(XY(-1, -1), XY(0, -1), XY(1, -1))
  private val botRow = setOf(XY(-1, 1), XY(0, 1), XY(1, 1))
  private val leftCol = setOf(XY(-1, -1), XY(-1, 0), XY(-1, 1))
  private val rightCol = setOf(XY(1, -1), XY(1, 0), XY(1, 1))
  private val sides = setOf(XY(-1, 0), XY(1, 0))

  private val sections = listOf(topRow, botRow, leftCol, rightCol)
  private val moveDelta = listOf(XY(0, -1), XY(0, 1), XY(-1, 0), XY(1, 0))

  override fun solve(lines: List<String>): Result {
    var elves = mutableSetOf<XY>()
    for ((y, line) in lines.withIndex()) {
      for ((x, ch) in line.withIndex()) {
        if (ch == '#') elves.add(XY(x, y))
      }
    }

    var partA = 0
    var round = 0
    while (true) {
      val proposals = mutableMapOf<XY, XY>()
      val newLocations = mutableSetOf<XY>()
      // Proposal phase
      for (elf in elves) {
        val numTopRow = elves.countDelta(elf, topRow)
        val numBotRow = elves.countDelta(elf, botRow)
        val numSides = elves.countDelta(elf, sides)
        val totalNeighbors = numTopRow + numSides + numBotRow

        if (totalNeighbors > 0) {
          for ((i, section) in sections.withIndex()) {
            if (elves.countDelta(elf, section) == 0) {
              proposals[elf] = elf.add(moveDelta[i])
              break
            }
          }
        }
        // Don't move since we can't.
        if (elf !in proposals) newLocations.add(elf)
      }
      Collections.rotate(sections, -1)
      Collections.rotate(moveDelta, -1)

      // Move phase
      val destinations = proposals.map { it.value }.toList()
      val destinationCounts =
        destinations.associateWith { destinations.count { it2 -> it == it2 } }
          .toMap()
      var elvesMoved = false
      for (elf in proposals) {
        // If we're the only one to propose moving to this location, do it!
        if (destinationCounts[elf.value]!! == 1) {
          elvesMoved = true
          newLocations.add(elf.value)
        } else {
          newLocations.add(elf.key)  // Don't move
        }
      }
      if (!elvesMoved) break

      elves = newLocations
      round++
      if (round == 10) partA = elves.countEmpty()
    }
    return resultFrom(partA, round + 1)
  }

  private fun Set<XY>.countDelta(pos: XY, d: Set<XY>) =
    d.map { it.add(pos) }.count { it in this }

  private fun Set<XY>.countEmpty(): Int {
    val minX = this.minOf { it.x }
    val maxX = this.maxOf { it.x }
    val minY = this.minOf { it.y }
    val maxY = this.maxOf { it.y }

    var numEmpty = 0
    for (y in minY..maxY) {
      for (x in minX..maxX) {
        if (XY(x, y) !in this) numEmpty++
      }
    }
    return numEmpty
  }

  private fun Set<XY>.print() {
    val minX = this.minOf { it.x } - 2
    val maxX = this.maxOf { it.x } + 2
    val minY = this.minOf { it.y } - 2
    val maxY = this.maxOf { it.y } + 2

    for (y in minY..maxY) {
      for (x in minX..maxX) {
        if (XY(x, y) in this) print('#')
        else print('.')
      }
      print('\n')
    }
    print('\n')
  }
}

