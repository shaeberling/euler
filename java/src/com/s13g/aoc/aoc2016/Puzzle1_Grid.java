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

import java.util.HashSet;
import java.util.Set;

/**
 * http://adventofcode.com/2016/day/1
 */
public class Puzzle1_Grid implements Puzzle {
  @Override
  public Solution solve(String input) {
    String[] instructions = input.split(",");

    Set<String> visitedLocations = new HashSet<>();
    visitedLocations.add("0,0");
    int solutionB = -1;

    int posX = 0, posY = 0, dirX = 0, dirY = 1;
    for (String instruction : instructions) {
      instruction = instruction.trim();
      char direction = instruction.charAt(0);
      if (dirX == 0) {
        dirX = dirY * (direction == 'L' ? -1 : 1);
        dirY = 0;
      } else {
        dirY = dirX * (direction == 'L' ? 1 : -1);
        dirX = 0;
      }
      int amount = Integer.parseInt(instruction.substring(1));
      for (int i = 0; i < amount; ++i) {
        posX += dirX;
        posY += dirY;
        String locStr = posX + "," + posY;
        if (visitedLocations.contains(locStr) && solutionB == -1) {
          solutionB = Math.abs(posX + posY);
        }
        visitedLocations.add(locStr);
      }
    }
    return new Solution(Math.abs(posX + posY), solutionB);
  }
}
