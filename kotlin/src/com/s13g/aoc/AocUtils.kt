/*
 * Copyright 2017 Sascha Haeberling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.s13g.aoc

import com.google.common.collect.ImmutableList
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Path
import kotlin.math.abs


/**
 * Reads a file as string.
 */
@Throws(IOException::class)
fun readAsString(file: Path): List<String> {
  return try {
    ImmutableList.copyOf(file.toFile().readLines())
  } catch (e: FileNotFoundException) {
    System.err.println("Cannot read file: ${e.message}")
    emptyList()
  }
}

fun List<Long>.mul(): Long {
  var result: Long = this[0]
  for (idx in 1 until this.size) {
    result *= this[idx]
  }
  return result
}

fun List<Int>.mul(): Int {
  var result: Int = this[0]
  for (idx in 1 until this.size) {
    result *= this[idx]
  }
  return result
}

fun <E> Collection<Collection<E>>.intersectAll(): Set<E> {
  var common = this.first().toSet()
  this.map { it.toMutableSet() }.forEach { common = common.intersect(it) }
  return common
}

data class XY(var x: Int, var y: Int)

fun XY.addTo(other: XY) {
  this.x += other.x
  this.y += other.y
}

fun XY.max() = maxOf(x, y)

/** Rotates the point times*90 degrees around the origin. */
fun XY.rotate90(times: Int, left: Boolean): XY {
  val result = XY(this.x, this.y)
  for (t in 0 until times) {
    val tempX = result.x
    result.x = (if (left) -1 else 1) * result.y; result.y = (if (!left) -1 else 1) * tempX
  }
  return result
}

/** Manhattan distance to the origin */
fun XY.manhattan() = abs(this.x) + abs(this.y)