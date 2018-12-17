package p17

import (
	c "common"
	"fmt"
	"image"
	"image/color"
	"image/draw"
	"image/png"
	"math"
	"os"
)

// --- Day 17: Reservoir Research ---
// http://adventofcode.com/2018/day/17
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	w := createWorld(lines)

	sources := make([]xy, 0)
	// That's where the initial water source is.
	sources = append(sources, xy{x: 500, y: 0})
	for len(sources) > 0 {
		newSources := make([]xy, 0)
		for _, source := range sources {
			newFlows := w.startFlowAt(source.x, source.y)
			for _, f := range newFlows {
				if !contains(newSources, f) {
					newSources = append(newSources, f)
				}
			}
		}
		sources = newSources
	}
	// Uncomment this to store the state of the world as a PNG.
	// if err := w.saveAsPng("day17.png"); err != nil {
	// 	fmt.Printf("Cannot save as PNG: %s\n", err)
	// }
	return c.ToString(w.numWater()), c.ToString(w.numSettledWater())
}

type xy struct {
	x, y int
}

func contains(haystack []xy, needle xy) bool {
	for _, p := range haystack {
		if p.x == needle.x && p.y == needle.y {
			return true
		}
	}
	return false
}

func createWorld(lines []string) world {
	minX, maxX, minY, maxY := math.MaxInt32, math.MinInt32, math.MaxInt32, math.MinInt32
	grid := make(map[xy]bool, 0)
	for _, l := range lines {
		parts := c.SplitByCommaTrim(l)
		rangeParts := c.SplitTrim(parts[1][2:], '.')
		if parts[0][0] == 'x' {
			// Vertical line
			x := c.ToIntOrPanic(parts[0][2:])
			startY := c.ToIntOrPanic(rangeParts[0])
			endY := c.ToIntOrPanic(rangeParts[len(rangeParts)-1])
			minX, maxX = c.Min(minX, x), c.Max(maxX, x)
			minY, maxY = c.Min(minY, startY), c.Max(maxY, endY)
			for y := startY; y <= endY; y++ {
				grid[xy{x: x, y: y}] = true
			}
		} else if parts[0][0] == 'y' {
			// Horizontal line
			y := c.ToIntOrPanic(parts[0][2:])
			startX := c.ToIntOrPanic(rangeParts[0])
			endX := c.ToIntOrPanic(rangeParts[len(rangeParts)-1])
			minY, maxY = c.Min(minY, y), c.Max(maxY, y)
			minX, maxX = c.Min(minX, startX), c.Max(maxX, endX)
			for x := startX; x <= endX; x++ {
				grid[xy{x: x, y: y}] = true
			}
		} else {
			panic("Unexpected input")
		}
	}
	// FYI we pad on X left and right by 1 to allow water to flow down if needed.
	w := world{
		minX:    minX - 1,
		minY:    minY,
		maxX:    maxX + 1,
		maxY:    maxY,
		solids:  grid,
		settled: make(map[xy]bool),
		flown:   make(map[xy]bool),
		source:  xy{500, 0},
	}
	return w
}

// Note: There is not hole-in-the ground scenario here to look out for.
type world struct {
	minX, maxX, minY, maxY int
	solids                 map[xy]bool
	settled                map[xy]bool
	flown                  map[xy]bool
	source                 xy
}

func (w *world) solid(x, y int) bool {
	solid := w.solids[xy{x: x, y: y}]
	settled := w.settled[xy{x: x, y: y}]
	// fmt.Printf("checkSolid(%d,%d)->%t/%t\n", x-w.minX, y-w.minY, solid, settled)
	return solid || settled
}

var pngCounter int

func (w *world) startFlowAt(x, y int) []xy {
	xStart, yStart := x, y
	if w.solid(x, y) {
		// Go back to the source and trace a new flow.
		return []xy{{x: 500, y: 0}}
	}
	// First we flow down until we hit a solid tile
	for ; !w.solid(x, y+1); y++ {
		// End flow if we flow out of the world.
		if y > w.maxY {
			return []xy{}
		}
		w.flown[xy{x: x, y: y}] = true
	}

	newStarts, visitedSideWays := make([]xy, 0), make([]xy, 0)
	for _, dir := range []int{-1, 1} {
		newFlow, pos, visited := w.goSideWays(x, y, dir)
		visitedSideWays = append(visitedSideWays, visited...)
		if newFlow {
			newStarts = append(newStarts, pos)
		}
	}

	// If there are no new flows, we need to settle the water we just visited.
	if len(newStarts) == 0 {
		for _, p := range visitedSideWays {
			w.settled[p] = true
		}
		newStarts = append(newStarts, xy{x: xStart, y: yStart})
	}
	return newStarts
}

func (w *world) goSideWays(xStart, y int, dir int) (bool, xy, []xy) {
	visited := make([]xy, 0)
	for x := xStart; x >= w.minX && x <= w.maxX; x += dir {
		pos := xy{x: x, y: y}
		visited = append(visited, pos)
		w.flown[pos] = true
		// We hit a wall
		if w.solid(x+dir, y) {
			return false, xy{}, visited
		}
		// We found an edge, need to start a new flow here
		if !w.solid(x, y+1) {
			return true, xy{x: x, y: y}, visited
		}
	}
	panic("Unexpected: We ran out of the map going sideways.")
}

func (w *world) width() int  { return w.maxX - w.minX + 1 }
func (w *world) height() int { return w.maxY - w.minY + 1 }

func (w *world) numWater() int        { return w.countWithinBounds(w.flown) }
func (w *world) numSettledWater() int { return w.countWithinBounds(w.settled) }

func (w *world) countWithinBounds(data map[xy]bool) (sum int) {
	for k, v := range data {
		if v && k.x >= w.minX && k.x <= w.maxX && k.y >= w.minY && k.y <= w.maxY {
			sum++
		}
	}
	return sum
}

func (w world) print() {
	fmt.Printf("width: %d, height: %d\n", w.width(), w.height())
	for y := 0; y <= w.maxY; y++ {
		for x := w.minX; x <= w.maxX; x++ {
			if w.source.x == x && w.source.y == y {
				fmt.Print("+")
			} else if w.solids[xy{x: x, y: y}] {
				fmt.Print("#")
			} else if w.settled[xy{x: x, y: y}] {
				fmt.Print("~")
			} else if w.flown[xy{x: x, y: y}] {
				fmt.Print("|")
			} else {
				fmt.Print(".")
			}
		}
		fmt.Print("\n")
	}
}

func (w world) saveAsPng(filename string) error {
	img := image.NewRGBA(image.Rect(0, 0, w.width(), w.height()))
	draw.Draw(img, img.Bounds(), &image.Uniform{color.White}, image.ZP, draw.Src)
	blue := color.RGBA{50, 50, 255, 255}
	darkBlue := color.RGBA{0, 0, 200, 255}
	// Set color for each pixel.
	for y := w.minY; y < w.maxY; y++ {
		for x := w.minX; x <= w.maxX; x++ {
			if w.solids[xy{x: x, y: y}] {
				xc, yc := x-w.minX, y-w.minY
				img.Set(xc, yc, color.Black)
			} else if w.settled[xy{x: x, y: y}] {
				xc, yc := x-w.minX, y-w.minY
				img.Set(xc, yc, darkBlue)
			} else if w.flown[xy{x: x, y: y}] {
				xc, yc := x-w.minX, y-w.minY
				img.Set(xc, yc, blue)
			}
		}
	}

	// Encode as PNG.
	f, err := os.Create(filename)
	if err != nil {
		return fmt.Errorf("cannot create file %s", err)
	}
	if err := png.Encode(f, img); err != nil {
		return fmt.Errorf("cannot encode PNG %s", err)
	}
	return nil
}
