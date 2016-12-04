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

import com.google.common.collect.Lists;
import com.s13g.Pair;
import com.s13g.aoc.Puzzle;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http://adventofcode.com/2016/day/4
 */
public class Puzzle4_Doors implements Puzzle {
  private static class CharCount {
    final char c;
    int count = 0;

    private CharCount(char c) {
      this.c = c;
    }
  }

  @Override
  public Solution solve(String input) {
    int sumPartA = 0;
    int resultB = 0;
    for (String entry : input.split("\\r?\\n")) {
      Pair<Integer> result = processDoor(entry);
      sumPartA += result.first;
      resultB += result.second;
    }
    return new Solution(sumPartA, resultB);
  }

  private Pair<Integer> processDoor(String entry) {
    String checksum = entry.substring(entry.indexOf('[') + 1, entry.length() - 1);
    String codeStr = "";
    Map<Character, CharCount> charCounts = new HashMap<>();
    int endOfName = 0;
    char[] charArray = entry.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];
      if (c == '-') {
        continue;
      }
      if (c == '[') {
        break;
      }
      if (Character.isDigit(c)) {
        if (endOfName == 0) {
          endOfName = i;
        }
        codeStr += String.valueOf(c);
        continue;
      }
      if (!charCounts.containsKey(c)) {
        charCounts.put(c, new CharCount(c));
      } else {
        charCounts.get(c).count++;
      }
    }
    List<CharCount> sortedCounts = Lists.newArrayList(charCounts.values());
    Collections.sort(sortedCounts, (a, b) -> b.count != a.count ? b.count - a.count : a.c - b.c);
    for (int i = 0; i < checksum.length(); ++i) {
      if (sortedCounts.get(i).c != checksum.charAt(i)) {
        return new Pair<>(0, 0);
      }
    }
    int code = Integer.parseInt(codeStr);
    String decryptedName = "";
    int codePartB = 0;
    for (char c : entry.substring(0, endOfName - 1).toCharArray()) {
      decryptedName += (c == '-') ? " " : (char) (((c - 'a' + code) % (('z' - 'a') + 1)) + 'a');
      if (decryptedName.equals("northpole object storage")) {
        codePartB = code;
      }
    }
    return new Pair<>(code, codePartB);
  }
}
