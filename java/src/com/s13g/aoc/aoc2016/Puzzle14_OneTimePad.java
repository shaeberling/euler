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

import com.s13g.aoc.HashUtil;
import com.s13g.aoc.Puzzle;
import com.s13g.util.CountingMap;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.s13g.aoc.StringUtil.toHexString;

/**
 * http://adventofcode.com/2016/day/14
 */
public class Puzzle14_OneTimePad implements Puzzle {
  private static final int INDEX_TO_FIND = 64;
  private static final int NEXT_X_HASHES = 1000;
  private static MessageDigest mMd5 = HashUtil.getMd5OrNull();
  private Map<String, Result> mResults;

  @Override
  public Solution solve(String input) {
    return new Solution(solve(input, false), solve(input, true));
  }

  int solve(String input, boolean superHash) {
    mResults = new HashMap<>();
    CountingMap fives1k = new CountingMap();
    Result rCurrent = getResult(input + String.valueOf(0), superHash);
    for (int i = 1; i <= NEXT_X_HASHES; ++i) {
      fives1k.addAll(getResult(input + String.valueOf(i), superHash).fives);
    }

    // Moving window through the remainder.
    int currentIndex = 0;
    int keysFound = 0;
    do {
      keysFound += fives1k.hasAtLastOne(rCurrent.threes) ? 1 : 0;
      rCurrent = getResult(input + String.valueOf(++currentIndex), superHash);
      Result addTo1k = getResult(input + String.valueOf(currentIndex + NEXT_X_HASHES), superHash);
      fives1k.removeAll(rCurrent.fives).addAll(addTo1k.fives);
    } while (keysFound < INDEX_TO_FIND);
    return (currentIndex - 1);
  }

  private Result getResult(String str, boolean superHash) {
    if (mResults.containsKey(str)) {
      return mResults.get(str);
    }
    Result result = new Result();
    char[] hashedBytes = superHash ? superHash(str) :
        toHexString(mMd5.digest(str.getBytes())).toCharArray();
    for (int i = 0; i < hashedBytes.length - 2; ++i) {
      if (hashedBytes[i] == hashedBytes[i + 1] && hashedBytes[i] == hashedBytes[i + 2]) {
        result.addThree(hashedBytes[i]);
        if (i < hashedBytes.length - 4 && hashedBytes[i] == hashedBytes[i + 3] &&
            hashedBytes[i] == hashedBytes[i + 4]) {
          result.addFive(hashedBytes[i]);
        }
      }
    }
    mResults.put(str, result);
    return result;
  }

  private char[] superHash(String str) {
    String result = str;
    for (int i = 0; i <= 2016; ++i) {
      result = toHexString(mMd5.digest(result.getBytes()));
    }
    return result.toCharArray();
  }


  private static class Result {
    char threes = 0;
    Set<Character> fives = new HashSet<>();

    void addThree(char c) {
      threes = threes == 0 ? c : threes;
    }

    void addFive(char c) {
      fives.add(c);
    }
  }
}
