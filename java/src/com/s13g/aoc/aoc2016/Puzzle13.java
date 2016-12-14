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

import com.s13g.Point;
import com.s13g.aoc.Puzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * http://adventofcode.com/2016/day/13
 */
public class Puzzle13 implements Puzzle {
  private static final int FAV_NUM = 1358; //10;
  private static final Point TO_REACH = new Point(31, 39); // new Point(7L, 4L);
  private static final int NUM_STEPS_PART_B = 50;

  private static final Set<Point> mVisited = new HashSet<>();


  @Override
  public Solution solve(String input) {
    return new Solution(shortestPathSolution(false), shortestPathSolution(true));
  }

  private long shortestPathSolution(boolean checkMax) {
    mVisited.clear();
    Set<Point> nextSet = new HashSet<>();
    nextSet.add(new Point(1, 1));
    int count = 0;
    while (!nextSet.contains(TO_REACH) || checkMax) {
      mVisited.addAll(nextSet);
      Set<Point> newSet = new HashSet<>();
      for (Point p : nextSet) {
        checkValid(new Point(p.x - 1, p.y), newSet);
        checkValid(new Point(p.x, p.y - 1), newSet);
        checkValid(new Point(p.x + 1, p.y), newSet);
        checkValid(new Point(p.x, p.y + 1), newSet);
      }
      nextSet.clear();
      nextSet.addAll(newSet);
      if (++count == NUM_STEPS_PART_B && checkMax) {
        return mVisited.size() + nextSet.size();
      }
    }
    return count;
  }

  private void checkValid(Point newP, Set<Point> newSet) {
    if (newP.x < 0 || newP.y < 0) {
      return;
    }
    if (!isWall(newP) && !mVisited.contains(newP)) {
      newSet.add(newP);
    }
  }

  private boolean isWall(Point p) {
    long foo = p.x * p.x + 3 * p.x + 2 * p.x * p.y + p.y + p.y * p.y;
    foo += FAV_NUM;
    String binary = Long.toBinaryString(foo);
    return (binary.chars().filter(c -> c == '1').count() % 2) == 1;
  }
}