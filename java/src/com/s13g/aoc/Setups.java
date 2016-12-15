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
import com.s13g.aoc.aoc2016.Puzzle10;
import com.s13g.aoc.aoc2016.Puzzle11;
import com.s13g.aoc.aoc2016.Puzzle12_Assembly;
import com.s13g.aoc.aoc2016.Puzzle13;
import com.s13g.aoc.aoc2016.Puzzle14_OneTimePad;
import com.s13g.aoc.aoc2016.Puzzle1_Grid;
import com.s13g.aoc.aoc2016.Puzzle2_Keypad;
import com.s13g.aoc.aoc2016.Puzzle3_Triangles;
import com.s13g.aoc.aoc2016.Puzzle4_Doors;
import com.s13g.aoc.aoc2016.Puzzle5_Passwords;
import com.s13g.aoc.aoc2016.Puzzle6_Passwords;
import com.s13g.aoc.aoc2016.Puzzle7;
import com.s13g.aoc.aoc2016.Puzzle8;
import com.s13g.aoc.aoc2016.Puzzle9;

/**
 * All active puzzles go here
 */
class Setups {
  static PuzzleSetup[] getPuzzles() {
    final String AOC15 = "../data/aoc/2015/";
    final String AOC16 = "../data/aoc/2016/";
    return new PuzzleSetup[]{
        // 2015
        new PuzzleSetup(AOC15 + "day1.txt", new Puzzle1_Floors(), 138, 1771),
        new PuzzleSetup(AOC15 + "day2.txt", new Puzzle2_WrappingPaper(), 1586300, 3737498),
        new PuzzleSetup(AOC15 + "day3.txt", new Puzzle3_Houses(), 2565, 2639),

        // 2016
        new PuzzleSetup(AOC16 + "day1.txt", new Puzzle1_Grid(), 161, 110),
        new PuzzleSetup(AOC16 + "day2.txt", new Puzzle2_Keypad(), "69642", "8CB23"),
        new PuzzleSetup(AOC16 + "day3.txt", new Puzzle3_Triangles(), 1032, 1838),
        new PuzzleSetup(AOC16 + "day4.txt", new Puzzle4_Doors(), 137896, 501),
        // new PuzzleSetup(AOC16 + "day5.txt", new Puzzle5_Passwords(), "801B56A7", "424A0197"),
        new PuzzleSetup(AOC16 + "day6.txt", new Puzzle6_Passwords(), "qrqlznrl", "kgzdfaon"),
        new PuzzleSetup(AOC16 + "day7.txt", new Puzzle7(), 110, 242),
        new PuzzleSetup(AOC16 + "day8.txt", new Puzzle8(), "115", getResult8b()),
        new PuzzleSetup(AOC16 + "day9.txt", new Puzzle9(), 98135L, 10964557606L),
        new PuzzleSetup(AOC16 + "day10.txt", new Puzzle10(), 73, 3965),
        new PuzzleSetup(AOC16 + "day11.txt", new Puzzle11(), null, null),
        // new PuzzleSetup(AOC16 + "day12.txt", new Puzzle12_Assembly(), 318117, 9227771),
        new PuzzleSetup(AOC16 + "day13.txt", new Puzzle13(), 96, 141),
        new PuzzleSetup(AOC16 + "day14.txt", new Puzzle14_OneTimePad(), 15168, 20864),
    };
  }

  private static String getResult8b() {
    return "" +
        "XXXX XXXX XXXX X   XX  X XXXX XXX  XXXX  XXX   XX \n" +
        "X    X    X    X   XX X  X    X  X X      X     X \n" +
        "XXX  XXX  XXX   X X XX   XXX  X  X XXX    X     X \n" +
        "X    X    X      X  X X  X    XXX  X      X     X \n" +
        "X    X    X      X  X X  X    X X  X      X  X  X \n" +
        "XXXX X    XXXX   X  X  X X    X  X X     XXX  XX  \n";
  }

}

