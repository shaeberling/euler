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

import java.util.LinkedList;
import java.util.List;

/**
 * http://adventofcode.com/2016/day/18
 */
public class Puzzle18 implements Puzzle {
  @Override
  public Solution solve(String input) {
    return new Solution(solve(input, 40), solve(input, 400000));
  }

  int solve(String input, int numLines) {
    List<String> lines = new LinkedList<>();
    lines.add(input);
    while (lines.size() < numLines) {
      String newLine = nextLine(lines.get(lines.size() - 1));
      lines.add(newLine);
    }

    int count = 0;
    for (String line : lines) {
      char[] lineChars = line.toCharArray();
      for (char c : lineChars) {
        if (c == '.') {
          count++;
        }
      }
    }
    return count;
  }

  private String nextLine(String line) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < line.length(); ++i) {
      result.append(isTrap(line, i) ? '^' : '.');
    }
    return result.toString();
  }

  private boolean isTrap(String prevLine, int i) {
    boolean leftTrap = i > 0 && prevLine.charAt(i - 1) == '^';
    boolean centerTrap = i > 0 && prevLine.charAt(i) == '^';
    boolean rightTrap = i < prevLine.length() - 1 && prevLine.charAt(i + 1) == '^';

    return (leftTrap && centerTrap && !rightTrap) ||
        (centerTrap && rightTrap && !leftTrap) ||
        (leftTrap && !centerTrap && !rightTrap) ||
        (!leftTrap && !centerTrap && rightTrap);
  }
}
