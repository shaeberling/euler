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

package com.s13g.aoc.aoc2015;

import com.s13g.aoc.Puzzle;

/**
 * http://adventofcode.com/2015/day/1
 */
public class Puzzle1_Floors implements Puzzle {
  private static final int START_FLOOR = 0;


  @Override
  public Solution solve(String input) {
    char[] inputChars = input.toCharArray();

    int floor = START_FLOOR;
    int solutionB = -1;
    for (int i = 0; i < inputChars.length; ++i) {
      if (inputChars[i] == '(') {
        ++floor;
      } else if (inputChars[i] == ')') {
        --floor;
      }
      if (solutionB < 0 && floor == -1) {
        solutionB = i + 1;
      }
    }
    return new Solution(floor, solutionB);
  }
}
