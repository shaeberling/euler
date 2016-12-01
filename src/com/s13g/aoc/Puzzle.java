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

    public Solution(int solutionA, int solutionB) {
      this.solutionA = String.valueOf(solutionA);
      this.solutionB = String.valueOf(solutionB);
    }
  }

  Solution solve(String input);

}
