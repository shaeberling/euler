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

import com.s13g.aoc.HashUtil;
import com.s13g.aoc.Puzzle;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import static com.s13g.aoc.StringUtil.toHexString;

/**
 * http://adventofcode.com/2016/day/17
 */
public class Puzzle17 implements Puzzle {
  private static MessageDigest mMd5 = HashUtil.getMd5OrNull();

  @Override
  public Solution solve(String input) {
    State shortestPath = null;
    State longestPath = new State(0, 0, "");
    Set<State> nextStates = new HashSet<>();
    nextStates.add(new State(0, 0, ""));

    while (!nextStates.isEmpty()) {
      Set<State> newStates = new HashSet<>();
      for (State nextState : nextStates) {
        String passcode = toHexString(mMd5.digest((input + nextState.path).getBytes()));
        if (isOpen(passcode.charAt(0)) && nextState.y > 0) {
          newStates.add(new State(nextState.x, nextState.y - 1, nextState.path + "U"));
        }
        if (isOpen(passcode.charAt(1)) && nextState.y < 3) {
          newStates.add(new State(nextState.x, nextState.y + 1, nextState.path + "D"));
        }
        if (isOpen(passcode.charAt(2)) && nextState.x > 0) {
          newStates.add(new State(nextState.x - 1, nextState.y, nextState.path + "L"));
        }
        if (isOpen(passcode.charAt(3)) && nextState.x < 3) {
          newStates.add(new State(nextState.x + 1, nextState.y, nextState.path + "R"));
        }
      }

      for (State state : newStates) {
        if (shortestPath == null && state.x == 3 && state.y == 3) {
          shortestPath = state;
        }
      }

      Set<State> toRemove = new HashSet<>();
      for (State state : newStates) {
        if (state.x == 3 && state.y == 3) {
          toRemove.add(state);
          if (state.path.length() > longestPath.path.length()) {
            longestPath = state;
          }
        }
      }
      newStates.removeAll(toRemove);
      nextStates = newStates;
    }
    return new Solution(shortestPath.path, String.valueOf(longestPath.path.length()));
  }

  private static boolean isOpen(char c) {
    return c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f';
  }

  private static class State {
    final int x;
    final int y;
    final String path;

    private State(int x, int y, String path) {
      this.x = x;
      this.y = y;
      this.path = path;
    }
  }
}
