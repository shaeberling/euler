package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 8: Handheld Halting ---
 * https://adventofcode.com/2020/day/8
 */
class Day8 : Solver {
  override fun solve(lines: List<String>): Result {
    val input = lines.map { it.split(" ") }.map { Instr(it[0], it[1].toInt()) }
    return Result("${runA(VM(input))}", "${runB(input)}")
  }
}

private fun runA(vm: VM): Int {
  while (vm.step()) if (vm.hasLooped()) return vm.acc
  error("No loop detected")
}

private fun runB(program: List<Instr>): Int {
  for (x in program.indices) {
    // Clone the list.
    val variant = program.toMutableList()

    // Swap the instruction if it matches.
    if (variant[x].cmd == "jmp") variant[x] = Instr("nop", variant[x].value)
    else if (variant[x].cmd == "nop") variant[x] = Instr("jmp", variant[x].value)

    // Create a Vm an run it until it either ends or loops.
    val vm = VM(variant)
    while (vm.step() && !vm.hasLooped());

    // If the program has ended normally, we found the fix!
    if (vm.hasEnded()) return vm.acc
  }
  error("Could not find a solution for Part B")
}

private class VM(val program: List<Instr>) {
  var acc = 0
  var pc = 0

  private val history = mutableSetOf<Int>()

  /** Returns whether the program is still running. False if it has finished. */
  fun step(): Boolean {
    history.add(pc)
    when (program[pc].cmd) {
      "nop" -> pc++
      "acc" -> {
        acc += program[pc].value; pc++
      }
      "jmp" -> pc += program[pc].value
    }
    return !hasEnded()
  }

  /** Whether the instruction to be executed has already been executed before. */
  fun hasLooped() = history.contains(pc)

  /** Whether this program has terminated normally .*/
  fun hasEnded() = pc >= program.size
}

private data class Instr(val cmd: String, val value: Int)
