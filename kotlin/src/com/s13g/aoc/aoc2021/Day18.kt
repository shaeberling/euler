package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.max

/**
 * --- Day 18: Snailfish ---
 * https://adventofcode.com/2021/day/18
 */
class Day18 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines.map { parseNum(it) }

    var result = input[0]
    for (i in 1 until input.size) {
      result = add(result, input[i])
    }

    var largestSum = Long.MIN_VALUE
    for (a in 0 until lines.size - 1) {
      for (b in a + 1 until lines.size) {
        largestSum = max(largestSum, add(parseNum(lines[a]), parseNum(lines[b])).magnitude())
        largestSum = max(largestSum, add(parseNum(lines[b]), parseNum(lines[a])).magnitude())
      }
    }

    val partA = result.magnitude()
    val partB = largestSum
    return Result("$partA", "$partB")
  }

  private fun add(x: SfNum, y: SfNum): SfNum {
    val result = SfPair(x, y)
    x.parent = result
    y.parent = result
    while (result.reduce()) {
      // Nothing to do here...
    }
    return result
  }

  private fun parseNum(line: String): SfNum {
    // Single number, must be a literal.
    if (line[0] != '[') return SfRegular(line[0].toString().toLong())

    // Find the comma-separated left and right substring:
    val strA = extractComponent(line.substring(1))
    val a = parseNum(strA)
    assert(line[strA.length + 1] == ',')
    val strB = extractComponent(line.substring(strA.length + 2))
    val b = parseNum(strB)
    val result = SfPair(a, b)
    a.parent = result
    b.parent = result
    return result
  }

  private fun extractComponent(str: String): String {
    var idx = 0
    var openGroupsCount = 0
    var subStr = ""
    do {
      subStr += str[idx]
      if (str[idx] == '[') openGroupsCount++
      if (str[idx] == ']') openGroupsCount--
      idx++
    } while (openGroupsCount > 0)
    return subStr
  }

  private interface SfNum {
    var parent: SfPair?
    fun reduce(): Boolean
    fun explode(): Boolean
    fun split(): Boolean
    fun nestLevel(): Int {
      var level = 0
      var p = parent
      while (p != null) {
        p = p.parent
        level++
      }
      return level
    }

    fun rightMostLeaf(): SfNum
    fun leftMostLeaf(): SfNum
    fun firstLeftUp(): SfNum?
    fun firstRightUp(): SfNum?
    fun magnitude(): Long
  }

  private class SfRegular(var num: Long) : SfNum {
    override var parent: SfPair? = null
    override fun reduce() = false
    override fun explode() = false
    override fun split() = false

    override fun rightMostLeaf() = this
    override fun leftMostLeaf() = this
    override fun firstLeftUp(): SfNum? {
      error("Should never be called")
    }

    override fun firstRightUp(): SfNum? {
      error("Should never be called")
    }

    override fun magnitude() = num
    override fun toString() = "$num"
  }

  private class SfPair(var a: SfNum, var b: SfNum) : SfNum {
    override var parent: SfPair? = null
    override fun reduce(): Boolean {
      if (explode()) return true
      return split()
    }

    override fun explode(): Boolean {
      if (nestLevel() == 4) {  // Explodes
        assert(a is SfRegular)
        assert(b is SfRegular)

        if (parent!!.a == this) { // We're the left node
          val findLeft = parent!!.firstLeftUp()
          if (findLeft != null) {
            val rightMost = findLeft.rightMostLeaf()
            (rightMost as SfRegular).num += (a as SfRegular).num
          }
          val leftMost = parent!!.b.leftMostLeaf()
          (leftMost as SfRegular).num += (b as SfRegular).num
          parent!!.a = SfRegular(0)
        } else if (parent!!.b == this) { // We're the right node
          val findRight = parent!!.firstRightUp()
          if (findRight != null) {
            val leftMost = findRight.leftMostLeaf()
            (leftMost as SfRegular).num += (b as SfRegular).num
          }
          val rightMost = parent!!.a.rightMostLeaf()
          (rightMost as SfRegular).num += (a as SfRegular).num
          parent!!.b = SfRegular(0)
        }
        return true
      }
      if (a.explode()) return true
      if (b.explode()) return true
      return false
    }

    override fun split(): Boolean {
      if (a is SfRegular && (a as SfRegular).num > 9) {
        val aa = SfRegular((a as SfRegular).num / 2)  // rounds down
        val bb = SfRegular((a as SfRegular).num - aa.num)
        a = SfPair(aa, bb)
        aa.parent = a as SfPair
        bb.parent = a as SfPair
        a.parent = this
        return true
      }
      if (a.split()) return true
      if (b is SfRegular && (b as SfRegular).num > 9) {
        val aa = SfRegular((b as SfRegular).num / 2)  // rounds down
        val bb = SfRegular((b as SfRegular).num - aa.num)
        b = SfPair(aa, bb)
        aa.parent = b as SfPair
        bb.parent = b as SfPair
        b.parent = this
        return true
      }
      if (b.split()) return true
      return false
    }


    override fun firstLeftUp(): SfNum? {
      if (parent == null) return null
      if (parent!!.a != this) return parent!!.a
      return parent!!.firstLeftUp()
    }

    override fun firstRightUp(): SfNum? {
      if (parent == null) return null
      if (parent!!.b != this) return parent!!.b
      return parent!!.firstRightUp()
    }

    override fun magnitude() = a.magnitude() * 3 + b.magnitude() * 2
    override fun rightMostLeaf() = b.rightMostLeaf()
    override fun leftMostLeaf() = a.leftMostLeaf()
    override fun toString() = "[$a,$b]"
  }
}