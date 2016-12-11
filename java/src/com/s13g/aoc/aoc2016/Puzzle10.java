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

import java.util.HashMap;
import java.util.Map;

/**
 * http://adventofcode.com/2016/day/10
 */
public class Puzzle10 implements Puzzle {
  private Map<Integer, BotOrOut> bots;
  private Map<Integer, BotOrOut> outs;

  @Override
  public Solution solve(String input) {
    bots = new HashMap<>();
    outs = new HashMap<>();
    Map<Integer, Instruction> instructions = new HashMap<>();
    for (String line : input.split("\\r?\\n")) {
      if (line.startsWith("value ")) {
        String[] parts = line.split(" ");
        int value = Integer.parseInt(parts[1]);
        int botNum = Integer.parseInt(parts[5]);
        giveTo(botNum, value, true);
      } else {
        String[] parts = line.split(" ");
        int botNum = Integer.parseInt(parts[1]);
        boolean lowRecevierBot = parts[5].equals("bot");
        int lowReceiver = Integer.parseInt(parts[6]);

        boolean highRecevierBot = parts[10].equals("bot");
        int highReceiver = Integer.parseInt(parts[11]);
        instructions.put(botNum,
            new Instruction(lowReceiver, lowRecevierBot, highReceiver, highRecevierBot));
      }
    }

    int solutionA = 0;
    BotOrOut bot;
    while ((bot = getNext()) != null) {
      int lowValue = Math.min(bot.values[0], bot.values[1]);
      int highValue = Math.max(bot.values[0], bot.values[1]);

      if (lowValue == 17 && highValue == 61) {
        solutionA = bot.num;
      }
      Instruction instruction = instructions.get(bot.num);
      giveTo(instruction.lowReceiver, lowValue, instruction.lowRecevierBot);
      giveTo(instruction.highReceiver, highValue, instruction.highRecevierBot);
      bot.values[0] = -1;
      bot.values[1] = -1;
    }
    int solutionB = outs.get(0).values[0] * outs.get(1).values[0] * outs.get(2).values[0];
    return new Solution(solutionA, solutionB);
  }

  private BotOrOut getNext() {
    for (BotOrOut bot : bots.values()) {
      if (bot.hasBothValues()) {
        return bot;
      }
    }
    return null;
  }

  private void giveTo(int outNum, int value, boolean bot) {
    if (!(bot ? bots : outs).containsKey(outNum)) {
      (bot ? bots : outs).put(outNum, new BotOrOut(outNum));
    }
    BotOrOut botValues = (bot ? bots : outs).get(outNum);
    if (botValues.values[0] == -1) {
      botValues.values[0] = value;
    } else if (botValues.values[1] == -1) {
      botValues.values[1] = value;
    }
  }

  private static class Instruction {
    final int lowReceiver;
    final boolean lowRecevierBot;
    final int highReceiver;
    final boolean highRecevierBot;

    private Instruction(int lowReceiver, boolean lowRecevierBot,
                        int highReceiver, boolean highRecevierBot) {
      this.lowReceiver = lowReceiver;
      this.lowRecevierBot = lowRecevierBot;
      this.highReceiver = highReceiver;
      this.highRecevierBot = highRecevierBot;
    }
  }

  private static class BotOrOut {
    final int num;
    final int[] values = new int[2];

    private BotOrOut(int num) {
      this.num = num;
      values[0] = -1;
      values[1] = -1;
    }

    boolean hasBothValues() {
      return values[0] >= 0 && values[1] >= 0;
    }
  }
}
