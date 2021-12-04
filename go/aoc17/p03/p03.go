package p03

import (
	"math"

	c "s13g.com/euler/common"
)

func Solve(input string) (string, string) {
	inputNum := c.ToIntOrPanic(input)
	return c.ToString(solveA(inputNum)), c.ToString(solveB(inputNum))
}

func solveA(input int) int {
	// Determine the "ring" on which our input is. That's the first distance
	// component. Then we multiple "ring" by 2 to get the dimension.
	dim := int(math.Ceil((math.Sqrt(float64(input))-1)/2)) * 2

	// Find the center values (cV) for that ring. The gap between our number to
	// the closest one (the one on the same side) will determine the other
	// offset component for our distance.
	// '(dim + 1) * (dim + 1)' is the bottom right corner number in the ring
	// cV indexes: 0: the south center, 1: west, 2: north, 3: west, going
	//             counter clockwise on the spiral.
	southCenter := ((dim + 1) * (dim + 1)) - (dim / 2)
	cV := []int{southCenter, southCenter - dim, southCenter - dim*2, southCenter - dim*3}
	minDistanceFromCenter := c.MinA(c.Map(cV, func(v int) int { return c.Abs(v - input) }))
	return dim/2 + minDistanceFromCenter // dim/2 = ring
}

// Brute force. Walk the spiral and add up cells.
func solveB(input int) int {
	grid, adjSum := c.NewGrid(), 0
	grid.Set(0, 0, 1)
	grid.Set(1, 0, 1)
	for ring, x, y := 1, 1, 0; adjSum < input; ring, x, y = ring+1, ring+1, ring {
		// Go Up
		for ; y > -(ring) && adjSum < input; y, adjSum = y-1, setAdjSum(x, y-1, grid) {
		}
		// Go Left
		for ; x > -(ring) && adjSum < input; x, adjSum = x-1, setAdjSum(x-1, y, grid) {
		}
		// Go Down
		for ; y < ring && adjSum < input; y, adjSum = y+1, setAdjSum(x, y+1, grid) {
		}
		// Go Right
		for ; x < ring+1 && adjSum < input; x, adjSum = x+1, setAdjSum(x+1, y, grid) {
		}
	}
	return adjSum
}

func setAdjSum(x, y int, grid *c.Grid) (sum int) {
	for yy := y - 1; yy <= y+1; yy++ {
		for xx := x - 1; xx <= x+1; xx++ {
			sum += grid.Get(xx, yy)
		}
	}
	grid.Set(x, y, sum)
	return
}
