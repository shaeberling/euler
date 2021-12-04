package p22

import (
	"regexp"

	c "s13g.com/euler/common"
)

// --- Day 22: Mode Maze ---
// http://adventofcode.com/2018/day/22
func Solve(input string) (string, string) {
	m := regexp.MustCompile("^depth: (.*?)\r\ntarget: (.*?),(.*?)$").FindStringSubmatch(input)
	depth := c.ToIntOrPanic(m[1])
	targetX := c.ToIntOrPanic(m[2])
	targetY := c.ToIntOrPanic(m[3])
	return c.ToString(solveA(depth, targetX, targetY)), ""
}

func solveA(depth, targetX, targetY int) int {
	for y := 0; y <= targetY; y++ {
		for x := 0; x < targetX; x++ {

		}
	}
	return 42
}
