package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.max
import kotlin.math.min

/**
 * --- Day 23: Amphipod ---
 * https://adventofcode.com/2021/day/23
 */
class Day23 : Solver {

  companion object {
    val podCost = mutableMapOf(
      "A" to 1,
      "B" to 10,
      "C" to 100,
      "D" to 1000
    )
  }

  override fun solve(lines: List<String>): Result {
    // These are taken from the input.
    val podsPartA = setOf(
      Pod("B", XY(3, 2)),
      Pod("A", XY(5, 2)),
      Pod("A", XY(7, 2)),
      Pod("D", XY(9, 2)),
      Pod("D", XY(3, 3)),
      Pod("C", XY(5, 3)),
      Pod("B", XY(7, 3)),
      Pod("C", XY(9, 3))
    )
    val podsPartB = setOf(
      Pod("B", XY(3, 2)),
      Pod("A", XY(5, 2)),
      Pod("A", XY(7, 2)),
      Pod("D", XY(9, 2)),
      Pod("D", XY(3, 3)),
      Pod("C", XY(5, 3)),
      Pod("B", XY(7, 3)),
      Pod("A", XY(9, 3)),
      Pod("D", XY(3, 4)),
      Pod("B", XY(5, 4)),
      Pod("A", XY(7, 4)),
      Pod("C", XY(9, 4)),
      Pod("D", XY(3, 5)),
      Pod("C", XY(5, 5)),
      Pod("B", XY(7, 5)),
      Pod("C", XY(9, 5))
    )

    val partA = cheapestWayCached(podsPartA, WorldData(false))
    val partB = cheapestWayCached(podsPartB, WorldData(true))
    return Result("$partA", "$partB")

  }

  // Memoization for the 'cheapestWay' function.
  private val cache = mutableMapOf<Set<Pod>, Int>()
  private fun cheapestWayCached(pods: Set<Pod>, world: WorldData): Int {
    if (cache.containsKey(pods)) return cache[pods]!!
    val result = cheapestWay(pods, world)
    cache[pods] = result
    return result
  }

  private fun cheapestWay(pods: Set<Pod>, world: WorldData): Int {
    val state = State(pods, world)
    if (state.isDone()) {
      return 0
    }

    // Sort options by cost. If no options exist, return immediately.
    val options = state.genNextSteps().sortedBy { it.second }
    if (options.isEmpty()) return Int.MAX_VALUE

    var cheapestCost = Int.MAX_VALUE
    for (option in options) {
      // Do not process if the cost for this step is higher than processing the
      // tail.
      if (option.second >= cheapestCost) continue
      val cost = cheapestWayCached(option.first.pods, world)
      if (cost != Int.MAX_VALUE) {
        val totalCost = cost + option.second
        cheapestCost = min(cheapestCost, totalCost)
      }
    }
    return cheapestCost
  }

  private data class State(val pods: Set<Pod>, val world: WorldData) {
    fun genNextSteps(): Set<Pair<State, Int>> {
      if (isDone()) error("Should never be called")

      val results = mutableSetOf<Pair<State, Int>>()

      // For each pod, figure out all the places it can go.
      for (pod in pods) {
        // The pod has already moved into the hallways it now needs to find its
        // way to its room.
        if (pod.pos.y == 1) {
          // First check of the room the pod needs to go to is empty or only
          // occupied by the correct pod.
          if (isRoomReadyToEnter(pod.id)) {
            // Second, check if the way is clear to enter the room
            val goalX = world.horLocForType(pod.id)

            var clearToGo = true
            var movesToRoom = 0
            for (x in min(goalX, pod.pos.x)..max(goalX, pod.pos.x)) {
              val tile = get(x, 1)
              if (tile != "." && x != pod.pos.x) {
                clearToGo = false
                break
              }
              movesToRoom++
            }
            if (clearToGo) {
              // What's the lowest slot in the room that's available...
              for (y in world.vertLocsForRooms()) {
                if (isEmpty(goalX, y)) {
                  val newState =
                    this.newState(pod, Pod(pod.id, XY(goalX, y), true))
                  results.add(Pair(newState, (movesToRoom) * podCost[pod.id]!!))
                  break
                }
                movesToRoom++
              }
            }
          }
        }
      }
      // Prefer moves that bring pods into their rooms. No need to eval other
      // options, so return early. This will speed up things significantly.
      if (results.isNotEmpty()) return results

      for (pod in pods) {
        // If the pod has not moved (aka is still in the room) and it can move
        // up...
        if (!pod.moved && isEmpty(pod.pos.x, pod.pos.y - 1)) {
          val y = 1
          var x = pod.pos.x
          var moves = pod.pos.y - 1

          // Check left way
          while (get(x, y) == ".") {
            if (x != 3 && x != 5 && x != 7 && x != 9) {
              val newState = this.newState(pod, Pod(pod.id, XY(x, y), true))
              results.add(Pair(newState, moves * podCost[pod.id]!!))
            }
            moves++
            x--
          }
          x = pod.pos.x
          moves = pod.pos.y - 1
          // Check right way
          while (get(x, y) == ".") {
            if (x != 3 && x != 5 && x != 7 && x != 9) {
              val newState = this.newState(pod, Pod(pod.id, XY(x, y), true))
              results.add(Pair(newState, moves * podCost[pod.id]!!))
            }
            moves++
            x++
          }
        }
      }
      return results
    }

    private fun newState(oldPod: Pod, replacePod: Pod): State {
      val newState = pods.filter { it != oldPod }.toMutableSet()
      newState.add(replacePod)
      return State(newState, world)
    }

    fun isDone() = world
      .podIds.sumBy { podID ->
        world.roomLocations(podID)
          .count { loc -> get(loc.x, loc.y) != podID }
      } == 0

    private fun get(x: Int, y: Int): String {
      val pos = XY(x, y)
      return pods.firstOrNull { it.pos == pos }?.id ?: world.tileTypes[pos]!!
    }

    private fun isRoomReadyToEnter(podId: String): Boolean {
      return world.roomLocations(podId).map { get(it.x, it.y) }
        .count { it != podId && it != "." } == 0
    }

    private fun isEmpty(x: Int, y: Int): Boolean {
      val pos = XY(x, y)
      return pods.count { it.pos == pos } == 0 && world.tileTypes[pos] != "#"
    }
  }

  private data class XY(val x: Int, val y: Int)
  private data class Pod(
    val id: String,
    val pos: XY,
    val moved: Boolean = false
  )

  private class WorldData(val partB: Boolean) {
    val podIds = listOf("A", "B", "C", "D")
    private val locsForRoom = podIds.associateWith { genLocsForRoom(it) }
    val tileTypes = genTileTypes()


    private fun genTileTypes(): Map<XY, String> {
      val result = mutableMapOf<XY, String>()
      for (y in 0..(if (partB) 6 else 4)) {
        for (x in 0..12) {
          val loc = XY(x, y)
          result[loc] = tileType(loc)
        }
      }
      return result
    }

    private fun tileType(pos: XY): String {
      if (pos in locsForRoom["A"]!!) return "."
      if (pos in locsForRoom["B"]!!) return "."
      if (pos in locsForRoom["C"]!!) return "."
      if (pos in locsForRoom["D"]!!) return "."
      if (pos.y == 1 && pos.x >= 1 && pos.x <= 11) return "."
      return "#"
    }

    fun horLocForType(podId: String): Int {
      return when (podId) {
        "A" -> 3
        "B" -> 5
        "C" -> 7
        "D" -> 9
        else -> {
          error("Unknown pod ID: $podId")
        }
      }
    }

    fun roomLocations(podId: String): List<XY> {
      return locsForRoom[podId]!!
    }

    fun vertLocsForRooms() = if (partB) {
      listOf(2, 3, 4, 5)
    } else {
      listOf(2, 3)
    }

    private fun genLocsForRoom(podId: String): List<XY> {
      val x = horLocForType(podId)
      return vertLocsForRooms().map { XY(x, it) }
    }

  }
}