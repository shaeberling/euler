package com.s13g.aoc.aoc2019

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 23: Category Six ---
 * https://adventofcode.com/2019/day/23
 */
class Day23 : Solver {
  override fun solve(lines: List<String>): Result {
    val prog =
        lines[0].split(",").map { it.toLong() }.plus(Array(100000) { 0L })
    val network = Network(prog)
    while (true) {
      if (!network.step()) {
        break
      }
    }
    return Result("${network.address255?.y}", "${network.resultB}")
  }

  private class Network(prog: List<Long>) {
    val computers: List<Computer> =
        (0..49).map { Computer(it, VM19(prog.toMutableList()), this) }
    var address255: XY? = null
    var natPacket = XY(0, 0)
    var idleCounter = 0
    var lastNatY = -1L
    var resultB = -1L

    fun step(): Boolean {
      computers.forEach { it.step() }

      // For Part B, check if all queues are empty for a while.
      if (computers.sumBy { it.packets.size } == 0) {
        idleCounter++
      }
      if (idleCounter == 1000) {
        sendNatPacket()
        idleCounter = 0
      }
      return resultB == -1L
    }

    fun sendNatPacket() {
      if (natPacket.y == lastNatY && resultB == -1L) {
        resultB = natPacket.y
      }
      lastNatY = natPacket.y
      sendPacket(0, natPacket)
    }

    fun sendPacket(address: Int, value: XY) {
      // Network is not idle if packets are sent.
      idleCounter = 0
      if (address == 255) {
        if (address255 == null) {
          address255 = value
        }
        natPacket = value
      }
      if (address < 50) {
        computers[address].packets.add(value)
      }
    }
  }

  private class Computer(val address: Int,
                         private val vm: VM19,
                         private val network: Network) : VM19.VmIO {
    var addressSet = false
    var packets = mutableListOf<XY>()
    var inCount = 0
    var outCount = 0
    val output = Array(3) { 0L }

    init {
      vm.vmIo = this
    }

    fun step() {
      if (!vm.isHalted) {
        vm.step()
      } else {
        println("Computer [$address] has halted.")
      }
    }

    override fun onInput(): Long {
      if (!addressSet) {
        addressSet = true
        return address.toLong()
      }
      if (packets.isEmpty()) {
        return -1
      }

      var result = 0L
      if (inCount == 0) {
        result = packets[0].x
      } else if (inCount == 1) {
        result = packets[0].y
        packets = packets.drop(1).toMutableList()
      }
      inCount = (inCount + 1) % 2
      return result
    }

    override fun onOutput(out: Long) {
      output[outCount] = out
      if (outCount == 2) {
        network.sendPacket(output[0].toInt(), XY(output[1], output[2]))
      }
      outCount = (outCount + 1) % 3
    }
  }

  private data class XY(val x: Long, val y: Long)
}