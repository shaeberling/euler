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

import java.util.ArrayList;
import java.util.List;

/** A cell group can be a row, column or square. */
class CellGroup {
    /** The size of the Sudoku grid. */
    private final int mGridSize;
    /** For debugging. */
    private final String mName;
    /** The cells contained within the group. */
    private final List<Cell> mCells = new ArrayList<>();

    public CellGroup(String name, int gridSize) {
        mName = name;
        mGridSize = gridSize;
    }

    /** Returns the name of this group (for debugging). */
    public String getName() {
        return mName;
    }

    /** Add a cell to this group. */
    public void addCell(Cell cell) {
        mCells.add(cell);
    }

    /**
     * @return All fixed values of the cells in this group.
     * @throws IllegalConfigException Thrown if the group has an illegal
     *             configuration, such as a duplicate value.
     */
    public boolean[] getFixedValues() throws IllegalConfigException {
        boolean[] fixedValues = new boolean[mGridSize];
        for (Cell cell : mCells) {
            int fixedValue = cell.getFixedValue();
            if (fixedValue >= 0) {
                if (fixedValues[fixedValue]) {
                    throw new IllegalConfigException(
                            mName + "] Fixed value twice: " + fixedValue);
                }
                fixedValues[fixedValue] = true;
            }
        }
        return fixedValues;
    }

    /**
     * Options that only apply to one cell in the group can be set as a fixed
     * value of that cell.
     *
     * @return whether something changed.
     */
    public boolean eliminateExclusiveOptions() {
        int[] optionsCount = new int[mGridSize];
        for (Cell cell : mCells) {
            boolean[] values = cell.getValues();
            for (int i = 0; i < mGridSize; ++i) {
                if (values[i]) {
                    optionsCount[i]++;
                }
            }
        }
        boolean changed = false;
        for (int i = 0; i < mGridSize; ++i) {
            if (optionsCount[i] == 1) {
                for (Cell cell : mCells) {
                    if (cell.getFixedValue() == -1) {
                        if (cell.getValues()[i]) {
                            for (int j = 0; j < mGridSize; ++j) {
                                if (j != i && cell.getValues()[j]) {
                                    changed = true;
                                    cell.setValue(j, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        return changed;
    }

    /**
     * @return Whether all cells in this group have fixed values and no
     *         duplicates exist.
     */
    public boolean isFinishedAndValid() {
        boolean[] checked = new boolean[mGridSize];
        for (Cell cell : mCells) {
            int cellValue = cell.getFixedValue();
            if (cellValue == -1 || checked[cellValue]) {
                return false;
            }
            checked[cellValue] = true;
        }
        for (int i = 0; i < mGridSize; ++i) {
            if (!checked[i]) {
                return false;
            }
        }
        return true;
    }
}
