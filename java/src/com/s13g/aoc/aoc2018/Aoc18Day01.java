package com.s13g.aoc.aoc2018;

import com.google.common.collect.Sets;
import com.s13g.ArrayUtil;
import com.s13g.aoc.Puzzle;

import java.util.Arrays;
import java.util.Set;

/**
 * --- Day 1: Chronal Calibration ---
 * https://adventofcode.com/2018/day/1
 **/
public class Aoc18Day01 implements Puzzle {
    @Override
    public Solution solve(String input) {
        int[] values = ArrayUtil.splitAsInt(input, "\\r?\\n");
        return new Solution(Arrays.stream(values).sum(), solveB(values));
    }

    private static int solveB(int[] values) {
        Set<Integer> previous = Sets.newHashSet();
        for (int i = 0, run = 0; ; run += values[i], i = (i + 1) % values.length) {
            if (previous.contains(run)) {
                return run;
            }
            previous.add(run);
        }
    }
}
