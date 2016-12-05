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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * http://adventofcode.com/2016/day/5
 */
public class Puzzle5_Passwords implements Puzzle {
  @Override
  public Solution solve(String input) {
    final char[] hex = "0123456789ABCDEF".toCharArray();
    try {
      String passwordA = "";
      StringBuilder passwordB = new StringBuilder("        ");
      MessageDigest hash = MessageDigest.getInstance("MD5");
      for (int idx = 0; passwordA.length() < 8 || passwordB.toString().contains(" "); ++idx) {
        byte[] hashed = hash.digest((input + String.valueOf(idx)).getBytes());
        int fiveSixInt = hashed[2] < 0 ? 256 + hashed[2] : hashed[2];
        int sevenInt = hashed[3] < 0 ? 256 + hashed[3] : hashed[3];
        if (hashed[0] == 0 && hashed[1] == 0 && fiveSixInt < 16) {
          passwordA += passwordA.length() < 8 ? hex[fiveSixInt] : "";
          try {
            if (fiveSixInt < 8 && passwordB.charAt(fiveSixInt) == ' ') {
              passwordB.setCharAt(fiveSixInt, (hex[sevenInt / 16]));
            }
          } catch (NumberFormatException ignore) {
          }
        }
      }
      return new Solution(passwordA, passwordB.toString());
    } catch (NoSuchAlgorithmException ex) {
      return null;
    }
  }
}
