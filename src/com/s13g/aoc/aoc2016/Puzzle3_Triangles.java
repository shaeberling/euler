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
    String[] triangles = input.split("\\r?\\n");
    return new Solution(solveForData(triangles), solveForData((mixForPart2(triangles))));
  }

  private String[] mixForPart2(String[] triangles) {
    String[] lines = new String[triangles.length];
    for (int i = 0; i < triangles.length; i += 3) {
      String[] row0 = triangles[i].trim().split("\\s+");
      String[] row1 = triangles[i + 1].trim().split("\\s+");
      String[] row2 = triangles[i + 2].trim().split("\\s+");
      lines[i] = row0[0] + " " + row1[0] + " " + row2[0];
      lines[i + 1] = row0[1] + " " + row1[1] + " " + row2[1];
      lines[i + 2] = row0[2] + " " + row1[2] + " " + row2[2];
    }
    return lines;
  }

  private int solveForData(String[] triangles) {
    int validTriangles = 0;
    for (String triangle : triangles) {
      int sides[] = ArrayUtil.splitAsInt(triangle.trim(), "\\s+");
      if (sides[0] + sides[1] > sides[2] &&
          sides[1] + sides[2] > sides[0] &&
          sides[2] + sides[0] > sides[1]) {
        validTriangles++;
      }
    }
    return validTriangles;
  }
}
