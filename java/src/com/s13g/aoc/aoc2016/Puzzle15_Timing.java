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
import java.util.List;

/**
 * http://adventofcode.com/2016/day/15
 */
public class Puzzle15_Timing implements Puzzle {
  @Override
  public Solution solve(String input) {
    return new Solution(solve(input, false), solve(input, true));
  }

  private int solve(String input, boolean part2) {
    List<Disc> discs = new ArrayList<>(6);
    int i = 0;
    for (String entry : input.split("\\r?\\n")) {
      String[] parts = entry.split(" ");
      int numPos = Integer.parseInt(parts[3]);
      int pos = Integer.parseInt(parts[11].substring(0, parts[11].length() - 1));
      discs.add(new Disc(numPos, pos).inc(i++));
    }
    if (part2) {
      discs.add(new Disc(11, 0).inc(i));
    }

    int time = 0;
    boolean done;
    do {
      done = true;
      for (Disc disc : discs) {
        if (disc.inc() != 0) {
          done = false;
        }
      }
      time++;
    } while (!done);
    return time - 1;
  }

  private static class Disc {
    final int numPos;
    int pos;

    private Disc(int numPos, int pos) {
      this.numPos = numPos;
      this.pos = pos;
    }

    int inc() {
      pos = (pos + 1) % numPos;
      return pos;
    }

    Disc inc(int n) {
      pos = (pos + n) % numPos;
      return this;
    }
  }
}
