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

/**
 * A grid contains the cells of the Sudoku puzzle and the core of the solving
 * code.
 */
class Grid {
    /** The size of the Sudoku grid. */
    private final int mGridSize;
    /**
     * Cells contain concrete numbers or options, if multiple values are
     * possible.
     */
    private final Cell[][] mCells;
    /** Every cell belongs to one row. */
    private final CellGroup[] mRows;
    /** Every cell belongs to one column. */
    private final CellGroup[] mCols;
    /** Every cell belongs to one square. */
    private final CellGroup[] mSquares;

    /** Initialize from the Euler content. Numbers will range from 1-9. */
    public Grid(int[][] content, int gridSize) {
        mGridSize = gridSize;
        mCells = new Cell[gridSize][gridSize];
        mRows = new CellGroup[gridSize];
        mCols = new CellGroup[gridSize];
        mSquares = new CellGroup[gridSize];

        Cell[][] cells = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                // Internal representation is 0-8.
                cells[i][j] = new Cell(content[i][j] - 1, mGridSize);
            }
        }
        init(cells);
    }

    /** Initialize with a set of cells. */
    private Grid(Cell[][] cells, int gridSize) {
        mGridSize = gridSize;
        mCells = new Cell[gridSize][gridSize];
        mRows = new CellGroup[gridSize];
        mCols = new CellGroup[gridSize];
        mSquares = new CellGroup[gridSize];

        init(cells);
    }

    /**
     * Initialize with the given cells. Ensures rows, columns and squares are
     * created and initialized with the given cells.
     */
    private void init(Cell[][] cells) {
        for (int i = 0; i < mGridSize; ++i) {
            mRows[i] = new CellGroup("Row-" + i, mGridSize);
            mCols[i] = new CellGroup("Col-" + i, mGridSize);
            mSquares[i] = new CellGroup("Sqr-" + i, mGridSize);
        }

        for (int i = 0; i < mGridSize; ++i) {
            for (int j = 0; j < mGridSize; ++j) {
                Cell cell = cells[i][j];
                int sqrIndex = ((i / 3) * 3) + (j / 3);
                cell.setGroups(mRows[i], mCols[j], mSquares[sqrIndex]);
                mCells[i][j] = cell;
            }
        }
    }

    /**
     * Solve this grid.
     *
     * @param stage a name for this stage, used for debugging.
     * @return The result of the solve. The result might or might not be valid
     *         and solved.
     */
    public Grid solve(String stage) {
        try {
            // First step is to reduce the values as much as possible.
            reduceValues();
        } catch (IllegalConfigException e) {
            // Unsolvable config.
            return this;
        }
        // If this first step did not solve the puzzle we will need to
        // reduce the options and backtrack.
        if (!isFinishedAndValid()) {
            for (int i = 0; i < mGridSize; ++i) {
                for (int j = 0; j < mGridSize; ++j) {
                    Cell cell = mCells[i][j];
                    if (cell.getFixedValue() == -1) {
                        boolean[] values = cell.getValues();
                        Grid cloned = this.cloneFixedOnly();
                        for (int k = 0; k < mGridSize; ++k) {
                            if (values[k]) {
                                cloned.mCells[i][j].resetValues();
                                cloned.mCells[i][j].setValue(k, true);
                                Grid grid = cloned
                                        .solve(stage + "(" + i + "/" + j + "=" + k + ")");
                                if (grid.isFinishedAndValid()) {
                                    return grid;
                                }
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    /**
     * For all cells that don't have one fixed value, calculate all possible
     * values (eliminate impossible values as per Sudoku rule).
     * <p>
     * Next check which options are ex ....
     * <p>
     * Repeat this process for the whole board until nothing can be reduced
     * further.
     */
    private void reduceValues() throws IllegalConfigException {
        boolean changed;
        do {
            changed = false;
            for (int i = 0; i < mGridSize; ++i) {
                for (int j = 0; j < mGridSize; ++j) {
                    if (mCells[i][j].calcPossibleValues()) {
                        changed = true;
                    }
                }
            }

            for (int i = 0; i < mGridSize; ++i) {
                if (mRows[i].eliminateExclusiveOptions()) {
                    changed = true;
                }
                if (mCols[i].eliminateExclusiveOptions()) {
                    changed = true;
                }
                if (mSquares[i].eliminateExclusiveOptions()) {
                    changed = true;
                }
            }
        } while (changed);
    }

    /** Check whether the grid is done and a valid config. */
    public boolean isFinishedAndValid() {
        for (int i = 0; i < mGridSize; ++i) {
            if (!mRows[i].isFinishedAndValid()) {
                return false;
            }
            if (!mCols[i].isFinishedAndValid()) {
                return false;
            }
            if (!mSquares[i].isFinishedAndValid()) {
                return false;
            }
        }
        return true;
    }

    /** Clones the grid with only the fixed values. */
    public Grid cloneFixedOnly() {
        Cell[][] cells = new Cell[mGridSize][mGridSize];
        for (int i = 0; i < mGridSize; ++i) {
            for (int j = 0; j < mGridSize; ++j) {
                cells[i][j] = new Cell(mCells[i][j].getFixedValue(), mGridSize);
            }
        }
        return new Grid(cells, mGridSize);
    }

    /** Needed for solving the Project Euler task. */
    public int getTopLeftThreeNum() {
        int result = (mCells[0][0].getFixedValue() + 1) * 100;
        result += (mCells[0][1].getFixedValue() + 1) * 10;
        result += mCells[0][2].getFixedValue() + 1;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mGridSize; ++i) {
            for (int j = 0; j < mGridSize; ++j) {
                builder.append(mCells[i][j]);
                if (j % 3 == 2) {
                    builder.append(' ');
                }
            }
            builder.append('\n');
            if (i % 3 == 2) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
