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
 * http://adventofcode.com/2016/day/2
 */
public class Puzzle2_Keypad implements Puzzle {
  @Override
  public Solution solve(String input) {
    String[] sets = input.split("\\r?\\n");
    return new Solution(solvePart1(sets), solvePart2(sets));
  }

  private String solvePart1(String[] sets) {
    String codeResult = "";
    for (String set : sets) {
      int pos = 5;
      for (char c : set.toCharArray()) {
        if (c == 'U' && pos > 3) {
          pos -= 3;
        } else if (c == 'D' && pos < 7) {
          pos += 3;
        } else if (c == 'R' && ((pos % 3) != 0)) {
          ++pos;
        } else if (c == 'L' && (((pos + 2) % 3) != 0)) {
          --pos;
        }
      }
      codeResult += String.valueOf(pos);
    }
    return codeResult;
  }

  private String solvePart2(String[] sets) {
    String pad = "__1___234_56789_ABC___D__";
    String codeResult = "";
    for (String set : sets) {
      int posX = 0, posY = 2;
      for (char c : set.toCharArray()) {
        int prevPosX = posX, prevPosY = posY;
        if (c == 'U' && posY > 0) {
          --posY;
        } else if (c == 'D' && posY < 4) {
          ++posY;
        } else if (c == 'L' && posX > 0) {
          --posX;
        } else if (c == 'R' && posX < 4) {
          ++posX;
        }
        if (pad.charAt(posY * 5 + posX) == '_') {
          posX = prevPosX;
          posY = prevPosY;
        }
      }
      codeResult += pad.charAt(posY * 5 + posX);
    }
    return codeResult;
  }
}
