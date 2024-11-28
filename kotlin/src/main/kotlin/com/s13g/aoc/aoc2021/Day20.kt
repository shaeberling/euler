package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 20: Trench Map ---
 * https://adventofcode.com/2021/day/20
 */
class Day20 : Solver {
  override fun solve(lines: List<String>): Result {
    val lookup = lines[0].map { it == '#' }
    val imgData = lines.subList(2, lines.size)
    val partA = runCycles(imgData, lookup, 2)
    val partB = runCycles(imgData, lookup, 50)
    return Result("$partA", "$partB")
  }

  private fun runCycles(imgData: List<String>, lookup: List<Boolean>, num: Int): Int {
    var img = Image(imgData.map { it.map { ch -> ch == '#' } }, lookup).pad(num * 2)
    for (i in 1..num) img = img.enhance()
    return img.countCenterLit()
  }

  private class Image(val str: List<List<Boolean>>, val lookup: List<Boolean>) {
    private val width = str[0].size
    private val height = str.size

    fun enhance(): Image {
      val newStr = List(this.height) { MutableList(this.width) { false } }
      for (y in 0 until this.height) {
        for (x in 0 until this.width) {
          newStr[y][x] = lookup[this.getCode(x, y)]
        }
      }
      return Image(newStr, lookup)
    }

    fun countCenterLit(): Int {
      var result = 0
      val cX = width / 2
      val cY = height / 2
      for (size in 1 until width) {
        var newSize = 0
        for (y in cY - size until cY + size) {
          for (x in cX - size until cX + size) {
            if (isLit(x, y)) newSize++
          }
        }
        // Return if no more lit pixels are added.
        if (result == newSize) return result
        result = newSize
      }
      error("Unreachable")
    }

    private fun isLit(x: Int, y: Int) = if (x < 0 || y < 0 || x >= width || y >= height) false else str[y][x]

    private fun getCode(x: Int, y: Int): Int {
      var code = 0
      var n = 0
      for (yy in y - 1..y + 1) {
        for (xx in x - 1..x + 1) {
          code += (if (isLit(xx, yy)) 1 else 0) shl (9 - ++n)
        }
      }
      return code
    }

    fun pad(n: Int): Image {
      val newWidth = this.width + n * 2
      val newHeight = this.height + n * 2
      val newStr = List(newHeight) { MutableList(newWidth) { false } }

      for (y in 0 until this.height) {
        for (x in 0 until this.width) {
          newStr[y + n][x + n] = this.str[y][x]
        }
      }
      return Image(newStr, lookup)
    }
  }
}