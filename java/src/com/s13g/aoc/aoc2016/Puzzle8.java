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

/**
 * http://adventofcode.com/2016/day/8
 */
public class Puzzle8 implements Puzzle {
  private static final int WIDTH = 50;
  private static final int HEIGHT = 6;
  private boolean[][] mPixels = new boolean[HEIGHT][WIDTH];

  @Override
  public Solution solve(String input) {
    int countA = 0;
    String[] lines = input.split("\\r?\\n");
    for (String line : lines) {
      if (line.startsWith("rect ")) {
        rect(line.substring("rect ".length()));
      } else if (line.startsWith("rotate row y=")) {
        rotateRow(line.substring("rotate row y=".length()));
      } else if (line.startsWith("rotate column x=")) {
        rotateCol(line.substring("rotate column x=".length()));
      } else {
        throw new RuntimeException("Unknown command: '" + line + "'. ");
      }
    }

    String code = "";
    for (int y = 0; y < HEIGHT; ++y) {
      for (int x = 0; x < WIDTH; ++x) {
        countA += mPixels[y][x] ? 1 : 0;
        code += (mPixels[y][x] ? "X" : " ");
      }
      code += "\n";
    }
    return new Solution(String.valueOf(countA), code);
  }

  private void rect(String line) {
    String[] parts = line.split("x");
    int width = Integer.parseInt(parts[0]);
    int height = Integer.parseInt(parts[1]);
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        mPixels[y][x] = true;
      }
    }
  }

  private void rotateRow(String line) {
    String[] parts = line.split(" by ");
    int row = Integer.parseInt(parts[0]);
    int amount = Integer.parseInt(parts[1]);

    for (int i = 0; i < amount; ++i) {
      boolean oldLast = mPixels[row][WIDTH - 1];
      System.arraycopy(mPixels[row], 0, mPixels[row], 1, WIDTH - 1);
      mPixels[row][0] = oldLast;
    }
  }

  private void rotateCol(String line) {
    String[] parts = line.split(" by ");
    int col = Integer.parseInt(parts[0]);
    int amount = Integer.parseInt(parts[1]);

    for (int i = 0; i < amount; ++i) {
      boolean oldLast = mPixels[HEIGHT - 1][col];
      for (int y = HEIGHT - 1; y > 0; --y) {
        mPixels[y][col] = mPixels[y - 1][col];
      }
      mPixels[0][col] = oldLast;
    }
  }
}
