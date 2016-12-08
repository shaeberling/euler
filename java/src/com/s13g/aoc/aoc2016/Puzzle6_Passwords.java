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

import com.s13g.ArrayUtil;
import com.s13g.Pair;
import com.s13g.aoc.Puzzle;
import com.s13g.util.CharCountCollection;

/**
 * http://adventofcode.com/2016/day/6
 */
public class Puzzle6_Passwords implements Puzzle {
  @Override
  public Solution solve(String input) {
    String[] columns = ArrayUtil.reverseColsAndRows(input);
    String passwordA = "";
    String passwordB = "";
    for (String line : columns) {
      CharCountCollection charCollection = new CharCountCollection();
      for (char c : line.toCharArray()) {
        charCollection.add(c);
      }
      Pair<Character> mostLeastCommon = charCollection.getMostLeastCommon();
      passwordA += mostLeastCommon.first;
      passwordB += mostLeastCommon.second;
    }
    return new Solution(passwordA, passwordB);
  }
}
