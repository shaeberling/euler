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

package com.s13g.aoc;

import java.util.Collection;

/**
 * Various collection utils.
 */
public class CollectionsUtil {
  public static boolean containsAny(Collection<String> strs, Collection<String> searchTerms) {
    for (String str : strs) {
      if (containsAny(str, searchTerms)) {
        return true;
      }
    }
    return false;
  }

  public static boolean containsAny(String str, Collection<String> searchTerms) {
    for (String searchTerm : searchTerms) {
      if (str.contains(searchTerm)) {
        return true;
      }
    }
    return false;
  }
}