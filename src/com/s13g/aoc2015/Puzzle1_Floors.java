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

package com.s13g.aoc2015;

import com.s13g.FileUtil;

import java.io.IOException;

/**
 * http://adventofcode.com/2015/day/1
 */
public class Puzzle1_Floors {
  private static final int START_FLOOR = 0;

  public static void main(String[] args) throws IOException {
    String input = FileUtil.readAsString("data/aoc2015/day1/input.txt");
    char[] inputChars = input.toCharArray();

    int floor = START_FLOOR;
    boolean part2Solved = false;
    for (int i = 0; i < inputChars.length; ++i) {
      if (inputChars[i] == '(') {
        ++floor;
      } else if (inputChars[i] == ')') {
        --floor;
      }
      if (!part2Solved && floor == -1) {
        System.out.println("Part 2 solution: " + (i + 1));
        part2Solved = true;
      }
    }
    System.out.println("Result: " + floor);
  }
}
