package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom
import kotlin.math.min
import kotlin.math.sign

/**
 * --- Day 13: Distress Signal ---
 * https://adventofcode.com/2022/day/13
 */
class Day13 : Solver {
  override fun solve(lines: List<String>): Result {
    val pairs = lines.windowed(2, 3).flatten().map { parse(it) }

    var part1 = 0
    for ((i, it) in pairs.windowed(2, 2).withIndex()) {
      if (ThingComparator.compare(it[0], it[1]) < 0) part1 += (i + 1)
    }

    val pairsB = mutableListOf<Thing>()
    pairs.windowed(2, 2) { pairsB.add(it[0]); pairsB.add(it[1]) }
    pairsB.addAll(listOf(parse("[[2]]"), parse("[[6]]")))
    pairsB.sortWith(ThingComparator)

    var a = 0
    var b = 0
    for ((i, thing) in pairsB.withIndex()) {
      if (thing.toString() == "[[2]]") a = i + 1
      if (thing.toString() == "[[6]]") b = i + 1
    }
    return resultFrom(part1, a * b)
  }


  fun parse(str: String): Thing {
    var level = 0
    val result = mutableListOf<Thing>()
    var curr = ""
    for ((i, ch) in str.substring(1).withIndex()) {
      if (ch == ',' && level == 0) {
        if (curr.isNotEmpty()) result.add(Thing(curr.toInt(), listOf()))
        curr = ""
      } else if (ch == '[') {
        if (level == 0) result.add(parse(str.substring(1).substring(i)))
        level++
      } else if (ch == ']') {
        level--
        if (level < 0) {
          if (curr.isNotEmpty()) result.add(Thing(curr.toInt(), listOf()))
          return Thing(-1, result)
        }
      } else if (level == 0) {
        curr += ch
      }
    }
    throw RuntimeException("Oh no, my hovercraft is full of eels")
  }

  data class Thing(val value: Int, val other: List<Thing> = emptyList()) {
    fun asList() = other.ifEmpty {
      if (value >= 0) listOf(Thing(value, emptyList()))
      else emptyList()
    }

    override fun toString(): String =
      if (value >= 0) "$value"
      else "[" + other.joinToString(",") { it.toString() } + "]"
  }
}

class ThingComparator {
  companion object : Comparator<Day13.Thing> {
    override fun compare(a: Day13.Thing, b: Day13.Thing): Int {
      val list1 = a.asList()
      val list2 = b.asList()

      for (i in 0 until (min(list1.size, list2.size))) {
        if (list1[i].value == -1 || list2[i].value == -1) {
          val o = compare(list1[i], list2[i])
          if (o != 0) return o
        } else {
          if (list1[i].value < list2[i].value) return -1
          if (list1[i].value > list2[i].value) return 1
        }
      }
      return (list1.size - list2.size).sign
    }
  }
}