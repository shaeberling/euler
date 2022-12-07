package com.s13g.aoc.aoc2022

import com.s13g.aoc.Result
import com.s13g.aoc.Solver
import com.s13g.aoc.resultFrom

/**
 * --- Day 7: No Space Left On Device ---
 * https://adventofcode.com/2022/day/7
 */
class Day7 : Solver {
  override fun solve(lines: List<String>): Result {
    val root = FileItem("/", null, 0)
    var curr = root
    for (line in lines) {
      if (line == "$ cd /") curr = root
      else if (line.startsWith("$ cd ..")) curr = curr.parent!!
      else if (line.startsWith("$ cd ")) curr =
        curr.children[line.substring("$ cd ".length)]!!
      else if (line[0] != '$') {  // Always follows "$ ls".
        val split = line.split(" ")
        curr.children[split[1]] =
          if (split[0] == "dir") FileItem(split[1], curr, 0)
          else FileItem(split[1], curr, split[0].toLong())
      }
    }
    var sumPart1 = 0L
    val dirsBigEnough = mutableListOf<FileItem>()
    val spaceToFree = 30000000 - (70000000 - root.totalSize())
    for (dir in root.getAllDirs()) {
      if (dir.totalSize() <= 100000) sumPart1 += dir.totalSize()
      if (dir.totalSize() >= spaceToFree) dirsBigEnough.add(dir)
    }
    val sizePart2 = dirsBigEnough.minOf { it.totalSize() }
    return resultFrom(sumPart1, sizePart2)
  }

  data class FileItem(
    val name: String,
    val parent: FileItem?,
    val size: Long,
    val children: MutableMap<String, FileItem> = mutableMapOf()
  ) {
    fun totalSize(): Long = children.values.sumOf { it.totalSize() } + size

    fun getAllDirs(): List<FileItem> =
      if (this.size == 0L) children.values.filter { it.size == 0L }
        .map { it.getAllDirs() + it }.flatten() else listOf()
  }
}