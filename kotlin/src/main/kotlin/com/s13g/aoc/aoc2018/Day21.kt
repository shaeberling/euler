package com.s13g.aoc.aoc2018

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

class Day21 : Solver {
  override fun solve(lines: List<String>): Result {
    val ipIndex = lines[0].split(" ")[1].toInt()
    val program = parseInstructions(lines)

    // Remove comment to see the decompiled code listing.
    // Decompiler(ipIndex).decompileProgram(program)

    // Explanation Part A:
    // When decompiling the program we can see that register '0' (which is the
    // only one we are allowed to modify) is only used in one location, which is
    // instruction 28. Reading the code at this point shows that it is compared
    // to reg[5] and if they are the same, reg[3] is set to '1' which will make
    // the instruction pointer jump over instruction 30 (which would reset the
    // program to goto 5.
    // Hence, if we execute the program until it hits 28 and read out the
    // register 5's value, we know what to set 0 to.
    var vm = AocVm(ipIndex, program)
    vm.runUntilIp(28)
    val solutionA = vm.getReg(5)


    // Note: This will run for a minute or so.
    // Explanation Part B:
    // We are basically looking for the first repeat value for our IP register 5
    // that does not repeat.
    var solutionB = 0
    val seen = hashSetOf<Int>()
    vm = AocVm(ipIndex, program)
    while (true) {
      vm.runUntilIp(28)
      val value = vm.getReg(5)
      if (seen.contains(value)) {
        break
      }
      seen.add(value)
      solutionB = value
    }
    return Result("$solutionA", "$solutionB")
  }
}
