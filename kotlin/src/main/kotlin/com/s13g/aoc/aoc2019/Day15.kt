package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 15: Oxygen System ---
 * https://adventofcode.com/2019/day/15
 */
class Day15 : Solver {
  override fun solve(lines: List<String>): Result {
    val programA =
        lines[0].split(",").map { it.toLong() }.plus(Array(10000) { 0L })
    val walker = DroidWalker(programA)
    walker.run()
    val flower = OxygenFlower(walker.map, walker.oxygenLocation)
    val resultB = flower.run()
    return Result("${walker.stepsToOxygen}", "$resultB")
  }

  /** BFS oxygen flow. */
  private class OxygenFlower(val map: MutableMap<XY, Int>, start: XY) {
    private val flow = mutableSetOf<XY>()
    private var step = -1

    init {
      flow.add(start)
    }

    fun run(): Int {
      while (flow.isNotEmpty()) {
        step++
        step()
      }
      return step
    }

    private fun step() {
      val flow2 = flow.toList()
      flow.clear()
      val add = fun(newLoc: XY) {
        if (map[newLoc] == 1) {
          flow.add(newLoc)
          map[newLoc] = 2
        }
      }

      for (f in flow2) {
        add(f.goIn(1))
        add(f.goIn(2))
        add(f.goIn(3))
        add(f.goIn(4))
      }
    }
  }

  /** Walks and spawns new droids (BFS). */
  private class DroidWalker(origProg: List<Long>) {
    val map = mutableMapOf<XY, Int>()
    var stepsToOxygen = -1
    var oxygenLocation = XY(0, 0)
    private val droids = mutableSetOf<Droid>()
    private var step = 0

    init {
      val origin = XY(0, 0)
      droids.add(Droid(origProg, origin.goIn(1), 1))
      droids.add(Droid(origProg, origin.goIn(2), 2))
      droids.add(Droid(origProg, origin.goIn(3), 3))
      droids.add(Droid(origProg, origin.goIn(4), 4))
    }

    fun run() {
      while (droids.isNotEmpty()) {
        step++
        step()
      }
    }

    private fun step() {
      val droids2 = droids.toList()
      droids.clear()
      val add = fun(newLoc: XY, dir: Int, prog: List<Long>) {
        if (newLoc !in map) {
          droids.add(Droid(prog.toMutableList(), newLoc, dir))
        }
      }
      for (droid in droids2) {
        val out = droid.run()
        map[droid.newLoc] = out
        if (stepsToOxygen == -1 && out == 2) {
          stepsToOxygen = step
          oxygenLocation = droid.newLoc
        }
        if (out != 0) {
          add(droid.newLoc.goIn(1), 1, droid.cloneMem())
          add(droid.newLoc.goIn(2), 2, droid.cloneMem())
          add(droid.newLoc.goIn(3), 3, droid.cloneMem())
          add(droid.newLoc.goIn(4), 4, droid.cloneMem())
        }
      }
    }
  }

  private class Droid(v: List<Long>,
                      val newLoc: XY,
                      val direction: Int) : VM19.VmIO {
    private var lastOutput = -1
    private val vm: VM19 = VM19(v.toMutableList(), mutableListOf())

    init {
      vm.vmIo = this
    }

    /** Run with given dir until output is produced, then pause VM. */
    fun run(): Int {
      while (lastOutput == -1) vm.step()
      return lastOutput
    }

    fun cloneMem() = vm.cloneMem()

    override fun onInput() = direction.toLong()

    override fun onOutput(out: Long) {
      // 0: Wall, 1: Empty tile, 2: TARGET!
      lastOutput = out.toInt()
    }
  }

  private data class XY(val x: Int, val y: Int) {
    // 1: north, 2: south, 3: west, 4: east
    fun goIn(dir: Int) = when (dir) {
      1 -> XY(x, y + 1)
      2 -> XY(x, y - 1)
      3 -> XY(x + 1, y)
      4 -> XY(x - 1, y)
      else -> throw RuntimeException("Invalid direction")
    }
  }
}