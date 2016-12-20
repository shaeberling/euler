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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * http://adventofcode.com/2016/day/19
 */
public class Puzzle19 implements Puzzle {

  @Override
  public Solution solve(String inputStr) {
    int input = Integer.parseInt(inputStr);
    return new Solution(solveA(input), solveB(input));
  }

  // Slow ...
  // Could do https://www.youtube.com/watch?v=uCsD3ZGzMgE
  private int solveB(int input) {
    ArrayList<Integer> elves = new ArrayList<>(input);
    for (int i = 0; i < input; ++i) {
      elves.add(i + 1);
    }

    int i = 0;
    do {
      int victim = (i + (elves.size() / 2)) % elves.size();
      elves.remove(victim);
      if (victim < i) {
        --i;
      }
      i = (i + 1) % elves.size();
    } while (elves.size() > 1);

    return elves.get(0);
  }

  private int solveA(int input) {
    int[] presents = new int[input];
    Arrays.fill(presents, 1);

    int i = 0;
    boolean found;
    do {
      // Find the next thief.
      while (presents[i] <= 0) {
        i = (i + 1) % input;
      }

      found = false;
      // Let's find somebody to steal presents from.
      for (int j = 1; j < input - 1; ++j) {
        int ii = (i + j) % input;
        if (presents[ii] > 0) {
          presents[i] += presents[ii];
          presents[ii] = 0;
          found = true;
          break;
        }
      }
      i = (i + 1) % input;
    } while (found);

    for (int e = 0; e < input; ++e) {
      if (presents[e] > 0) {
        return e + 1;
      }
    }
    throw new RuntimeException("Cannot find a solution");
  }
}
