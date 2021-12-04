package p05

import (
	"math"

	c "s13g.com/euler/common"
)

// --- Day 5: A Maze of Twisty Trampolines, All Alike ---
// http://adventofcode.com/2017/day/5
func Solve(input string) (string, string) {
	inputArr := c.SplitByNewline(input)
	return c.ToString(solve(inputArr, math.MaxInt32)), c.ToString(solve(inputArr, 3))
}

func solve(input []string, lim int) (n int) {
	instructions := c.ParseIntArray(input)
	for i := 0; i >= 0 && i < len(instructions); n++ {
		prevI, prevValue := i, instructions[i]
		i += instructions[i]
		instructions[prevI]++
		if prevValue >= lim {
			instructions[prevI] -= 2
		}
	}
	return
}
