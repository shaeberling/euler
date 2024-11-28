package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.lang.RuntimeException

/** https://adventofcode.com/2019/day/13 */
class Day13 : Solver {
  override fun solve(lines: List<String>): Result {
    val programA = lines[0].split(",").map { it.toLong() }.toMutableList()
    programA.addAll(Array(1000) { 0L })
    val gameA = Game(VM19(programA, mutableListOf()))
    gameA.run()
    val countA = gameA.map.values.count { it == 2L }

    val programB = lines[0].split(",").map { it.toLong() }.toMutableList()
    programB.addAll(Array(1000) { 0L })
    programB[0] = 2L  // Play for free
    val gameB = Game(VM19(programB, mutableListOf()))
    gameB.run()

    return Result("$countA", "${gameB.score}")
  }

  private class Game(val vm: VM19) : VM19.VmIO {
    val map = hashMapOf<XY, Long>()
    var score = 0L
    val ballPos = XY(0, 0)
    val paddlePos = XY(0, 0)
    var outCounter = 0
    val lastOut = Out(0L, 0L, 0L)

    init {
      vm.vmIo = this
    }

    fun run() {
      while (true) {
        if (!vm.step()) {
          return
        }
      }
    }

    override fun onInput(): Long {
      // Move the paddle where the ball is.
      if (ballPos.x > paddlePos.x) {
        return 1
      } else if (ballPos.x < paddlePos.x) {
        return -1
      } else {
        return 0
      }
    }

    override fun onOutput(v: Long) {
      if (outCounter == 0) {
        lastOut.a = v
      } else if (outCounter == 1) {
        lastOut.b = v
      } else {
        lastOut.c = v
        onOutput(lastOut)
      }
      outCounter = (outCounter + 1) % 3
    }

    private fun onOutput(out: Out) {
      if (out.a == -1L && out.b == 0L) {
        score = out.c
      } else {
        map[XY(out.a, out.b)] = out.c
        if (out.c == 4L) {
          ballPos.x = out.a
          ballPos.y = out.b
        } else if (out.c == 3L) {
          paddlePos.x = out.a
          paddlePos.y = out.b
        }
      }
    }

    private fun printMap() {
      val minX = map.keys.map { it.x }.min()!!
      val maxX = map.keys.map { it.x }.max()!!
      val minY = map.keys.map { it.y }.min()!!
      val maxY = map.keys.map { it.y }.max()!!

      for (y in minY..maxY) {
        for (x in minX..maxX) {
          print(getChar(map[XY(x, y)]))
        }
        println()
      }
    }

    private fun getChar(c: Long?) = when (c) {
      0L -> " "
      1L -> "#"
      2L -> "*"
      3L -> "-"
      4L -> "o"
      null -> " "
      else -> throw RuntimeException("Illegal tile type")
    }
  }

  private data class Out(var a: Long, var b: Long, var c: Long)

  private data class XY(var x: Long, var y: Long)
}