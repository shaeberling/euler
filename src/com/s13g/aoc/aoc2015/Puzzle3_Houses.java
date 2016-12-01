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

import java.util.HashSet;
import java.util.Set;

/**
 * http://adventofcode.com/2015/day/3
 */
public class Puzzle3_Houses implements Puzzle {

  @Override
  public Solution solve(String input) {
    int partA;
    char[] directions = input.toCharArray();
    {
      Set<String> visitedHouses = new HashSet<>();
      Location locSanta = new Location();
      visitedHouses.add(locSanta.toString());
      for (char direction : directions) {
        visitedHouses.add(locSanta.go(direction).toString());
      }
      partA = visitedHouses.size();
    }

    {
      Set<String> visitedHouses = new HashSet<>();
      Location locSanta = new Location();
      Location locRobot = new Location();
      boolean santasTurn = true;
      visitedHouses.add(locSanta.toString());
      for (char direction : directions) {
        Location activeLocation = santasTurn ? locSanta : locRobot;
        visitedHouses.add(activeLocation.go(direction).toString());
        santasTurn = !santasTurn;
      }
      return new Solution(partA, visitedHouses.size());
    }
  }

  private static class Location {
    int x = 0;
    int y = 0;

    Location go(char direction) {
      if (direction == '>') {
        ++x;
      } else if (direction == '<') {
        --x;
      } else if (direction == '^') {
        ++y;
      } else if (direction == 'v') {
        --y;
      }
      return this;
    }

    @Override
    public String toString() {
      return x + "x" + y;
    }
  }
}
