package p03

import (
	c "common"
)

// Fabric size.
const DIM = 1000

// --- Day 3: No Matter How You Slice It ---
// http://adventofcode.com/2018/day/3
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	// Parse every line as a claim.
	claims := make([]*claim, len(lines))
	for i, line := range lines {
		claims[i] = newClaim(line)
	}
	fabric := make([][]int, DIM*DIM, DIM*DIM)

	// Mark coverage
	for _, c := range claims {
		for x := c.left; x < c.left+c.width; x++ {
			for y := c.top; y < c.top+c.height; y++ {
				i := y*DIM + x
				fabric[i] = append(fabric[i], c.id)
			}
		}
	}
	return c.ToString(solveA(fabric, claims)), c.ToString(solveB(fabric, claims))
}

// Solution A: Count the number of square-inches that have more than one claim.
func solveA(fabric [][]int, claims []*claim) int {
	count := 0
	for _, tile := range fabric {
		if len(tile) > 1 {
			count++
		}
	}
	return count
}

// Solution B: Find claim with no overlap
func solveB(fabric [][]int, claims []*claim) int {
	for _, c := range claims {
		if isClaimAlone(fabric, c) {
			return c.id
		}
	}
	return -42
}

func isClaimAlone(fabric [][]int, c *claim) bool {
	for x := c.left; x < c.left+c.width; x++ {
		for y := c.top; y < c.top+c.height; y++ {
			if len(fabric[y*DIM+x]) > 1 {
				return false
			}
		}
	}
	return true
}

// newClaim creates a new claim.
func newClaim(line string) *claim {
	attrs := c.SplitByWhitespaceTrim(line)

	result := new(claim)
	result.id = c.ToIntOrPanic(attrs[0][1:])

	pos := c.SplitTrim(attrs[2], ',')
	result.left = c.ToIntOrPanic(pos[0])
	result.top = c.ToIntOrPanic(pos[1][:len(pos[1])-1])

	dim := c.SplitTrim(attrs[3], 'x')
	result.width = c.ToIntOrPanic(dim[0])
	result.height = c.ToIntOrPanic(dim[1])
	return result
}

type claim struct {
	id, left, top, width, height int
}
