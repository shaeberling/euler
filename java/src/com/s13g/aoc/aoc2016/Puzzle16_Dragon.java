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

import com.s13g.aoc.Puzzle;

/**
 * http://adventofcode.com/2016/day/16
 */
public class Puzzle16_Dragon implements Puzzle {
  @Override
  public Solution solve(String input) {
    return new Solution(solve(input, 272), solve(input, 35651584));
  }

  String solve(String input, int diskLength) {
    while (input.length() < diskLength) {
      String b = new StringBuilder(input).reverse().toString();
      input = input + "0" + b.replace('0', 'A').replace('1', '0').replace('A', '1');
    }
    input = input.substring(0, diskLength);
    do {
      StringBuilder checksum = new StringBuilder();
      char[] inputChars = input.toCharArray();
      for (int i = 0; i < inputChars.length - 1; i += 2) {
        checksum.append((inputChars[i] == inputChars[i + 1]) ? '1' : '0');
      }
      input = checksum.toString();
    } while (input.length() % 2 == 0);
    return input;
  }
}
