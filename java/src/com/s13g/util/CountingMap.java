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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Counts entries.
 */
public class CountingMap {
  Map<Character, Integer> mMap = new HashMap<>();

  public boolean hasAtLastOne(char c) {
    return mMap.containsKey(c) && mMap.get(c) > 0;
  }

  public void add(char c) {
    if (!mMap.containsKey(c)) {
      mMap.put(c, 1);
    } else {
      mMap.put(c, mMap.get(c) + 1);
    }
  }

  public CountingMap addAll(Collection<Character> chars) {
    for (char c : chars) {
      add(c);
    }
    return this;
  }

  public void remove(char c) {
    if (!mMap.containsKey(c) || mMap.get(c) <= 0) {
      throw new RuntimeException("Element not found or zero.");
    }
    mMap.put(c, mMap.get(c) - 1);
  }

  public CountingMap removeAll(Collection<Character> chars) {
    for (char c : chars) {
      remove(c);
    }
    return this;
  }
}
