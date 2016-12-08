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
 * A cell containing potential values. If a cell only has one potential value,
 * then this value is fixed.
 */
class Cell {
    /** The size of the Sudoku grid. */
    private final int mGridSize;
    /** The potential values, from 0-GRID_SIZE-1. */
    private boolean[] mValues;
    /** The row this cell belongs to. */
    private CellGroup mRow;
    /** The columns this cell belongs to. */
    private CellGroup mCol;
    /** The square this cell belongs to. */
    private CellGroup mSquare;

    /** Initialize the cell with a fixed value. */
    public Cell(int fixedValue, int gridSize) {
        mGridSize = gridSize;
        setFixedValue(fixedValue);
    }

    /**
     * Based on the fixed values in its row, column and square, determines which
     * values are possible for this cell. If only one value is possible, this
     * cell will become a fixed-value cell.
     *
     * @return Whether something changed.
     */
    public boolean calcPossibleValues() throws IllegalConfigException {
        // Nothing to do if this cell already has a fixed value.
        if (getFixedValue() != -1) {
            return false;
        }

        boolean[] rowFixed = mRow.getFixedValues();
        boolean[] colFixed = mCol.getFixedValues();
        boolean[] squareFixed = mSquare.getFixedValues();

        boolean[] newValues = new boolean[mGridSize];
        for (int i = 0; i < mGridSize; ++i) {
            newValues[i] = !rowFixed[i] && !colFixed[i] && !squareFixed[i];
        }

        // If no possible value is found, this is not solvable.
        boolean atLeastOneSet = false;
        for (int i = 0; i < mGridSize; ++i) {
            if (newValues[i]) {
                atLeastOneSet = true;
                break;
            }
        }
        if (!atLeastOneSet) {
            throw new IllegalConfigException(
                    "No possible value: " + mRow.getName() + "/" + mCol.getName());
        }

        boolean changed = false;
        for (int i = 0; i < mGridSize; ++i) {
            if (newValues[i] != mValues[i]) {
                changed = true;
                break;
            }
        }
        mValues = newValues;
        return changed;
    }

    /** Get the values of this cell. */
    public boolean[] getValues() {
        return mValues;
    }

    /** Sets the value of this cell. */
    public void setValue(int i, boolean value) {
        mValues[i] = value;
    }

    /** Resets the values of this cell. */
    public void resetValues() {
        mValues = new boolean[mGridSize];
    }

    /**
     * @return If this cell has a fixed value, it will be returned. Otherwise,
     *         -1 is returned. This is the case if there are multiple values or
     *         none.
     */
    public int getFixedValue() {
        int fixedValue = -1;
        for (int i = 0; i < mValues.length; ++i) {
            if (mValues[i]) {
                if (fixedValue >= 0) {
                    // Multiple possible values, not fixed.
                    return -1;
                }
                fixedValue = i;
            }
        }
        return fixedValue;
    }

    /** Sets this cell to a fixed value. */
    public void setFixedValue(int fixedValue) {
        mValues = new boolean[mGridSize];
        if (fixedValue >= 0) {
            mValues[fixedValue] = true;
        }
    }

    /** Sets the groups (row, columns, square) of this cell. */
    public void setGroups(CellGroup row, CellGroup col, CellGroup square) {
        mRow = row;
        mCol = col;
        mSquare = square;

        mRow.addCell(this);
        mCol.addCell(this);
        mSquare.addCell(this);
    }

    @Override
    public String toString() {
        return String.valueOf(getFixedValue() + 1);
    }
}
