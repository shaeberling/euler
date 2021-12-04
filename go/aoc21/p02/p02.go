package p02

import (
	c "s13g.com/euler/common"
)

// --- Day 2: Dive! ---
// http://adventofcode.com/2021/day/2
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solveA(lines)), c.ToString(solveB(lines))
}

func solveA(lines []string) int {
	horPos := 0
	depth := 0

	for _, line := range lines {
		comp := c.SplitTrim(line, ' ')
		instr := comp[0]
		value := c.ToIntOrPanic(comp[1])

		switch instr {
		case "forward":
			horPos += value
		case "down":
			depth += value
		case "up":
			depth -= value
		}
	}
	return horPos * depth
}

func solveB(lines []string) int {
	horPos := 0
	depth := 0
	aim := 0

	for _, line := range lines {
		comp := c.SplitTrim(line, ' ')
		instr := comp[0]
		value := c.ToIntOrPanic(comp[1])

		switch instr {
		case "forward":
			horPos += value
			depth += value * aim
		case "down":
			aim += value
		case "up":
			aim -= value
		}
	}
	return horPos * depth
}
