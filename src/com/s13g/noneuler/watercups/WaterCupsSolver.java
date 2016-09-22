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

package com.s13g.noneuler.watercups;

/**
 * Array-based solver for the wate cups problem.
 */
final class WaterCupsSolver {
  private static final boolean PRINT_DEBUG = false;

  /**
   * Pour water into the glass pyramid and get fill status at given location
   *
   * @param amount the amount of water to pour into the top.
   * @param x      x-position of the cup of which to get the fill status (0-based).
   * @param y      y-position of the cup of which to get the fill status (0-based).
   * @return The fill amount of the given location after pouring the water.
   */
  double pourWater(double amount, int x, int y) {
    double[] cups = new double[getTotalWaterUpToIncludingRow(y + 1)];
    pourWaterInto(amount, 0, 0, cups);
    return cups[getTotalWaterUpToIncludingRow(y - 1) + x];
  }

  /**
   * Pours water into the given glass from the pyramid. Will have effect on it and all glasses below.
   *
   * @param amount the amount of water to pour.
   * @param x      x-position of the glass to pur into (0-based).
   * @param y      y-position of the glass to pur into (0-based).
   * @param cups   the cups pyramid to work on.
   */
  private void pourWaterInto(double amount, int x, int y, double[] cups) {
    int cupNumber = getTotalWaterUpToIncludingRow(y - 1) + x;
    if (amount <= 0 || cupNumber >= cups.length) {
      return;
    }

    double preValDebug = cups[cupNumber];
    cups[cupNumber] += amount;
    double overflow = Math.max(0, cups[cupNumber] - 1);
    cups[cupNumber] = Math.min(1, cups[cupNumber]);
    if (PRINT_DEBUG) {
      System.out.println("Filling " + amount + " into " + x + "/" + y + "->" + cupNumber +
          " with prevVal=" + preValDebug + " new=" + cups[cupNumber] + " overflow=" + overflow);
    }

    // Pour half into the left follower and half into the right follower one row below.
    pourWaterInto(overflow / 2f, x, y + 1, cups);
    pourWaterInto(overflow / 2f, x + 1, y + 1, cups);
  }

  /**
   * Gets the number of cups in total for a pyramid of the given height.
   */
  private static int getTotalWaterUpToIncludingRow(int y) {
    // Triangular number for determining water contents.
    return ((y + 1) * ((y + 1) + 1)) / 2;
  }
}
