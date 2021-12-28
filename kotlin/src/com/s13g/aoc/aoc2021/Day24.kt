package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

class Day24 : Solver {

  override fun solve(lines: List<String>): Result {
    val partA = OptimizedALU(9 downTo 1).solve(-1, 0, 0, 0, 0).second
    val partB = OptimizedALU(1..9).solve(-1, 0, 0, 0, 0).second
    return Result(partA, partB)
  }

  private class OptimizedALU(val numRange: IntProgression) {
    val NO_RESULT = Pair(false, "")

    // This is extracted from the input...
    val params = listOf(
      Parameters(14L, 1L, 7L),
      Parameters(12L, 1L, 4L),
      Parameters(11L, 1L, 8L),
      Parameters(-4L, 26L, 1L),
      Parameters(10L, 1L, 5L),
      Parameters(10L, 1L, 14L),
      Parameters(15L, 1L, 12L),
      Parameters(-9L, 26L, 10L),
      Parameters(-9L, 26L, 5L),
      Parameters(12L, 1L, 7L),
      Parameters(-15L, 26L, 6L),
      Parameters(-7L, 26L, 8L),
      Parameters(-10L, 26L, 4L),
      Parameters(0L, 26L, 6L)
    )

    // Memoization of the solve function.
    val cache = mutableMapOf<AluState, Pair<Boolean, String>>()
    fun solveCached(
      block: Int,
      input: Long,
      xx: Long,
      yy: Long,
      zz: Long
    ): Pair<Boolean, String> {
      // A bit of a hack, but this shortens the number of tries significantly.
      if (zz > 5000000) return NO_RESULT
      val state = AluState(block, input, xx, yy, zz)
      if (cache.containsKey(state)) return cache[state]!!
      val result = solve(block, input, xx, yy, zz)
      cache[state] = result
      return result
    }

    fun solve(
      block: Int,
      inp: Long,
      xx: Long,
      yy: Long,
      zz: Long
    ): Pair<Boolean, String> {
      var x = xx
      var y = yy
      var z = zz

      if (block >= 0) {
        // Reverse engineered from the input.
        x = if ((z % 26 + params[block].a) == inp) 0 else 1
        z = (z / params[block].b) * ((25 * x) + 1) + (inp + params[block].c) * x
      }

      // This was the last block.
      if (block == 13) {
        return Pair(z == 0L, "")
      }

      // Continue with the next digits. Depth first search...
      for (n in numRange) {
        val res = solveCached(block + 1, n.toLong(), x, y, z)
        if (res.first) {
          return Pair(true, "$n${res.second}")
        }
      }
      return NO_RESULT
    }
  }

  /** Used to cache the result. */
  private data class AluState(
    val block: Int,
    val input: Long,
    val x: Long,
    val y: Long,
    val z: Long
  )

  private data class Parameters(val a: Long, val b: Long, val c: Long)
}