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

  fun runUntilIp(ipHalt: Int) {
    do {
      if (!next()) break
    } while (ip != ipHalt)
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


/** A way to understand the program better. */
class Decompiler(val ipIndex: Int) {
  fun decompileProgram(program: List<Instruction>) {
    for ((n, instr) in program.withIndex()) {
      print("${n.toString().padStart(2, '0')} ")
      when (instr.op) {
        "addr" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} + ${reg(instr.params.b)}")
        }
        "addi" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} + ${instr.params.b}")
        }
        "mulr" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} * ${reg(instr.params.b)}")
        }
        "muli" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} * ${instr.params.b}")
        }
        "banr" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} & ${reg(instr.params.b)}")
        }
        "bani" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} & ${instr.params.b}")
        }
        "borr" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} | ${reg(instr.params.b)}")
        }
        "bori" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)} | ${instr.params.b}")
        }
        "setr" -> {
          println("${reg(instr.params.c)} = ${reg(instr.params.a)}")
        }
        "seti" -> {
          println("${reg(instr.params.c)} = ${instr.params.a}")
        }
        "gtir" -> {
          println("${reg(instr.params.c)} = boolToInt(${instr.params.a} > ${reg(instr.params.b)})")
        }
        "gtri" -> {
          println("${reg(instr.params.c)} = boolToInt(${reg(instr.params.a)} > ${instr.params.b})")
        }
        "gtrr" -> {
          println("${reg(instr.params.c)} = boolToInt(${reg(instr.params.a)} > ${reg(instr.params.b)})")
        }
        "eqir" -> {
          println("${reg(instr.params.c)} = boolToInt(${instr.params.a} == ${reg(instr.params.b)})")
        }
        "eqri" -> {
          println("${reg(instr.params.c)} = boolToInt(${reg(instr.params.a)} == ${instr.params.b})")
        }
        "eqrr" -> {
          println("${reg(instr.params.c)} = boolToInt(${reg(instr.params.a)} == ${reg(instr.params.b)})")
        }
        else -> {
          println("[MISSING: '${instr.op}']")
        }
      }
    }
  }

  private fun reg(reg: Int) = if (reg == ipIndex) "ip" else "reg[$reg]"
}
