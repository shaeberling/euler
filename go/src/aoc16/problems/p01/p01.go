package p01

import (
	"common"
	"fmt"
	"log"
	"strconv"
)

// --- Day 1: No Time for a Taxicab ---
// http://adventofcode.com/2016/day/1
func Solve(input string) (string, string) {
	visited := make(map[string]bool)
	visited[fmt.Sprintf("0,0")] = true
	var solutionB int

	var x, y int
	dirX, dirY := 0, -1
	for _, instr := range common.SplitByCommaTrim(input) {
		dir := instr[0]
		steps, err := strconv.ParseInt(instr[1:], 10, 64)
		if err != nil {
			log.Fatalf("Number of steps not a number: '%s' of '%s'.", instr[1:], instr)
			return "", ""
		}

		// Determine directional changes depending on input.
		if dirX == 0 {
			if dir == 'L' {
				dirX = dirY * -1
			} else {
				dirX = dirY
			}
			dirY = 0
		} else {
			if dir == 'L' {
				dirY = dirX
			} else {
				dirY = dirX * -1
			}
			dirX = 0
		}

		// We have to do it one step at a time to find solutionB
		for s := 0; s < int(steps); s += 1 {
			x += dirX
			y += dirY

			key := fmt.Sprintf("%d,%d", x, y)
			if _, ok := visited[key]; solutionB == 0 && ok {
				solutionB = x + y
			}
			visited[key] = true
		}
	}
	return fmt.Sprintf("%d", x+y), fmt.Sprintf("%d", solutionB)
}
