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

package com.s13g.aoc;

/**
 * All AOC puzzles implement this.
 */
public interface Puzzle {
  class Solution {
    final String solutionA;
    final String solutionB;

    public Solution(String solutionA, String solutionB) {
      this.solutionA = solutionA;
      this.solutionB = solutionB;
    }

    public Solution(long solutionA, long solutionB) {
      this.solutionA = String.valueOf(solutionA);
      this.solutionB = String.valueOf(solutionB);
    }
  }

  Solution solve(String input);

}
