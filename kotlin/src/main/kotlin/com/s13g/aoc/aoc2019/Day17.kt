package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.max

/**
 * --- Day 17: Set and Forget ---
 * https://adventofcode.com/2019/day/17
 */
class Day17 : Solver {
  override fun solve(lines: List<String>): Result {
    val progA = lines[0].split(",").map { it.toLong() }.plus(Array(9999) { 0L })
    val crA = CamRobot(VM19(progA.toMutableList(), mutableListOf()))
    crA.run()
    val intersections = crA.getIntersections()
    val resultA = intersections.sumBy { it.x * it.y }

    // Figured this out by tracing the path from the output map (see input file)
    val main = "A,B,A,C,A,B,C,A,B,C\n".map { it.toInt() }
    val prgA = "R,12,R,4,R,10,R,12\n".map { it.toInt() }
    val prgB = "R,6,L,8,R,10\n".map { it.toInt() }
    val prgC = "L,8,R,4,R,4,R,6\n".map { it.toInt() }

    val input = mutableListOf<Int>()
    input.addAll(main)
    input.addAll(prgA)
    input.addAll(prgB)
    input.addAll(prgC)
    input.addAll("n\n".map { it.toInt() })  // No map output.

    val progB = progA.toMutableList()
    progB[0] = 2  // Wake up the robot.
    val crB = CamRobot(VM19(progB.toMutableList(), mutableListOf()), input)
    crB.run()

    return Result("$resultA", "${crB.lastOutput}")
  }

  private class CamRobot(val vm: VM19,
                         private val input: List<Int> = emptyList()) : VM19.VmIO {
    val map = mutableMapOf<XY, Boolean>()
    var outMapStr = ""
    var maxX = 0
    var x = 0
    var y = 0
    var lastOutput = 0L
    var inputIdx = 0

    init {
      vm.vmIo = this
    }

    fun run() {
      vm.run()
    }

    fun getIntersections(): List<XY> {
      val result = mutableListOf<XY>()
      val m = fun(xM: Int, yM: Int) = map[XY(xM, yM)] ?: false
      for (yy in 0 until y) {
        for (xx in 0 until maxX) {
          // Check for intersection.
          if (m(xx, yy) && m(xx, yy - 1) && m(xx + 1, yy) && m(xx, yy + 1) && m(xx - 1, yy)) {
            result.add(XY(xx, yy))
          }
        }
      }
      return result
    }

    override fun onInput(): Long {
      return input[inputIdx++].toLong()
    }

    override fun onOutput(out: Long) {
      lastOutput = out
      outMapStr += out.toChar()
      if (out == 35L) { // '#'
        map[XY(x, y)] = true
      }
      if (out == 10L) { // new-line
        y++
        x = 0
      } else {
        x++
      }
      maxX = max(x, maxX)
    }
  }

  private data class XY(val x: Int, val y: Int)
}