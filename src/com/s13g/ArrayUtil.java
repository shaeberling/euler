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

package com.s13g;

/**
 * Array utilities.
 */
public class ArrayUtil {
  public String[][] create(String content, int cols, int rows) {
    int c = 0;
    char[] chars = content.toCharArray();
    String[][] result = new String[rows][cols];
    for (int y = 0; y < rows; ++y) {
      for (int x = 0; x < cols && c < chars.length; ++x) {
        result[y][x] = String.valueOf(chars[c++]);
      }
    }
    return result;
  }
}
