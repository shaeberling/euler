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
 * Input and expected outputs for the water cup problem.
 */
final class Input {
  final float liters;
  final int col;
  final int row;
  final double expectedResult;

  Input(float liters, int col, int row, double expectedResult) {
    this.liters = liters;
    this.col = col;
    this.row = row;
    this.expectedResult = expectedResult;
  }

  @Override
  public String toString() {
    return "liters=" + liters + " col=" + col + " row=" + row + " exp=" + expectedResult;
  }

}
