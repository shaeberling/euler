package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 4: Passport Processing ---
 * https://adventofcode.com/2020/day/3
 */
class Day4 : Solver {
  override fun solve(lines: List<String>): Result {
    val passports = mutableListOf<List<String>>()
    var current = mutableListOf<String>()

    for (line in lines) {
      line.split(' ').forEach { current.add(it) }
      if (line.isBlank()) {
        passports.add(current)
        current = mutableListOf()
      }
    }
    // Add the last one if there was no new-line at the end of the file.
    // If passport is empty, it's going to be invalid anyway.
    passports.add(current)

    val resultA = passports.map { it.hasAllRequiredFields() }.filter { it }.count()
    val resultB = passports.map { it.isValid() }.filter { it }.count()
    return Result("$resultA", "$resultB")
  }
}

fun List<String>.isValid() = this.hasAllRequiredFields() &&
    this.map { it.isNotBlank() && !isComponentValid(it) }.count { it } == 0

val hcl_regex = """^#[a-f0-9]{6}""".toRegex()
val ecl_regex = """amb|blu|brn|gry|grn|hzl|oth""".toRegex()
val hgt_regex = """^(\d+)(cm|in)$""".toRegex()
val pid_regex = """[0-9]{9}""".toRegex()
fun isComponentValid(comp: String): Boolean {
  val (key, valueRaw) = comp.split(':')
  return when (key) {
    "byr" -> {
      valueRaw.toInt() in 1920..2002
    }
    "iyr" -> {
      valueRaw.toInt() in 2010..2020
    }
    "eyr" -> {
      valueRaw.toInt() in 2020..2030
    }
    "hgt" -> {
      val r = hgt_regex.find(valueRaw)
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
    "hcl" -> {
      valueRaw.matches(hcl_regex)
    }
    "ecl" -> {
      valueRaw.matches(ecl_regex)
    }
    "pid" -> {
      valueRaw.matches(pid_regex)
    }
    else -> true
  }
}

fun List<String>.hasAllRequiredFields() =
    this.hasField("byr:") &&
        this.hasField("iyr:") &&
        this.hasField("eyr:") &&
        this.hasField("hgt:") &&
        this.hasField("hcl:") &&
        this.hasField("ecl:") &&
        this.hasField("pid:")

fun List<String>.hasField(field: String) = this.map { it.startsWith(field) }.count { it } > 0
