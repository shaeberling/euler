package p22

import (
	c "common"
	"fmt"
)

type grid struct {
	data map[string]int
}

func newGrid() *grid {
	g := new(grid)
	g.data = make(map[string]int)
	return g
}

func key(x, y int) string {
	return fmt.Sprintf("%d x %d", x, y)
}

func (g *grid) increment(x, y int) {
	v := (g.get(x, y) + 1) % 4
	g.set(x, y, v)
}

func (g *grid) set(x, y int, v int) {
	g.data[key(x, y)] = v
}

func (g *grid) get(x, y int) int {
	return g.data[key(x, y)]
}

func (g *grid) numInfected() (num int) {
	for _, v := range g.data {
		if v > 0 {
			num++
		}
	}
	return
}

type direction struct {
	dx, dy int
}

func genDirections() []direction {
	result := make([]direction, 4)
	result[0] = direction{0, -1} // North
	result[1] = direction{1, 0}  // West
	result[2] = direction{0, 1}  // South
	result[3] = direction{-1, 0} // East
	return result
}

// --- Day 22: Sporifica Virus ---
// http://adventofcode.com/2017/day/2
func Solve(input string) (string, string) {
	return c.ToString(solveA(parseMap(input))), c.ToString(solveB(parseMap(input)))
}

func solveA(g *grid, middle int) int {
	x, y, dir := middle, middle, 0
	dirs := genDirections()
	num := 0
	for i := 0; i < 10000; i++ {
		// If infected, turn right.
		if g.get(x, y) > 0 {
			dir = (dir + 1) % 4
		} else {
			dir = (dir + 3) % 4
		}
		if g.get(x, y) == 0 {
			g.set(x, y, 1)
			num++
		} else {
			g.set(x, y, 0)
		}
		x += dirs[dir].dx
		y += dirs[dir].dy
	}
	return num
}

func solveB(g *grid, middle int) int {
	x, y, dir := middle, middle, 0
	dirs := genDirections()
	num := 0
	for i := 0; i < 10000000; i++ {
		// If clean, turn left
		if g.get(x, y) == 0 {
			dir = (dir + 3) % 4
		} else if g.get(x, y) == 2 {
			dir = (dir + 1) % 4
		} else if g.get(x, y) == 3 {
			dir = (dir + 2) % 4
		}
		g.increment(x, y)
		if g.get(x, y) == 2 {
			num++
		}
		x += dirs[dir].dx
		y += dirs[dir].dy
	}
	return num
}

func parseMap(input string) (*grid, int) {
	g := newGrid()

	lines := c.SplitByNewline(input)
	for y, line := range lines {
		for x, char := range line {
			if char == '#' {
				g.set(x, y, 2)
			}
		}
	}
	return g, len(lines[0]) / 2
}
