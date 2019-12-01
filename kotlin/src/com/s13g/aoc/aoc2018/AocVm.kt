package com.s13g.aoc.aoc2018


class AocVm(private val ipIdx: Int, private val program: List<Instruction>) {
  private var ip = 0
  private val reg = intArrayOf(0, 0, 0, 0, 0, 0)
  private val ops = createOperations()

  fun runUntilHalt() {
    while (true) {
      if (!next()) break
    }
  }

  /** Returns false, if the program execution halted */
  private fun next(): Boolean {
    if (program.size <= ip) return false
    val instr = program[ip]
//    val preDbgStr = "ip=$ip [${reg.joinToString(", ")}] $instr"
    reg[ipIdx] = ip
    ops[instr.op]!!(instr.params, reg)
    ip = reg[ipIdx]
    ip++
//    println("$preDbgStr [${reg.joinToString(", ")}]")
    return true
  }

  fun getReg(i: Int) = reg[i]
  fun setReg(i: Int, v: Int) {
    reg[i] = v
  }
}

fun parseInstructions(program: List<String>): List<Instruction> {
  val result = arrayListOf<Instruction>()
  for (line in program) {
    if (line.startsWith("#")) continue
    val split = line.split(" ")
    assert(split.size == 4) { println("Invalid instruction: '$line'") }
    result.add(Instruction(split[0],
        Params(split[1].toInt(), split[2].toInt(), split[3].toInt())))
  }
  return result
}

data class Instruction(val op: String, val params: Params) {
  override fun toString() = "$op $params"
}

data class Params(val a: Int, val b: Int, val c: Int) {
  override fun toString() = "$a $b $c"
}

typealias Operation = (Params, IntArray) -> Unit

private fun createOperations(): HashMap<String, Operation> {
  return hashMapOf(
      Pair("addr", ::addr),
      Pair("addi", ::addi),
      Pair("mulr", ::mulr),
      Pair("muli", ::muli),
      Pair("banr", ::banr),
      Pair("bani", ::bani),
      Pair("borr", ::borr),
      Pair("bori", ::bori),
      Pair("setr", ::setr),
      Pair("seti", ::seti),
      Pair("gtir", ::gtir),
      Pair("gtri", ::gtri),
      Pair("gtrr", ::gtrr),
      Pair("eqir", ::eqir),
      Pair("eqri", ::eqri),
      Pair("eqrr", ::eqrr))
}

private fun addr(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] + reg[param.b]
}

private fun addi(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] + param.b
}

private fun mulr(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] * reg[param.b]
}

private fun muli(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] * param.b
}

private fun banr(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] and reg[param.b]
}

private fun bani(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] and param.b
}

private fun borr(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] or reg[param.b]
}

private fun bori(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a] or param.b
}

private fun setr(param: Params, reg: IntArray) {
  reg[param.c] = reg[param.a]
}

private fun seti(param: Params, reg: IntArray) {
  reg[param.c] = param.a
}

private fun gtir(param: Params, reg: IntArray) {
  reg[param.c] = if (param.a > reg[param.b]) 1 else 0
}

private fun gtri(param: Params, reg: IntArray) {
  reg[param.c] = if (reg[param.a] > param.b) 1 else 0
}

private fun gtrr(param: Params, reg: IntArray) {
  reg[param.c] = if (reg[param.a] > reg[param.b]) 1 else 0
}

private fun eqir(param: Params, reg: IntArray) {
  reg[param.c] = if (param.a == reg[param.b]) 1 else 0
}

private fun eqri(param: Params, reg: IntArray) {
  reg[param.c] = if (reg[param.a] == param.b) 1 else 0
}

private fun eqrr(param: Params, reg: IntArray) {
  reg[param.c] = if (reg[param.a] == reg[param.b]) 1 else 0
}
