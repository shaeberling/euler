package p13

import (
	"fmt"
	"sort"
	"strings"
)

// --- Day 13: Mine Cart Madness ---
// http://adventofcode.com/2018/day/13
func Solve(input string) (string, string) {
	// Parse the train track grid. Make a copy for partB.
	lines := strings.FieldsFunc(input, func(c rune) bool { return c == '\n' })
	width, height := len(lines[0]), len(lines)
	gridA := make([]rune, width*height)
	trainsA := trains(make([]train, 0))
	for y := 0; y < height; y++ {
		for x := 0; x < width; x++ {
			r := rune(lines[y][x])
			switch r {
			case 'v':
				trainsA = append(trainsA, train{x: x, y: y, dir: 2})
				r = '|'
			case '^':
				trainsA = append(trainsA, train{x: x, y: y, dir: 0})
				r = '|'
			case '>':
				trainsA = append(trainsA, train{x: x, y: y, dir: 1})
				r = '-'
			case '<':
				trainsA = append(trainsA, train{x: x, y: y, dir: 3})
				r = '-'
			}
			gridA[(y*width)+x] = r
		}
	}
	gridB := make([]rune, width*height)
	copy(gridB, gridA)
	trainsB := trains(make([]train, len(trainsA)))
	copy(trainsB, trainsA)
	return solveA(gridA, trainsA, width, height), solveB(gridA, trainsB, width, height)
}

// Print the grid for debugging, including trains (in order)
func printGrid(grid []rune, ts trains, w, h int) {
	for y := 0; y < h; y++ {
		for x := 0; x < w; x++ {
			c := grid[y*w+x]
			r, hasTrain := ts.hasTrainAt(x, y)
			if hasTrain {
				c = r
			}
			fmt.Print(string(c))
		}
		fmt.Print("\n")
	}
}

func solveA(grid []rune, ts trains, w, h int) string {
	for {
		sort.Sort(ts)
		// printGrid(grid, ts, w, h)
		for i := range ts {
			ts[i].iterate(grid, w)
			cX, cY, crash := containsCrash(ts)
			if crash {
				// Report first crash.
				return fmt.Sprintf("%d,%d", cX, cY)
			}
		}
	}
}

func solveB(grid []rune, ts trains, w, h int) string {
	for {
		sort.Sort(ts)
		// printGrid(grid, ts, w, h)
		for i := 0; i < len(ts); i++ {
			ts[i].iterate(grid, w)
			cX, cY, crash := containsCrash(ts)
			if crash {
				// Mark trains as dead, so they will be excluded
				// from future crash calculations.
				ts.markDead(cX, cY)
			}
		}
		// Last man standing, we have a winner!
		if ts.numAlive() == 1 {
			for _, t := range ts {
				if !t.dead {
					return fmt.Sprintf("%d,%d", t.x, t.y)
				}
			}
		}
	}
}

// Whether there is a crash right now, and where.
func containsCrash(ts trains) (int, int, bool) {
	locs := make(map[string]bool)
	for _, t := range ts {
		if t.dead {
			continue
		}
		pos := fmt.Sprintf("%d,%d", t.x, t.y)
		if locs[pos] {
			return t.x, t.y, true
		}
		locs[pos] = true
	}
	return 0, 0, false
}

// A train traveling through the system.
type train struct {
	dead bool
	x, y int
	// 0=up, 1=right, 2=down, 3=left
	dir int
	// 0=left, 1=straight, 2=right, ...
	nextTurn int
}

// Iterate a single train one tick.
func (t *train) iterate(grid []rune, width int) {
	tile := grid[t.y*width+t.x]
	// Handle intersection separately first, we migh change directions.
	if tile == '+' {
		turn := t.nextTurn
		// Left-Turn
		if turn == 0 {
			t.dir--
		}
		// Right-Turn
		if turn == 2 {
			t.dir++
		}

		if t.dir < 0 {
			t.dir += 4
		}
		if t.dir > 3 {
			t.dir -= 4
		}
		t.nextTurn = (t.nextTurn + 1) % 3
	} else {
		switch t.dir {
		// Left
		case 3:
			switch tile {
			case '/':
				t.dir = 2
			case '\\':
				t.dir = 0
			}
		// Right
		case 1:
			switch tile {
			case '\\':
				t.dir = 2
			case '/':
				t.dir = 0
			}
		// Down
		case 2:
			switch tile {
			case '\\':
				t.dir = 1
			case '/':
				t.dir = 3
			}
		// Up
		case 0:
			switch tile {
			case '\\':
				t.dir = 3
			case '/':
				t.dir = 1
			}
		}
	}

	// Drive the given direction.
	switch t.dir {
	case 0:
		t.y--
	case 1:
		t.x++
	case 2:
		t.y++
	case 3:
		t.x--
	}
}

// A collection of trains.
type trains []train

// For debugging: Whether there is a train and a latter representing its order.
func (ts trains) hasTrainAt(x, y int) (rune, bool) {
	for i, t := range ts {
		if t.x == x && t.y == y {
			return rune(int('A') + i), true
		}
	}
	return ' ', false
}

// Mark trains at given position as dead/crashed.
func (ts trains) markDead(x, y int) {
	for i, t := range ts {
		if t.x == x && t.y == y {
			ts[i].dead = true
		}
	}
}

// Counts how many trains are still alive.
func (ts *trains) numAlive() int {
	result := 0
	for _, t := range *ts {
		if !t.dead {
			result++
		}
	}
	return result
}

// Sort interface for trains, since an exact order needs to be followed.
func (ts trains) Len() int      { return len(ts) }
func (ts trains) Swap(i, j int) { ts[i], ts[j] = ts[j], ts[i] }
func (ts trains) Less(i, j int) bool {
	if ts[i].y == ts[j].y {
		return ts[i].x < ts[j].x
	} else {
		return ts[i].y < ts[j].y
	}
}
