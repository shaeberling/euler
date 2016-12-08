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

package com.s13g.util;

import com.google.common.collect.Lists;
import com.s13g.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Counts characters.
 */
public class CharCountCollection {
  public static class CharCount {
    public final char c;
    int count = 0;

    CharCount(char c) {
      this.c = c;
    }
  }

  private Map<Character, CharCount> counts = new HashMap<>();

  public void add(char c) {
    if (!counts.containsKey(c)) {
      counts.put(c, new CharCount(c));
    } else {
      counts.get(c).count++;
    }
  }

  public List<CharCount> getSortedCounts() {
    List<CharCount> sortedCounts = Lists.newArrayList(counts.values());
    Collections.sort(sortedCounts, (a, b) -> b.count != a.count ? b.count - a.count : a.c - b.c);
    return sortedCounts;
  }

  public Pair<Character> getMostLeastCommon() {
    List<CharCount> sortedCounts = getSortedCounts();
    return new Pair<>(sortedCounts.get(0).c, sortedCounts.get(sortedCounts.size() - 1).c);
  }
}
