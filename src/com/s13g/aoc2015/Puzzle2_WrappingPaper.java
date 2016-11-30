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

package com.s13g.aoc2015;

import com.s13g.FileUtil;

import java.io.IOException;
import java.util.Arrays;

/**
 * http://adventofcode.com/2015/day/2
 */
public class Puzzle2_WrappingPaper {
  private static class Box {
    private final int mSide1;
    private final int mSide2;
    private final int mSide3;
    private final int mVolume;
    private final int mSmallestTwo[];

    static Box from(String dimensionStr) {
      String[] measures = dimensionStr.split("x");
      int length = Integer.parseInt(measures[0]);
      int width = Integer.parseInt(measures[1]);
      int height = Integer.parseInt(measures[2]);
      return new Box(length, width, height);
    }

    private Box(int length, int width, int height) {
      mSide1 = width * height;
      mSide2 = width * length;
      mSide3 = height * length;
      mVolume = width * height * length;
      mSmallestTwo = getSmallestTwo(width, height, length);
    }

    private static int[] getSmallestTwo(int a, int b, int c) {
      int[] input = new int[]{a, b, c};
      Arrays.sort(input);
      return new int[]{input[0], input[1]};
    }


    int getPaperNeeded() {
      return (2 * mSide1) + (2 * mSide2) + (2 * mSide3) + getSmallestSide();
    }

    int getSmallestSide() {
      return Math.min(Math.min(mSide1, mSide2), mSide3);
    }

    int getBowLength() {
      return mSmallestTwo[0] * 2 + mSmallestTwo[1] * 2 + mVolume;
    }
  }

  public static void main(String[] args) throws IOException {
    String input = FileUtil.readAsString("data/aoc2015/day2/input.txt");
    String[] dimensions = input.split("\n");

    int totalPaper = 0;
    int totalBowLength = 0;
    for (String dimension : dimensions) {
      Box box = Box.from(dimension);
      totalPaper += box.getPaperNeeded();
      totalBowLength += box.getBowLength();
    }
    System.out.println("Paper needed : " + totalPaper);
    System.out.println("Ribbon needed: " + totalBowLength);
  }
}
