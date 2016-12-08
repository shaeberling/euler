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

import java.util.ArrayList;
import java.util.List;

/**
 * Cups of water quiz
 */
public final class WaterCupsMain {

  public static void main(String[] args) {
    new WaterCupsSolver().determineEffects();

    List<Input> inputs = new ArrayList<>();
    inputs.add(new Input(2, 2, 2, 0.5));
    inputs.add(new Input(10, 4, 3, 0.375));
    inputs.add(new Input(20, 3, 7, 0.71875));
    inputs.add(new Input(42, 6, 6, 0.34375));

    WaterCupsSolver solver = new WaterCupsSolver();
    for (Input input : inputs) {
      double result = solver.pourWater(input.liters, input.col - 1, input.row - 1);
      if (result == input.expectedResult) {
        System.out.println("PASS");
      } else {
        System.out.println("FAIL: " + input + "  but was " + result);
      }
    }
  }
}
