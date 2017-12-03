package p03

import (
	c "common"
	"math"
	"fmt"
)

func Solve(input string) (string, string) {
	inputNum := c.ToIntOrPanic(input)
	return c.ToString(solveA(inputNum)), c.ToString(solveB(368078))
}

func solveA(input int) int {
	// Determine the "ring" on which our input is. That's the first distance
	// component.
	ring := int(math.Ceil((math.Sqrt(float64(input)) - 1) / 2))
	dim := ring * 2

	// Find the center values (cV) for that ring. The gap between our number to
	// the closest one (the one on the same side) will determine the other
	// offset component for our distance.
	cV := make([]int, 4)

	// '(dim + 1) * (dim + 1)' is the bottom right corner number in the ring
	// cV indexes: 0: the south center, 1: west, 2: north, 3: west, going
	//             counter clockwise on the spiral.
	cV[0] = ((dim + 1) * (dim + 1)) - (dim / 2)
	cV[1] = cV[0] - dim
	cV[2] = cV[1] - dim
	cV[3] = cV[2] - dim
	minDistanceFromCenter := c.MinA(c.Map(cV, func(v int) int { return c.Abs(v - input) }))
	return ring + minDistanceFromCenter
}

// Brute force. Walk the spiral and add up cells.
func solveB(input int) int {
	mtrx := make(map[string]int)
	toKey := func(x, y int) string { return fmt.Sprintf("%d,%d", x, y) }

	mtrx[toKey(0, 0)] = 1
	mtrx[toKey(1, 0)] = 1
	adjSum := 0
	for ring := 1; adjSum < input; ring++ {
		x := ring
		y := ring - 2

		// Go Up
		for ; y >= -(ring) && adjSum < input; y -= 1 {
			adjSum = setAdjSum(x, y, mtrx, toKey)
		}
		y++
		// Go Left
		for x--; x >= -(ring) && adjSum < input; x -= 1 {
			adjSum = setAdjSum(x, y, mtrx, toKey)
		}
		x++
		// Go Down
		for y++; y <= ring && adjSum < input; y += 1 {
			adjSum = setAdjSum(x, y, mtrx, toKey)
		}
		y--
		// Go Right
		for x++; x <= ring+1 && adjSum < input; x += 1 {
			adjSum = setAdjSum(x, y, mtrx, toKey)
		}
	}
	return adjSum
}

func setAdjSum(x, y int, mtrx map[string]int, toKey func(int, int) string) (sum int) {
	for yy := y - 1; yy <= y+1; yy++ {
		for xx := x - 1; xx <= x+1; xx++ {
			sum += mtrx[toKey(xx, yy)]
		}
	}
	mtrx[toKey(x, y)] = sum
	return
}
