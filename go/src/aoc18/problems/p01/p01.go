package p01

import (
	c "common"
)

// --- Day 1: Chronal Calibration ---
// http://adventofcode.com/2018/day/1
func Solve(input string) (string, string) {
	lines := c.MapStrI(c.SplitByNewline(input), c.ToIntOrPanic)
	return c.ToString(solveA(lines)), c.ToString(solveB(lines))
}

func solveA(lines []int) int {
	result := 0
	for _, v := range lines {
		result += v
	}
	return result
}

func solveB(lines []int) int {
	run := 0
	freqs := make(map[int]bool)
	for {
		for _, v := range lines {
			run += v
			if freqs[run] {
				return run
			}
			freqs[run] = true
		}
	}
}
