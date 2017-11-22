package com.s13g.aoc;

import com.s13g.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Runs all the Advent of Code puzzle solvers.
 */
public class PuzzleRunner {
  private static final String NEW_TMPL = "(%s)[ NEW ] - %s --> %s";
  private static final String OK_TMPL = "(%s)[ OK ]  - %s";
  private static final String FAIL_TMPL = "(%s)[FAIL]  -  %s --> %s (but should be %s)";


  public static void main(String[] args) throws IOException {
    File dataDir = getDataDirectory(args);

    for (PuzzleSetup setup : Setups.getPuzzles()) {
      String input = FileUtil.readAsString(new File(dataDir, setup.inputFileName));
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
      print(OK_TMPL, prefix, className);
    } else {
      print(FAIL_TMPL, prefix, className, result, expected);
    }
  }

  private static void print(String format, Object... vars) {
    System.out.println(String.format(Locale.US, format, vars));
  }

  private static File getDataDirectory(String args[]) {
    if (args.length != 1) {
      throw new IllegalArgumentException("One argument expected: Data directory.");
    }
    File dataDir = new File(args[0]);
    if (!dataDir.isDirectory()) {
      throw new IllegalArgumentException("Argument is not a valid directory: " + dataDir.getAbsolutePath());
    }
    return dataDir;
  }

  static class PuzzleSetup {
    final String inputFileName;
    final Puzzle puzzle;
    final String expectedResultA;
    final String expectedResultB;

    PuzzleSetup(String inputFileName,
                        Puzzle puzzle,
                        String expectedResultA,
                        String expectedResultB) {
      this.inputFileName = inputFileName;
      this.puzzle = puzzle;
      this.expectedResultA = expectedResultA;
      this.expectedResultB = expectedResultB;
    }

    PuzzleSetup(String inputFileName,
                Puzzle puzzle,
                long expectedResultA,
                long expectedResultB) {
      this.inputFileName = inputFileName;
      this.puzzle = puzzle;
      this.expectedResultA = String.valueOf(expectedResultA);
      this.expectedResultB = String.valueOf(expectedResultB);
    }
  }
}
