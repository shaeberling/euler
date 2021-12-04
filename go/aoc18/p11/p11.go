package p11

import (
	"fmt"
	"math"

	c "s13g.com/euler/common"
)

const dim = 300

// --- Day 11: Chronal Charge ---
// http://adventofcode.com/2018/day/11
func Solve(input string) (string, string) {
	serial := c.ToIntOrPanic(input)
	grid := createGrid(serial)
	return solveA(grid, serial), solveB(grid, serial)
}

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
	solver := cachedSolver{
		grid:  grid,
		cache: make(map[cacheKey]int, len(grid)*3),
	}

	largestV, vX, vY, vS := math.MinInt32, 0, 0, 0
	for s := 1; s <= dim; s++ {
		for y := 0; y < dim-(s-1); y++ {
			for x := 0; x < dim-(s-1); x++ {
				v, _ := solver.get(x, y, s)
				if v > largestV {
					largestV = v
					vX, vY, vS = x, y, s
				}
			}
		}
	}
	return fmt.Sprintf("%d,%d,%d", vX+1, vY+1, vS)
}

func createGrid(serial int) []int {
	grid := make([]int, dim*dim)
	for y := 0; y < dim; y++ {
		for x := 0; x < dim; x++ {
			xx, yy := x+1, y+1
			rackID := xx + 10
			v := ((rackID * yy) + serial) * rackID
			grid[y*dim+x] = ((v%1000 - v%100) / 100) - 5
		}
	}
	return grid
}

// Gets the sum for the grid, non-cached.
func getXxX(grid []int, x, y, size int) int {
	sum := 0
	for yy := y; yy < y+size; yy++ {
		for xx := x; xx < x+size; xx++ {
			sum += grid[yy*dim+xx]
		}
	}
	return sum
}

type cachedSolver struct {
	grid  []int
	cache map[cacheKey]int
}

type cacheKey struct {
	x, y, size int
}

func (s *cachedSolver) get(x, y, size int) (int, bool) {
	// Size 1 does not need to be cached, we just use the grid itself.
	if size == 1 {
		return s.grid[y*dim+x], true
	}
	key := cacheKey{x: x, y: y, size: size}
	if v, ok := s.cache[key]; ok {
		return v, true
	}

	// Editor's note: Tried to speed things up more by adding up all size%2 sums
	// by their contained 4 sums, but that was pretty slow.

	sum, _ := s.get(x, y, size-1)
	// Add bottom line
	for xx := x; xx < x+size; xx++ {
		yy := y + size - 1
		sum += s.grid[yy*dim+xx]
	}
	// Add right column, minus the last since the last row already contained it.
	for yy := y; yy < y+size-1; yy++ {
		xx := x + size - 1
		sum += s.grid[yy*dim+xx]
	}
	s.cache[key] = sum
	return sum, false
}
