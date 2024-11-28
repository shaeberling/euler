package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 19: Tractor Beam ---
 * https://adventofcode.com/2019/day/19
 */
class Day19 : Solver {
  override fun solve(lines: List<String>): Result {
    val prog =
        lines[0].split(",").map { it.toLong() }.plus(Array(100000) { 0L })
    val beamer = Beamer(prog)
    for (y in 0..49) {
      beamer.newLine(49)
    }
    val resultA = beamer.field.size
    val resultB = findRoom(beamer, 100)
    return Result("$resultA", "$resultB")
  }

  private fun findRoom(beamer: Beamer, size: Int): Int {
    while (true) {
      // This defines the bottom left corner of the ship's area.
      val y = beamer.newLine(Int.MAX_VALUE)
      val x = beamer.prevXStart

      val topY = y - (size - 1)
      val rightX = x + (size - 1)
      if (beamer.get(x, topY) && beamer.get(rightX, topY)) {
        return 10000 * x + topY
      }
    }
  }

  private class Beamer(val program: List<Long>) {
    val field = mutableMapOf<XY, Boolean>()
    var prevXStart = 0
    var y = -1

    fun newLine(xLimit: Int): Int {
      y++
      var xStarted = false
      var x = prevXStart
      while (x < xLimit) {
        val beam = Beam(VM19(program.toMutableList(), mutableListOf()), x, y)
        if (beam.run()) {
          if (!xStarted) {
            prevXStart = x
          }
          xStarted = true
          field[XY(x, y)] = true
        } else {
          // The beam is one contiguous line per row. Once it ends, it's done.
          if (xStarted) {
            break
          }
        }
        x++
      }
      return y
    }

    fun get(x: Int, y: Int) = field[XY(x, y)] ?: false
  }

  private class Beam(val vm: VM19, val x: Int, val y: Int) : VM19.VmIO {
    private var inputIsY = false
    private var lastOutput = -1

    init {
      vm.vmIo = this
    }

    fun run(): Boolean {
      while (true) {
        if (!vm.step()) {
          return lastOutput == 1
        }
      }
    }

    override fun onInput(): Long {
      val input = if (inputIsY) y else x
      inputIsY = !inputIsY
      return input.toLong()
    }

    override fun onOutput(out: Long) {
      lastOutput = out.toInt()
    }
  }

  private data class XY(val x: Int, val y: Int)
}