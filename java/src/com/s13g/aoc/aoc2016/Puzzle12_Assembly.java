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
 * http://adventofcode.com/2016/day/12
 */
public class Puzzle12_Assembly implements Puzzle {

  @Override
  public Solution solve(String input) {
    String[] inst = input.split("\\r?\\n");
    return new Solution(solveForRegA(inst, new int[4]), solveForRegA(inst, new int[]{0, 0, 1, 0}));
  }

  private int solveForRegA(String[] inst, int[] reg) {
    int ip = 0;
    while (ip < inst.length) {
      String[] parts = inst[ip].split(" ");
      switch (parts[0]) {
        case "cpy":
          reg[(int) parts[2].charAt(0) - 97] = getValue(parts[1], reg);
          break;
        case "inc":
          reg[(int) parts[1].charAt(0) - 97]++;
          break;
        case "dec":
          reg[(int) parts[1].charAt(0) - 97]--;
          break;
        case "jnz":
          if (getValue(parts[1], reg) != 0) {
            ip += Integer.parseInt(parts[2]) - 1;
          }
          break;
      }
      ip++;
    }
    return reg[0];
  }

  private int getValue(String v, int reg[]) {
    if (v.length() == 1 && v.charAt(0) >= 97) {
      return reg[(int) v.charAt(0) - 97];
    }
    return Integer.parseInt(v);
  }
}
