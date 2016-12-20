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
import java.util.Collections;
import java.util.List;

/**
 * http://adventofcode.com/2016/day/20
 */
public class Puzzle20 implements Puzzle {
  @Override
  public Solution solve(String input) {
    List<Range> ranges = new ArrayList<>();
    for (String entry : input.split("\\r?\\n")) {
      String[] parts = entry.split("-");
      ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
    }
    Collections.sort(ranges);

    long numUnblocked = 0;
    long previousMax = -1;
    for (Range r : ranges) {
      if (r.from > previousMax) {
        numUnblocked += (r.from - previousMax - 1);
      }
      previousMax = Math.max(previousMax, r.to);
    }
    numUnblocked += (4294967295L - previousMax);

    long lowestToTest = 0;
    for (Range r : ranges) {
      if (r.from <= lowestToTest) {
        lowestToTest = r.to + 1;
      }
    }
    return new Solution(lowestToTest, numUnblocked);
  }

  static class Range implements Comparable<Range> {
    final long from;
    final long to;

    Range(long from, long to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public int compareTo(Range o) {
      return from - o.from > 0 ? 1 : -1;
    }

    @Override
    public String toString() {
      return from + " - " + to;
    }
  }
}
