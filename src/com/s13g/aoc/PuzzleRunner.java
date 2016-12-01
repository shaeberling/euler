package com.s13g.aoc;

import com.s13g.FileUtil;
import com.s13g.aoc.aoc2015.Puzzle1_Floors;
import com.s13g.aoc.aoc2015.Puzzle2_WrappingPaper;
import com.s13g.aoc.aoc2015.Puzzle3_Houses;

import java.io.IOException;
import java.util.Locale;

/**
 * Runs all the Advent of Code puzzle solvers.
 */
public class PuzzleRunner {
  private static final String NEW_TMPL = "(%s)[ NEW ] - %s --> %s";
  private static final String OK_TMPL = "(%s)[ OK ]  - %s --> %s";
  private static final String FAIL_TMPL = "(%s)[FAIL]  -  %s --> %s (but should be %s)";

  private static PuzzleSetup[] getPuzzles() {
    final String AOC15 = "data/aoc/2015/";
    final String AOC16 = "data/aoc/2016/";
    return new PuzzleSetup[]{
        new PuzzleSetup(AOC15 + "day1.txt", new Puzzle1_Floors(), 138, 1771),
        new PuzzleSetup(AOC15 + "day2.txt", new Puzzle2_WrappingPaper(), 1586300, 3737498),
        new PuzzleSetup(AOC15 + "day3.txt", new Puzzle3_Houses(), 2565, 2639)
    };

  }

  public static void main(String[] args) throws IOException {
    for (PuzzleSetup setup : getPuzzles()) {
      String input = FileUtil.readAsString(setup.inputFileName);
      String className = setup.puzzle.getClass().getSimpleName();
      Puzzle.Solution solution = setup.puzzle.solve(input);
      printResult("A", className, setup.expectedResultA, solution.solutionA);
      printResult("B", className, setup.expectedResultB, solution.solutionB);
    }
  }

  private static void printResult(String prefix, String className, String expected, String result) {
    if (expected == null) {
      print(NEW_TMPL, prefix, className, result);
    } else if (expected.equals(result)) {
      print(OK_TMPL, prefix, className, result);
    } else {
      print(FAIL_TMPL, prefix, className, result, expected);
    }
  }

  private static void print(String format, Object... vars) {
    System.out.println(String.format(Locale.US, format, vars));
  }

  private static class PuzzleSetup {
    final String inputFileName;
    final Puzzle puzzle;
    final String expectedResultA;
    final String expectedResultB;

    private PuzzleSetup(String inputFileName,
                        Puzzle puzzle,
                        String expectedResultA,
                        String expectedResultB) {
      this.inputFileName = inputFileName;
      this.puzzle = puzzle;
      this.expectedResultA = expectedResultA;
      this.expectedResultB = expectedResultB;
    }

    private PuzzleSetup(String inputFileName,
                        Puzzle puzzle,
                        int expectedResultA,
                        int expectedResultB) {
      this.inputFileName = inputFileName;
      this.puzzle = puzzle;
      this.expectedResultA = String.valueOf(expectedResultA);
      this.expectedResultB = String.valueOf(expectedResultB);
    }
  }
}
