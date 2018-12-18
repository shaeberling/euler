package p18

import (
	c "common"
	"fmt"
	"hash/fnv"
	"math"
)

// --- Day 18: Settlers of The North Pole ---
// http://adventofcode.com/2018/day/18
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solveA(parseWorld(lines))), c.ToString(solveB(parseWorld(lines)))
}
func solveA(w world) int {
	for i := 0; i < 10; i++ {
		w.tick()
	}
	_, trees, lumberyards := w.countAll()
	return trees * lumberyards
}

func solveB(w world) int {
	target, round, states := 1000000000, 0, make(map[uint32]int)
	for round < target {
		w.tick()
		h := w.hash()
		// Loop detected!
		if r, ok := states[h]; ok {
			loopLength := round - r
			numLoopsSkipF := float64(target-round-1) / float64(loopLength)
			numLoopsSkip := int(numLoopsSkipF)
			numMoreRounds := int(math.Round((numLoopsSkipF - float64(numLoopsSkip)) * float64(loopLength)))
			// Just increment this number of rounds and then exit loop.
			for i := 0; i < numMoreRounds; i++ {
				w.tick()
			}
			break
		}
		states[h] = round
		round++
	}
	_, trees, lumberyards := w.countAll()
	return trees * lumberyards
}

type world struct {
	width, height int
	//0:ground, 1:tree, 2:lumberyard
	grid []byte
}

func (w world) hash() uint32 {
	h := fnv.New32a()
	h.Write(w.grid)
	return h.Sum32()
}

// Counts the different kinds of tiles adjacent to the given one.
func (w *world) countAdj(x, y int) (int, int, int) {
	var counts [3]int
	startX, startY := c.Max(0, x-1), c.Max(0, y-1)
	endX, endY := c.Min(w.width, x+2), c.Min(w.height, y+2)
	for yy := startY; yy < endY; yy++ {
		for xx := startX; xx < endX; xx++ {
			if yy == y && xx == x {
				continue
			}
			counts[w.grid[yy*w.width+xx]]++
		}
	}
	return counts[0], counts[1], counts[2]
}

// Count all types of tiles in the world.
func (w *world) countAll() (int, int, int) {
	var counts [3]int
	for y := 0; y < w.height; y++ {
		for x := 0; x < w.width; x++ {
			counts[w.grid[y*w.width+x]]++
		}
	}
	return counts[0], counts[1], counts[2]
}

func (w *world) tick() {
	newGrid := make([]byte, len(w.grid))
	for y := 0; y < w.height; y++ {
		for x := 0; x < w.width; x++ {
			pos := y*w.width + x
			_, trees, lumbers := w.countAdj(x, y)
			switch w.grid[pos] {
			case 0:
				newGrid[pos] = 0
				if trees >= 3 {
					newGrid[pos] = 1
				}
			case 1:
				newGrid[pos] = 1
				if lumbers >= 3 {
					newGrid[pos] = 2
				}
			case 2:
				newGrid[pos] = 0
				if lumbers >= 1 && trees >= 1 {
					newGrid[pos] = 2
				}
			}
		}
	}
	w.grid = newGrid
}

func (w *world) print() {
	for y := 0; y < w.height; y++ {
		for x := 0; x < w.width; x++ {
			c := "."
			pos := y*w.width + x
			if w.grid[pos] == 1 {
				c = "|"
			} else if w.grid[pos] == 2 {
				c = "#"
			}
			fmt.Print(c)
		}
		fmt.Print("\n")
	}
}

func parseWorld(lines []string) world {
	width, height := len(lines[0]), len(lines)
	grid := make([]byte, width*height)
	for y, l := range lines {
		for x, c := range l {
			var v byte
			if c == '|' {
				v = 1
			} else if c == '#' {
				v = 2
			}
			grid[y*width+x] = v
		}
	}
	return world{width: width, height: height, grid: grid}
}
