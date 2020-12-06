package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.intersectAll

typealias Group = List<String>

/**
 * --- Day 6: Custom Customs ---
 * https://adventofcode.com/2020/day/6
 */
class Day6 : Solver {
  override fun solve(lines: List<String>): Result {
    val groups = parseGroups(lines)
    val resultA = groups.map { it.anyPassenger() }.sum()
    val resultB = groups.map { it.allPassengers() }.sum()
    return Result("$resultA", "$resultB")
  }

  private fun parseGroups(lines: List<String>): List<Group> {
    val result = mutableListOf<List<String>>()
    var person = mutableListOf<String>()
    for (line in lines) if (line.isBlank()) {
      result.add(person)
      person = mutableListOf()
    } else person.add(line)
    result.add(person)
    return result
  }

  // Part 1: Add all different letters up for each passenger.
  private fun Group.anyPassenger() = this.flatMap { it.toList() }.toSet().size

  // Part 2: Count all letters that all passengers have in common.
  private fun Group.allPassengers() = this.map { it.toCharArray().toSet() }.toList().intersectAll().size
}