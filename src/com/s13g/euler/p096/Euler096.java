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

package com.s13g.euler.p096;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://projecteuler.net/problem=96">Euler Problem 96</a>
 */
public class Euler096 {
    /** Standard grid size, but this code should work on any size. */
    private static final int GRID_SIZE = 9;

    public static void main(String[] args) throws IOException {
        List<Grid> content = readFile("data/p096/p096_sudoku.txt");
        System.out.println("Grids: " + content.size());

        long startTimeMillis = System.currentTimeMillis();
        long eulerSolutionSum = 0;
        int numSolved = 0;
        for (int i = 0; i < content.size(); ++i) {
            long startTimeMillisPuzzle = System.nanoTime();
            Grid grid = content.get(i).solve("start");
            long endTimeMillisPuzzle = System.nanoTime();
            boolean solved = grid.isFinishedAndValid();
            System.out.println(i + " Solved: " + solved + " (" + (endTimeMillisPuzzle - startTimeMillisPuzzle) / 1000000f + "ms.)");
            if (solved) {
                numSolved++;
            }
            eulerSolutionSum += grid.getTopLeftThreeNum();
        }
        System.out.println("Solved: " + numSolved + "/" + content.size());
        System.out.println("Time: " + (System.currentTimeMillis() - startTimeMillis) + " ms.");
        System.out.println("Project Euler Sum: " + eulerSolutionSum);
    }

    /** Read the Euler problem file and return the list of Grids. */
    private static List<Grid> readFile(String fileName) throws IOException {
        List<Grid> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName)));
        String tempLine;
        List<int[]> gridBuilder = null;
        while ((tempLine = reader.readLine()) != null) {
            if (tempLine.startsWith("Grid")) {
                if (gridBuilder != null) {
                    result.add(new Grid(gridBuilder.toArray(new int[0][0]), GRID_SIZE));
                }
                gridBuilder = new ArrayList<>();
            } else {
                gridBuilder.add(parseRow(tempLine));
            }
        }
        result.add(new Grid(gridBuilder.toArray(new int[0][0]), GRID_SIZE));
        return result;
    }

    private static int[] parseRow(String line) {
        int[] row = new int[line.length()];
        for (int i = 0; i < line.length(); ++i) {
            row[i] = Integer.parseInt(String.valueOf(line.charAt(i)));
        }
        return row;
    }
}
