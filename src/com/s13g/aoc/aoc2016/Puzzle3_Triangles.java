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

import static com.s13g.ArrayUtil.splitAsIntArray;

/**
 * http://adventofcode.com/2016/day/3
 */
public class Puzzle3_Triangles implements Puzzle {
  @Override
  public Solution solve(String input) {
    int[][] values = splitAsIntArray(input);
    int solutionA = 0, solutionB = 0;
    for (int[] value : values) {
      for (int j = 0; j < value.length; j += 3) {
        solutionA += isValidTriangle(value[j], value[j + 1], value[j + 2]) ? 1 : 0;
      }
    }
    for (int j = 0; j < values[0].length; ++j) {
      for (int i = 0; i < values.length; i += 3) {
        solutionB += isValidTriangle(values[i][j], values[i + 1][j], values[i + 2][j]) ? 1 : 0;
      }
    }
    return new Solution(solutionA, solutionB);
  }

  private static boolean isValidTriangle(int a, int b, int c) {
    return (a + b > c) && (b + c > a) && (c + a > b);
  }
}
