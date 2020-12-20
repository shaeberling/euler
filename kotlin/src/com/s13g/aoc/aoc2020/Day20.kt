package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 20: Jurassic Jigsaw ---
 * https://adventofcode.com/2020/day/20
 */
class Day20 : Solver {
  companion object {
    private const val PUZZLE_SIZE = 12  // 12x12 tiles
    private const val TILE_SIZE = 10
    private val tileRegex = """Tile (\d+):""".toRegex()
    private fun index(x: Int, y: Int, n: Int = 10) = y * n + x
    private fun indexP(x: Int, y: Int) = y * PUZZLE_SIZE + x
  }

  override fun solve(lines: List<String>): Result {
    val allTiles = parseInput(lines)

    // Tries to place all the tiles into the 12x12 grid.
    val result = placeNewTile(0, 0, mapOf(), allTiles)

    // Multiply the corner IDs as requested for Part 1.
    val resultA = result[indexP(0, 0)]!!.id.toLong() *
        result[indexP(PUZZLE_SIZE - 1, 0)]!!.id.toLong() *
        result[indexP(0, PUZZLE_SIZE - 1)]!!.id.toLong() *
        result[indexP(PUZZLE_SIZE - 1, PUZZLE_SIZE - 1)]!!.id.toLong()

    // Render the image as requested for Part2 and generate all eight variants.
    val variantsPart2 = genVariants(renderImageForPart2(result))
    // Then try to find the monsters in any of the variants and count remaining '#' tiles.
    val resultB = findSeamonster(variantsPart2)

    return Result("$resultA", "$resultB")
  }

  private fun findSeamonster(datas: List<TileData>): Int {
    // This monster was given in part 2.
    val mWidth = 20
    val mHeight = 3
    val monster = "                  # #    ##    ##    ### #  #  #  #  #  #   "

    for (data in datas) {
      var numMonsters = 0
      for (y in 0 until data.dim - mHeight) {
        for (x in 0 until data.dim - mWidth) {
          if (data.matches(monster, mWidth, mHeight, x, y)) numMonsters++
        }
      }
      // Monsters don't overlap, so we can just count the numbers of monsters and subtract
      // that many '#' findings.
      if (numMonsters > 0)
        return data.data.count { it == '#' } - (numMonsters * monster.count { it == '#' })
    }
    error("Cannot find any monsters.")
  }

  /** Based on result from Part 1, produce the image that will serve as base for Part 2. */
  private fun renderImageForPart2(placed: Map<Int, TileData>): TileData {
    // We're going to cut 1 pixel from each edge of each tile before glueing them together.
    val sideLength = (TILE_SIZE - 2) * PUZZLE_SIZE
    val result = CharArray(sideLength * sideLength) { ' ' }

    for (tileX in 0 until PUZZLE_SIZE) {
      for (tileY in 0 until PUZZLE_SIZE) {
        val startX = tileX * (TILE_SIZE - 2)
        val startY = tileY * (TILE_SIZE - 2)
        val tile = placed[indexP(tileX, tileY)]!!.data
        for (y in 1 until TILE_SIZE - 1) {
          for (x in 1 until TILE_SIZE - 1) {
            result[index(x + startX - 1, y + startY - 1, sideLength)] = tile[index(x, y, TILE_SIZE)]
          }
        }
      }
    }
    return TileData(0, String(result), sideLength)
  }

  /** Tries to place a new tile at location x,y. If successful, will call itself recursively for next tile . */
  private fun placeNewTile(x: Int, y: Int, placed: Map<Int, TileData>, allTiles: Map<Int, List<TileData>>): Map<Int, TileData> {
    // The tile we want to place.
    val pos = indexP(x, y)
    assert(pos !in placed)

    val placedCopy = placed.toMutableMap()

    // Check all remaining tiles
    for (tile in allTiles) {
      if (tile.key in placed.values.map { it.id }) continue  // Already placed -> skip.

      // Check all the variants
      for (variant in tile.value) {
        placedCopy[pos] = variant

        // If placement is valid as to the rules....
        if (isValidTile(x, y, placedCopy)) {
          // Chose next coordinate and repeat recursively.
          var newX = x + 1
          var newY = y
          if (newX == PUZZLE_SIZE) {
            newX = 0
            newY++
          }
          if (newY == PUZZLE_SIZE) {
            // We found the final solution!
            return placedCopy
          }
          // If we were unable to complete the placement recursively, replace our current
          // candidate placement by continuing the loop (back track).
          val result = placeNewTile(newX, newY, placedCopy, allTiles)
          if (result.isNotEmpty()) return result
        }
      }
    }
    return emptyMap()
  }

  /** Checks if the tile at location x,y is valid by matching its edges with neighbors' edges. */
  private fun isValidTile(x: Int, y: Int, placed: Map<Int, TileData>): Boolean {
    val currentTile = placed[indexP(x, y)]!!
    if (y > 0) {
      val topOfIdx = indexP(x, y - 1)
      if (topOfIdx in placed) {
        val bottomEdgeOfTop = placed[topOfIdx]!!.edges[2]
        val topEdgeOfCurrent = currentTile.edges[0]
        if (bottomEdgeOfTop != topEdgeOfCurrent) return false
      }
    }
    if (x < 2) {
      val rightOfIdx = indexP(x + 1, y)
      if (rightOfIdx in placed) {
        val leftEdgeOfRight = placed[rightOfIdx]!!.edges[3]
        val rightEdgeOfCurrent = currentTile.edges[1]
        if (leftEdgeOfRight != rightEdgeOfCurrent) return false
      }
    }
    if (y < 2) {
      val bottomOfIdx = indexP(x, y + 1)
      if (bottomOfIdx in placed) {
        val topEdgeOfBottom = placed[bottomOfIdx]!!.edges[0]
        val bottomEdgeOfCurrent = currentTile.edges[2]
        if (topEdgeOfBottom != bottomEdgeOfCurrent) return false
      }
    }
    if (x > 0) {
      val leftOfIdx = indexP(x - 1, y)
      if (leftOfIdx in placed) {
        val rightEdgeOfLeft = placed[leftOfIdx]!!.edges[1]
        val leftEdgeOfCurrent = currentTile.edges[3]
        if (rightEdgeOfLeft != leftEdgeOfCurrent) return false
      }
    }
    return true
  }

  /** Represents a single tile. */
  private class TileData(val id: Int, val data: String, val dim: Int = TILE_SIZE) {
    val edges: List<String>

    init {
      // Generate the four edges as a list of strings so we can match them easily.
      var top = ""
      for (x in 0 until dim) top += data[index(x, 0)]
      var bottom = ""
      for (x in 0 until dim) bottom += data[index(x, dim - 1)]
      var left = ""
      for (y in 0 until dim) left += data[index(0, y)]
      var right = ""
      for (y in 0 until dim) right += data[index(dim - 1, y)]
      edges = listOf(top, right, bottom, left)
    }

    /** Gets a single pixel .*/
    fun get(x: Int, y: Int) = data[index(x, y, dim)]

    /** Returns a copy of a 90 degrees CW rotated tile. */
    fun rotateRight(): TileData {
      var rightRot = ""
      for (y in 0 until dim) {
        for (x in 0 until dim) {
          rightRot += data[index(y, dim - 1 - x, dim)]
        }
      }
      return TileData(id, rightRot, dim)
    }

    /** Returns a copy of a horizontally flipped tile. */
    fun horFlip(): TileData {
      var flipped = ""
      for (y in 0 until dim) {
        for (x in 0 until dim) {
          flipped += data[index(dim - 1 - x, y, dim)]
        }
      }
      return TileData(id, flipped, dim)
    }

    /** Tries to match all '#' in given pattern with given dimensions in given location. */
    fun matches(pattern: String, width: Int, height: Int, x: Int, y: Int): Boolean {
      for (my in 0 until height) {
        for (mx in 0 until width) {
          if (pattern[width * my + mx] == '#' && get(x + mx, y + my) != '#') return false
        }
      }
      return true
    }
  }

  /** Generates all 8 variants of this tile (rotated, flipped, both).*/
  private fun genVariants(orig: TileData): List<TileData> {
    val result = mutableListOf(orig)
    for (i in 0..2) result.add(result.last().rotateRight())
    result.add(orig.horFlip())
    for (i in 0..2) result.add(result.last().rotateRight())
    return result
  }

  private fun parseInput(lines: List<String>): Map<Int, List<TileData>> {
    val input = mutableMapOf<Int, TileData>()
    var currentPic = ""
    var currentId = 0
    for (line in lines) {
      when {
        line.startsWith("Tile") -> {
          currentId = tileRegex.find(line)!!.destructured.component1().toInt()
          currentPic = ""
        }
        line.isBlank() -> input[currentId] = TileData(currentId, currentPic)
        else -> currentPic += line
      }
    }
    return input.map { (k, v) -> Pair(k, genVariants(v)) }.associate { it }
  }
}
