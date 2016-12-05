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
    try {
      String passwordA = "";
      StringBuilder passwordB = new StringBuilder("        ");
      MessageDigest hash = MessageDigest.getInstance("MD5");
      for (int idx = 0; passwordA.length() < 8 || passwordB.toString().contains(" "); ++idx) {
        byte[] hashed = hash.digest((input + String.valueOf(idx)).getBytes());
        char[] fiveSix = String.format("%02X", hashed[2]).toCharArray();
        char seven = String.format("%02X", hashed[3]).toCharArray()[0];
        if (hashed[0] == 0 && hashed[1] == 0 && fiveSix[0] == '0') {
          passwordA += passwordA.length() < 8 ? fiveSix[1] : "";
          try {
            int pos = Integer.parseInt(String.valueOf(fiveSix[1]));
            if (pos < 8 && passwordB.charAt(pos) == ' ') {
              passwordB.setCharAt(pos, seven);
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
