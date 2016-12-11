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

import com.s13g.Pair;
import com.s13g.aoc.Puzzle;

import java.util.Optional;

/**
 * http://adventofcode.com/2016/day/9
 */
public class Puzzle9 implements Puzzle {
  @Override
  public Solution solve(String input) {
    return new Solution(String.valueOf(solveA(input)), String.valueOf(solveB(input)));
  }

  private long solveB(String input) {
    int markerStart = input.indexOf('(');
    if (markerStart == -1) {
      return input.length();
    } else if (markerStart != 0) {
      String substring = input.substring(markerStart);
      return markerStart + solveB(substring);
    }

    // Now we have a string that starts with a marker.
    int markerEnd = input.indexOf(')');
    Optional<Pair<Integer>> marker = parseMarker(
        input.substring(markerStart + 1, markerEnd));
    if (!marker.isPresent()) {
      return 1 + solveB(input.substring(1));
    }

    String substring = input.substring(markerEnd + 1, markerEnd + 1 + marker.get().first);
    String remainder = input.substring(markerEnd + 1 + substring.length());
    return solveB(substring) * marker.get().second + solveB(remainder);
  }

  // TODO: Part A can be rolled into part B.
  private long solveA(String input) {
    long resultA = 0;

    while (true) {
      int markerStart = input.indexOf('(');
      if (markerStart == -1) {
        resultA += input.length();
        break;
      }
      int markerEnd = input.indexOf(')', markerStart);
      Optional<Pair<Integer>> marker = parseMarker(
          input.substring(markerStart + 1, markerEnd));
      resultA += (markerStart);
      input = input.substring(markerEnd + 1);
      if (marker.isPresent()) {
        int numChars = marker.get().first;
        int repeat = marker.get().second;
        resultA += numChars * repeat;
        input = input.substring(numChars);
      }
    }
    return resultA;
  }

  private Optional<Pair<Integer>> parseMarker(String marker) {
    String[] parts = marker.split("x");
    if (parts.length != 2) {
      return Optional.empty();
    }
    try {
      int numChars = Integer.parseInt(parts[0]);
      int repeat = Integer.parseInt(parts[1]);
      return Optional.of(new Pair<>(numChars, repeat));
    } catch (NumberFormatException ex) {
      return Optional.empty();
    }
  }
}
