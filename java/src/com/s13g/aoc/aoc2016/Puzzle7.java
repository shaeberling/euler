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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.s13g.aoc.CollectionsUtil.containsAny;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/**
 * http://adventofcode.com/2016/day/7
 * <p>
 * In a lambda mood. ;)
 */
public class Puzzle7 implements Puzzle {
  @Override
  public Solution solve(String input) {
    int countA = 0, countB = 0;
    for (String line : input.split("\\r?\\n")) {
      List<String> out = new LinkedList<>(), in = new LinkedList<>();
      boolean isOutside = !line.startsWith("[");
      for (String segment : line.split("(\\[)|(\\])")) {
        (isOutside ? out : in).add(segment);
        isOutside = !isOutside;
      }
      countA += (out.stream().map(String::toCharArray).anyMatch(this::hasTls) &&
          !in.stream().map(String::toCharArray).anyMatch(this::hasTls)) ? 1 : 0;
      countB += containsAny(in, getAbas(out).stream().map(s -> "" + s[1] + s[0] + s[1])
          .collect(toList())) ? 1 : 0;
    }
    return new Solution(countA, countB);
  }

  private boolean hasTls(char[] s) {
    return range(0, s.length - 3)
        .anyMatch(i -> s[i] != s[i + 1] && s[i] == s[i + 3] && s[i + 1] == s[i + 2]);
  }

  private Set<char[]> getAbas(Collection<String> strings) {
    Set<char[]> abas = new HashSet<>();
    for (String str : strings) {
      char[] c = str.toCharArray();
      range(0, c.length - 2).filter(i -> c[i] != c[i + 1] && c[i] == c[i + 2])
          .mapToObj(i -> "" + c[i] + c[i + 1] + c[i + 2])
          .map(String::toCharArray).forEach(abas::add);
    }
    return abas;
  }
}
