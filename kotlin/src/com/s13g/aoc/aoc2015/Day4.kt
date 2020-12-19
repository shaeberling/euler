package com.s13g.aoc.aoc2015

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import java.security.MessageDigest

/**
 * --- Day 4: The Ideal Stocking Stuffer ---
 * https://adventofcode.com/2015/day/4
 */
class Day4 : Solver {
  private val digest = MessageDigest.getInstance("MD5")
  private val leadingZerosRegex = """^(0*)""".toRegex()
  override fun solve(lines: List<String>): Result {
    val secret = lines[0]

    return Result("${mineCoin(secret, 5)}", "${mineCoin(secret, 6)}")
  }

  private fun mineCoin(input: String, prefixLength: Int): Int {
    for (n in 0..Int.MAX_VALUE) {
      if (leadingZerosRegex.find(md5HexString("$input$n"))!!.destructured.component1().length == prefixLength) {
        return n
      }
    }
    error("OMG")
  }

  private fun md5HexString(input: String) = digest.digest(input.toByteArray()).toHex()

  private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}