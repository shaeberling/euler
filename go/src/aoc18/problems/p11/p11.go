package p11

import (
	c "common"
	"fmt"
	"math"
)

// --- Day 11: Chronal Charge ---
// http://adventofcode.com/2018/day/11
func Solve(input string) (string, string) {
	serial := c.ToIntOrPanic(input)
	grid := make([]int, dim*dim)
	for y := 0; y < dim; y++ {
		for x := 0; x < dim; x++ {
			grid[y*dim+x] = power(x, y, serial)
		}
	}
	return solveA(grid, serial), solveB(grid, serial)
}

const dim = 300

func solveA(grid []int, serial int) string {
	largestV, vX, vY := math.MinInt32, 0, 0
	for y := 0; y < dim-2; y++ {
		for x := 0; x < dim-2; x++ {
			v := getXxX(grid, x, y, 3)
			if v > largestV {
				largestV = v
				vX, vY = x, y
			}
		}
	}
	return fmt.Sprintf("%d,%d", vX+1, vY+1)
}

func solveB(grid []int, serial int) string {
	largestV, vX, vY, vS := math.MinInt32, 0, 0, 0
	for s := 1; s <= dim; s++ {
		fmt.Printf("%d ", s)
		for y := 0; y < dim-(s-1); y++ {
			for x := 0; x < dim-(s-1); x++ {
				v := getXxX(grid, x, y, s)
				if v > largestV {
					largestV = v
					vX, vY, vS = x, y, s
				}
			}
		}
	}
	return fmt.Sprintf("%d,%d,%d", vX+1, vY+1, vS)
}

func getXxX(grid []int, x, y, size int) int {
	sum := 0
	for yy := y; yy < y+size; yy++ {
		for xx := x; xx < x+size; xx++ {
			sum += grid[yy*dim+xx]
		}
	}
	return sum
}

func power(x, y, serial int) int {
	x, y = x+1, y+1
	rackID := x + 10
	v := ((rackID * y) + serial) * rackID
	d := ((v%1000 - v%100) / 100) - 5
	return d
}
