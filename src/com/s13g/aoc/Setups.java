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

import com.s13g.aoc.PuzzleRunner.PuzzleSetup;
import com.s13g.aoc.aoc2015.Puzzle1_Floors;
import com.s13g.aoc.aoc2015.Puzzle2_WrappingPaper;
import com.s13g.aoc.aoc2015.Puzzle3_Houses;
import com.s13g.aoc.aoc2016.Puzzle1_Grid;
import com.s13g.aoc.aoc2016.Puzzle2_Keypad;
import com.s13g.aoc.aoc2016.Puzzle3_Triangles;
import com.s13g.aoc.aoc2016.Puzzle4_Doors;
import com.s13g.aoc.aoc2016.Puzzle5_Passwords;

/**
 * All active puzzles go here
 */
class Setups {
  static PuzzleSetup[] getPuzzles() {
    final String AOC15 = "data/aoc/2015/";
    final String AOC16 = "data/aoc/2016/";
    return new PuzzleSetup[]{
        new PuzzleSetup(AOC15 + "day1.txt", new Puzzle1_Floors(), 138, 1771),
        new PuzzleSetup(AOC15 + "day2.txt", new Puzzle2_WrappingPaper(), 1586300, 3737498),
        new PuzzleSetup(AOC15 + "day3.txt", new Puzzle3_Houses(), 2565, 2639),

        // 2016
        new PuzzleSetup(AOC16 + "day1.txt", new Puzzle1_Grid(), 161, 110),
        new PuzzleSetup(AOC16 + "day2.txt", new Puzzle2_Keypad(), "69642", "8CB23"),
        new PuzzleSetup(AOC16 + "day3.txt", new Puzzle3_Triangles(), 1032, 1838),
        new PuzzleSetup(AOC16 + "day4.txt", new Puzzle4_Doors(), 137896, 501),
        new PuzzleSetup(AOC16 + "day5.txt", new Puzzle5_Passwords(), "801B56A7", "424A0197"),
    };
  }
}
