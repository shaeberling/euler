/*
 * Copyright 2016 Sascha Haeberling
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

package com.s13g.aoc.aoc2016;

import com.s13g.ArrayUtil;
import com.s13g.aoc.Puzzle;

/**
 * http://adventofcode.com/2016/day/3
 */
public class Puzzle3_Triangles implements Puzzle {
  @Override
  public Solution solve(String input) {
    return new Solution(solveForData(input), solvePart2(input));
  }

  private int solvePart2(String input) {
    int validTriangles = 0;
    int[][] cols = ArrayUtil.splitAsIntColumns(input);
    for (int[] values : cols) {
      for (int j = 0; j < values.length; j += 3) {
        validTriangles += isValidTriangle(values[j], values[j + 1], values[j + 2]) ? 1 : 0;
      }
    }
    return validTriangles;
  }

  private int solveForData(String input) {
    int validTriangles = 0;
    for (String triangle : input.split("\\r?\\n")) {
      int sides[] = ArrayUtil.splitAsInt(triangle.trim(), "\\s+");
      validTriangles += isValidTriangle(sides[0], sides[1], sides[2]) ? 1 : 0;
    }
    return validTriangles;
  }

  private static boolean isValidTriangle(int a, int b, int c) {
    return (a + b > c) && (b + c > a) && (c + a > b);
  }
}
