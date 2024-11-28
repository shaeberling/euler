package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 4: Giant Squid ---
 * https://adventofcode.com/2021/day/4
 */
class Day4 : Solver {
  override fun solve(lines: List<String>): Result {
    val (numbers, boards) = parseData(lines)
    return Result("${solve(boards, numbers, false)}", "${solve(boards, numbers, true)}")
  }

  fun solve(boards: MutableList<BingoBoard>, numbers: List<Long>, partB: Boolean): Long {
    for (num in numbers) {
      boards.forEach { it.mark(num) }
      val toRemove = boards.filter { it.isWon() }.filter { boards.contains(it) }
      boards.removeAll(toRemove)
      if (partB && boards.isEmpty()) return toRemove[0].unmarkedSum() * num
      if (!partB && toRemove.isNotEmpty()) return toRemove[0].unmarkedSum() * num
    }
    error("No board won")
  }

  private fun parseData(lines: List<String>): Pair<List<Long>, MutableList<BingoBoard>> {
    val numbers = lines[0].split(",").map { it.toLong() }
    val boards = mutableListOf<BingoBoard>()
    for (line in lines.listIterator(1)) {
      if (line.isEmpty()) {
        boards.add(BingoBoard(mutableListOf()))
      } else {
        boards.last().feedLine(line)
      }
    }
    return Pair(numbers, boards)
  }
}

class BingoBoard(val values: MutableList<Long>) {
  fun feedLine(line: String) {
    values.addAll(line.split(" ").filter { it.isNotBlank() }.map { it.toLong() })
  }

  fun mark(num: Long) {
    values.replaceAll { if (it == num) -1 else it }
  }

  fun get(x: Int, y: Int) = values[y * 5 + x]
  fun unmarkedSum() = values.filter { it != -1L }.sum()

  fun isWon(): Boolean {
    (0..4).forEach { y -> if ((0..4).map { x -> get(x, y) }.count { it == -1L } == 5) return true }
    (0..4).forEach { x -> if ((0..4).map { y -> get(x, y) }.count { it == -1L } == 5) return true }
    return false
  }
}