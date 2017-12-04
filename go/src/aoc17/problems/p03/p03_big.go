package p03

import (
	c "common"
	"math/big"
)

func SolveBig(input string) (string, string) {
	inputNum := c.ToIntOrPanic(input)
	return c.ToString(solveA(inputNum)), c.ToStringBig(solveBBig(inputNum))
}

// This is a just a fun test to try and get the 250.000th number in the spiral.
// A good way to test how fast big math is in Go. Turns out, VERY fast.
func solveBBig(input int) *big.Int {
	grid := c.NewBigGrid()
	adjSum := big.NewInt(0)
	grid.Set(0, 0, big.NewInt(1))
	grid.Set(1, 0, big.NewInt(1))

	// For my input, 65 was the index of the number that was the result.
	// Feel free to change this to something really big. 250,000 took about
	// 1.4 seconds on a Macbook Pro.
	const nThNum = 65
	counter := 0
	for ring, x, y := 1, 1, 0; counter < nThNum-2; ring, x, y = ring+1, ring+1, ring {
		// Go Up
		for ; y > -(ring) && counter < nThNum-2; y = y - 1 {
			setAdjSumBig(x, y-1, grid, adjSum)
			counter++
		}
		// Go Left
		for ; x > -(ring) && counter < nThNum-2; x = x - 1 {
			setAdjSumBig(x-1, y, grid, adjSum)
			counter++
		}
		// Go Down
		for ; y < ring && counter < nThNum-2; y = y + 1 {
			setAdjSumBig(x, y+1, grid, adjSum)
			counter++
		}
		// Go Right
		for ; x < ring+1 && counter < nThNum-2; x = x + 1 {
			setAdjSumBig(x+1, y, grid, adjSum)
			counter++
		}
	}
	return adjSum
}

func setAdjSumBig(x, y int, grid *c.BigGrid, sum *big.Int) {
	tmp := big.NewInt(0)
	sum.Set(big.NewInt(0))
	for yy := y - 1; yy <= y+1; yy++ {
		for xx := x - 1; xx <= x+1; xx++ {
			sum.Add(sum, grid.Get(xx, yy, tmp))
		}
	}
	grid.Set(x, y, sum)
}
