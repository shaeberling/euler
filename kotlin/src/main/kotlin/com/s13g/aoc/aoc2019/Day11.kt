package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.lang.RuntimeException

/** https://adventofcode.com/2019/day/11 */
class Day11 : Solver {
  override fun solve(lines: List<String>): Result {
    val program = lines[0].split(",").map { it.toLong() }.toMutableList()
    program.addAll(Array(1000) { 0L })

    val robotA = Robot(VM19(program, mutableListOf()))
    robotA.run()
    val robotB = Robot(VM19(program, mutableListOf()))
    robotB.map[XY(0, 0)] = 1
    robotB.run()
    return Result("${robotA.numPanelsPainted()}", robotB.printPanel())
  }

  private class Robot(val vm: VM19) : VM19.VmIO {
    var dir = 0
    var pos = XY(0, 0)
    val map = hashMapOf<XY, Long>()
    var outIsColor = true

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

    fun numPanelsPainted() = map.size

    override fun onInput() = map[pos] ?: 0L

    override fun onOutput(out: Long) {
      if (outIsColor) {
        map[pos] = out
      } else {
        changeDir(out)
      }
      outIsColor = !outIsColor
    }

    private fun changeDir(i: Long) {
      // 0 --> turn left, 1 --> turn right
      dir += if (i == 0L) -1 else 1
      if (dir < 0) dir += 4
      dir %= 4

      pos = pos.add(when (dir) {
        0 -> XY(0, -1)
        1 -> XY(1, 0)
        2 -> XY(0, 1)
        3 -> XY(-1, 0)
        else -> throw RuntimeException("Unknown direction")
      })
    }

    fun printPanel(): String {
      val minX = map.keys.map { it.x }.min()!!
      val maxX = map.keys.map { it.x }.max()!!
      val minY = map.keys.map { it.y }.min()!!
      val maxY = map.keys.map { it.y }.max()!!

      var result = "\n"
      for (y in minY..maxY) {
        for (x in minX..maxX) {
          result += if ((map[XY(x, y)] ?: 0L) == 1L) "#" else "."
        }
        result += "\n"
      }
      return result
    }
  }

  private data class XY(var x: Int, var y: Int) {
    fun add(b: XY) = XY(x + b.x, y + b.y)
  }
}