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

import java.util.LinkedList;
import java.util.List;

/**
 * Various String utility functions.
 */
public class StringUtil {
  private static final char[] HEX =
      new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
  private static String[] BYTE_TO_HEX = new String[256];

  static {
    for (int i = 0; i < 256; ++i) {
      int high = i / 16;
      int low = i % 16;
      BYTE_TO_HEX[i] = "" + HEX[high] + HEX[low];
    }
  }

  public static String toHexString(byte[] bytes) {
    final StringBuilder builder = new StringBuilder();
    for (byte b : bytes) {
      builder.append(BYTE_TO_HEX[b < 0 ? b + 256 : b]);
    }
    return builder.toString();
  }

  public static List<Integer> getAllPosWithin(String str, String search) {
    List<Integer> positions = new LinkedList<>();
    for (int pos = str.indexOf(search); pos >= 0; pos = str.indexOf(search, pos + 1)) {
      positions.add(pos);
    }
    return positions;
  }

  public static String repeat(String str, int repeat) {
    String result = "";
    for (int i = 0; i < repeat; ++i) {
      result += str;
    }
    return result;
  }
}
