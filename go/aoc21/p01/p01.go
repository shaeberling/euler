package p01

import (
	c "s13g.com/euler/common"
)

// --- Day 1: Sonar Sweep ---
// http://adventofcode.com/2021/day/1
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	values := c.ParseIntArray(lines)

	largerA := 0
	for i := range values {
		if i == 0 {
			continue
		}
		if values[i] > values[i-1] {
			largerA++
		}
	}

	largerB := 0
	for i := range values {
		if i < 3 {
			continue
		}
		if values[i-2]+values[i-1]+values[i] > values[i-3]+values[i-2]+values[i-1] {
			largerB++
		}
	}

	return c.ToString(largerA), c.ToString(largerB)
}
