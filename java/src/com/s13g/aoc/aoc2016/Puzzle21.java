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

import com.s13g.ArrayUtil;
import com.s13g.aoc.Puzzle;

import static java.lang.Integer.parseInt;

/**
 * http://adventofcode.com/2016/day/21
 */
public class Puzzle21 implements Puzzle {
  @Override
  public Solution solve(String commands) {
    return new Solution(solve("abcdefgh", commands, false), solve("fbgdceah", commands, true));
  }

  public String solve(String input, String commands, boolean reverse) {
    Pass pass = new Pass(input, reverse);
    String[] commandArray = commands.split("\\r?\\n");
    if (reverse) {
      commandArray = ArrayUtil.reverse(commandArray);
    }
    for (String entry : commandArray) {
      String[] split = entry.split(" ");
      if (split[0].equals("swap") && split[1].equals("position")) {
        pass.swapPos(parseInt(split[2]), parseInt(split[5]));
      } else if (split[0].equals("swap") && split[1].equals("letter")) {
        pass.swapLetters(split[2].charAt(0), split[5].charAt(0));
      } else if (split[0].equals("rotate")) {
        if (split[1].equals("based")) {
          pass.rotateForLetter(split[6].charAt(0));
        } else {
          pass.rotate(split[1].equals("right"), parseInt(split[2]));
        }
      } else if (split[0].equals("reverse")) {
        pass.reverse(parseInt(split[2]), parseInt(split[4]));
      } else if (split[0].equals("move")) {
        pass.move(parseInt(split[2]), parseInt(split[5]));
      }
    }
    return pass.toString();
  }


  private static class Pass {
    final char[] mPass;
    final boolean mReverse;

    Pass(String passStr, boolean reverse) {
      mPass = passStr.toCharArray();
      mReverse = reverse;
    }

    void swapPos(int pos1, int pos2) {
      // OK to reverse
      char temp = mPass[pos1];
      mPass[pos1] = mPass[pos2];
      mPass[pos2] = temp;
    }

    void swapLetters(char a, char b) {
      // OK to reverse
      for (int i = 0; i < mPass.length; ++i) {
        if (mPass[i] == a) {
          mPass[i] = b;
        } else if (mPass[i] == b) {
          mPass[i] = a;
        }
      }
    }

    void rotate(boolean right, int num) {
      rotate(right, num, false);
    }

    void rotate(boolean right, int num, boolean forceNoReverse) {
      if (!forceNoReverse && mReverse) {
        right = !right;
      }
      if (right) {
        for (int i = 0; i < num; ++i) {
          char oldLast = mPass[mPass.length - 1];
          System.arraycopy(mPass, 0, mPass, 1, mPass.length - 1);
          mPass[0] = oldLast;
        }
      } else {
        for (int i = 0; i < num; ++i) {
          char oldFirst = mPass[0];
          System.arraycopy(mPass, 1, mPass, 0, mPass.length - 1);
          mPass[mPass.length - 1] = oldFirst;
        }
      }
    }

    void rotateForLetter(char c) {
      if (mReverse) {
        String result = toString();
        for (int i = 0; i <= mPass.length; ++i) {
          Pass p = new Pass(result, false);
          p.rotate(false, i);
          p.rotateForLetter(c);
          if (p.toString().equals(result)) {
            this.rotate(false, i, true);
            return;
          }
        }
        throw new RuntimeException("Cannot un-rotate");
      } else {
        int times = indexOf(c);
        rotate(true, (times >= 4 ? times + 2 : times + 1));
      }
    }

    void reverse(int from, int to) {
      // OK to reverse
      while (from < to) {
        char tmp = mPass[from];
        mPass[from] = mPass[to];
        mPass[to] = tmp;
        ++from;
        --to;
      }
    }

    void move(int from, int to) {
      if (mReverse) {
        int tmp = from;
        from = to;
        to = tmp;
      }
      if (from < to) {
        for (int i = from; i < to; ++i) {
          char tmp = mPass[i];
          mPass[i] = mPass[i + 1];
          mPass[i + 1] = tmp;
        }
      } else if (from > to) {
        for (int i = from; i > to; --i) {
          char tmp = mPass[i];
          mPass[i] = mPass[i - 1];
          mPass[i - 1] = tmp;
        }
      }
    }

    int indexOf(char c) {
      for (int i = 0; i < mPass.length; ++i) {
        if (mPass[i] == c) {
          return i;
        }
      }
      throw new RuntimeException("Cannot find character");
    }

    @Override
    public String toString() {
      return new String(mPass);
    }
  }
}
