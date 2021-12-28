package com.s13g.aoc.aoc2021

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import kotlin.math.max
import kotlin.math.min

class Day23 : Solver {
  override fun solve(lines: List<String>): Result {
    // FIXME: Parse this.
    val podsPartA = setOf(
      Pod("B", XY(3, 2), false),
      Pod("A", XY(5, 2), false),
      Pod("a", XY(7, 2), false),
      Pod("d", XY(9, 2), false),
      Pod("D", XY(3, 3), false),
      Pod("C", XY(5, 3), false),
      Pod("b", XY(7, 3), false),
      Pod("c", XY(9, 3), false)
    )
    val podsPartB = setOf(
      Pod("B", XY(3, 2), false),
      Pod("A", XY(5, 2), false),
      Pod("a", XY(7, 2), false),
      Pod("d", XY(9, 2), false),
      Pod("D", XY(3, 3), false),
      Pod("C", XY(5, 3), false),
      Pod("B", XY(7, 3), false),
      Pod("A", XY(9, 3), false),
      Pod("D", XY(3, 4), false),
      Pod("B", XY(5, 4), false),
      Pod("A", XY(7, 4), false),
      Pod("C", XY(9, 4), false),
      Pod("D", XY(3, 5), false),
      Pod("C", XY(5, 5), false),
      Pod("b", XY(7, 5), false),
      Pod("c", XY(9, 5), false)
    )

    // Example config...
    val examplePartB = setOf(
      Pod("B", XY(3, 2), false),
      Pod("C", XY(5, 2), false),
      Pod("b", XY(7, 2), false),
      Pod("D", XY(9, 2), false),

      Pod("D", XY(3, 3), false),
      Pod("C", XY(5, 3), false),
      Pod("B", XY(7, 3), false),
      Pod("A", XY(9, 3), false),

      Pod("D", XY(3, 4), false),
      Pod("B", XY(5, 4), false),
      Pod("A", XY(7, 4), false),
      Pod("C", XY(9, 4), false),

      Pod("A", XY(3, 5), false),
      Pod("d", XY(5, 5), false),
      Pod("c", XY(7, 5), false),
      Pod("a", XY(9, 5), false)
    )


    val partA = cheapestWayCached(podsPartA)
    val partB = 0
    return Result("$partA", "$partB")

  }

  private val cache = mutableMapOf<Set<Pod>, Int>()
  private fun cheapestWayCached(pods: Set<Pod>): Int {
    if (cache.containsKey(pods)) return cache[pods]!!
    val result = cheapestWay(pods)
    cache[pods] = result
    return result
  }

  private fun cheapestWay(pods: Set<Pod>): Int {
    val state = State(pods)
    if (state.isDone()) {
      println("Done!")
      return 0
    }

    // Sort options by cost
    val options = state.genNextSteps().sortedBy { it.second }
    if (options.isEmpty()) return Int.MAX_VALUE


    var cheapestCost = Int.MAX_VALUE
    for (option in options.toList()) {
      // Do not process if the cost for this step is higher than processing the
      // tail.
      if (option.second >= cheapestCost) continue
      val cost = cheapestWayCached(option.first.pods)
      if (cost != Int.MAX_VALUE) {
        val totalCost = cost + option.second
        cheapestCost = min(cheapestCost, totalCost)
      }
    }
    return cheapestCost
  }


  private data class State(val pods: Set<Pod>) {

    fun genNextSteps(): Set<Pair<State, Int>> {
      if (isDone()) error("Should never be called")

      val results = mutableSetOf<Pair<State, Int>>()

      // For each pod, figure out all the places it can go.
      for (pod in pods) {
        // The pod has already moved but is not yet in its room, it now needs to
        // find its way to its room.
        if (pod.pos.y == 1) {
          // First check of the room the pod needs to go to is empty or only
          // occupied by the correct pod.
          if (isRoomReadyToEnter(pod.id)) {
            // Second, check if the way is clear to enter the room
            val goalX = horLocForType(pod.id)

            var clearToGo = true
            var movesToRoom = 0
            for (x in min(goalX, pod.pos.x)..max(goalX, pod.pos.x)) {
              val tile = get(x, 1)
              if (tile != "." && tile != pod.id) clearToGo = false
              movesToRoom++
            }
            if (clearToGo) {
              // Check if the lower room is available.
              if (isEmpty(goalX, 3)) {
                val newState = this.newState(Pod(pod.id, XY(goalX, 3), true))
                results.add(Pair(newState, (movesToRoom + 1) * pod.moveCost()))
              } else {
                val newState = this.newState(Pod(pod.id, XY(goalX, 2), true))
                results.add(Pair(newState, (movesToRoom) * pod.moveCost()))
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
              val newState = this.newState(Pod(pod.id, XY(x, y), true))
              results.add(Pair(newState, moves * pod.moveCost()))
            }
            moves++
            x--
          }
          x = pod.pos.x
          moves = pod.pos.y - 1
          // Check right way
          while (get(x, y) == ".") {
            if (x != 3 && x != 5 && x != 7 && x != 9) {
              val newState = this.newState(Pod(pod.id, XY(x, y), true))
              results.add(Pair(newState, moves * pod.moveCost()))
            }
            moves++
            x++
          }
        }
      }
      return results
    }

    private fun newState(replacePod: Pod): State {
      val newState = pods.filter { it.id != replacePod.id }.toMutableSet()
      newState.add(replacePod)
      return State(newState)
    }

    fun isDone() =
      get(3, 2).equals("A", true) && get(3, 3).equals("A", true) &&
          get(5, 2).equals("B", true) && get(5, 3).equals("B", true) &&
          get(7, 2).equals("C", true) && get(7, 3).equals("C", true) &&
          get(9, 2).equals("D", true) && get(9, 3).equals("D", true)

    private fun get(x: Int, y: Int): String {
      val pos = XY(x, y)
      return pods.firstOrNull { it.pos == pos }?.id ?: tileType(pos)
    }

    private fun tileType(pos: XY): String {
      if (pos == XY(3, 2) || pos == XY(3, 3)) return "α"
      if (pos == XY(5, 2) || pos == XY(5, 3)) return "β"
      if (pos == XY(7, 2) || pos == XY(7, 3)) return "γ"
      if (pos == XY(9, 2) || pos == XY(9, 3)) return "δ"
      if (pos.y == 1 && pos.x >= 1 && pos.x <= 11) return "."
      return "#"
    }

    private fun isRoomReadyToEnter(podId: String): Boolean {
      if (podId.equals("A", true)) {
        return get(3, 2).equals("A", true) || get(3, 2) == "α" &&
            get(3, 3).equals("A", true) || get(3, 3) == "α"
      }
      if (podId.equals("B", true)) {
        return get(5, 2).equals("B", true) || get(5, 2) == "β" &&
            get(5, 3).equals("B", true) || get(5, 3) == "β"
      }
      if (podId.equals("C", true)) {
        return get(7, 2).equals("C", true) || get(7, 2) == "γ" &&
            get(7, 3).equals("C", true) || get(7, 3) == "γ"
      }
      if (podId.equals("D", true)) {
        return get(9, 2).equals("D", true) || get(9, 2) == "δ" &&
            get(9, 3).equals("D", true) || get(9, 3) == "δ"
      }
      error("Unknown pod ID: $podId")
    }

    private fun horLocForType(podId: String): Int {
      return when (podId.toUpperCase()) {
        "A" -> 3
        "B" -> 5
        "C" -> 7
        "D" -> 9
        else -> {
          error("Unknown pod ID: $podId")
        }
      }
    }

    private fun isHome(pod: Pod) =
      pod.pos.y > 1 && pod.pos.x == horLocForType(pod.id)

    private fun isEmpty(x: Int, y: Int): Boolean {
      val pos = XY(x, y)
      return pods.count { it.pos == pos } == 0 && tileType(pos) != "#"
    }

    override fun toString(): String {
      var result = ""
      for (y in 0..4) {
        for (x in 0..12) {
          result += get(x, y)
        }
        result += "\n"
      }
      return result + "\n"
    }
  }

  private data class XY(val x: Int, val y: Int)
  private data class Pod(val id: String, val pos: XY, val moved: Boolean) {
    fun moveCost() = when (id.toUpperCase()) {
      "A" -> 1
      "B" -> 10
      "C" -> 100
      "D" -> 1000
      else -> {
        error("Ugh oh.")
      }
    }
  }
}