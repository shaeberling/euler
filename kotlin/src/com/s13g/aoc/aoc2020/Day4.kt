package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 4: Passport Processing ---
 * https://adventofcode.com/2020/day/4
 */
class Day4 : Solver {
  override fun solve(lines: List<String>): Result {
    val passports = mutableListOf<Map<String, String>>()
    var current = mutableMapOf<String, String>()

    for (line in lines) {
      line.split(' ').filter { it.isNotBlank() }.forEach { it.split(":").let { s -> current.put(s[0], s[1]) } }
      if (line.isBlank()) {
        passports.add(current)
        current = mutableMapOf()
      }
    }
    // Add the last one if there was no new-line at the end of the file.
    // If passport is empty, it's going to be invalid anyway.
    passports.add(current)

    val resultA = passports.map { it.hasAllRequiredFields() }.filter { it }.count()
    val resultB = passports.map { it.hasAllRequiredFields() && it.isValid() }.filter { it }.count()
    return Result("$resultA", "$resultB")
  }

  // Part 1 validation check.
  private fun Map<String, String>.hasAllRequiredFields() =
      this.keys.containsAll(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))

  // Part 2 validation check.
  private fun Map<String, String>.isValid() = this.map { !isComponentValid(it) }.count { it } == 0

  private val hclRegex = """^#[a-f0-9]{6}""".toRegex()
  private val eclRegex = """amb|blu|brn|gry|grn|hzl|oth""".toRegex()
  private val hgtRegex = """^(\d+)(cm|in)$""".toRegex()
  private val pidRegex = """[\d]{9}""".toRegex()
  private fun isComponentValid(comp: Map.Entry<String, String>): Boolean {
    return when (comp.key) {
      "byr" -> comp.value.toInt() in 1920..2002
      "iyr" -> comp.value.toInt() in 2010..2020
      "eyr" -> comp.value.toInt() in 2020..2030
      "hgt" -> {
        val r = hgtRegex.find(comp.value)
        if (r == null) false
        else {
          val value = r.groupValues[1].toInt()
          when (r.groupValues[2]) {
            "cm" -> value in 150..193
            "in" -> value in 59..76
            else -> false
          }
        }
      }
      "hcl" -> comp.value.matches(hclRegex)
      "ecl" -> comp.value.matches(eclRegex)
      "pid" -> comp.value.matches(pidRegex)
      else -> true
    }
  }
}