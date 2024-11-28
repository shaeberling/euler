package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2019/day/8 */
class Day8 : Solver {
  override fun solve(lines: List<String>): Result {
    val width = 25
    val height = 6
    val layers = lines[0].chunked(width * height)
    val target = layers.map { count(it) }.reduce { acc, map -> if (map['0']!! < acc['0']!!) map else acc }
    val resultA = target['1']!! * target['2']!!
    val resultB = renderImg(layers, width, height)
    return Result("$resultA", resultB)
  }

  private fun count(layer: String): Map<Char, Int> {
    val result = mutableMapOf<Char, Int>()
    layer.forEach { result[it] = (result[it] ?: 0) + 1 }
    return result
  }

  private fun renderImg(layers: List<String>, width: Int, height: Int): String {
    val ascii = Array(width * height) { '2' }
    layers.forEach{ it.withIndex().forEach { (i, c) -> if (ascii[i] == '2') ascii[i] = c }}
    return ascii.withIndex().map { (i, c) ->
      (if (i % width == 0) "\n" else "") + when (c) {
        '0' -> '.'
        else -> '#'
      }
    }.reduce { acc, s -> acc + s }
  }
}