package p06

import (
	"math"

	c "s13g.com/euler/common"
)

// --- Day 6: Chronal Coordinates ---
// http://adventofcode.com/2018/day/6
func Solve(input string) (string, string) {
	// Parse lines into coordinates
	lines := c.SplitByNewline(input)
	coords := make([]*coordinate, len(lines))
	for i, l := range lines {
		coords[i] = newCoordinate(l)
	}

	// Determine the area with have to work with.
	minX, maxX, minY, maxY := math.MaxInt32, math.MinInt32, math.MaxInt32, math.MinInt32
	for _, k := range coords {
		minX, maxX = c.Min(minX, k.x), c.Max(maxX, k.x)
		minY, maxY = c.Min(minY, k.y), c.Max(maxY, k.y)
	}
	// Exclude coords that are at the edges, since they go to infinity.
	exclude := make([]bool, len(coords))
	// Marks how many tiles for the given coord are closest to it.
	closest := make([]int, len(coords))
	// For part B we count accumulated distances for each tile.
	partBCoords := make([]int, (maxX+1)*(maxY+1))
	for i := range partBCoords {
		partBCoords[i] = 0
	}

	for y := minY; y <= maxY; y++ {
		for x := minX; x <= maxX; x++ {
			closestID := -1
			closestDistance := math.MaxInt32
			for i, k := range coords {
				// Manhattan distance.
				distance := c.Abs(x-k.x) + c.Abs(y-k.y)
				if distance < closestDistance {
					closestDistance = distance
					closestID = i
				} else if distance == closestDistance {
					// Don't count if it's a tie.
					closestID = -1
				}
				// Add the distance for part B.
				partBCoords[(y*maxX)+x] += distance
			}
			if closestID >= 0 {
				// Exclude the ones that border the edge, they go to infinity.
				if (x == minX || x == maxX || y == minY || y == maxY) && !exclude[closestID] {
					exclude[closestID] = true
				}
				closest[closestID]++
			}
		}
	}
	// Part A: Find the largest area that is not infinity.
	largestNonInf := 0
	for i, d := range closest {
		if !exclude[i] && d > largestNonInf {
			largestNonInf = d
		}
	}

	// Part B: Count all tiles that are below 10k accumulated distance.
	areaPartB := 0
	for _, a := range partBCoords {
		if a > 0 && a < 10000 {
			areaPartB++
		}
	}
	return c.ToString(largestNonInf), c.ToString(areaPartB)
}

func newCoordinate(line string) *coordinate {
	split := c.MapStrI(c.SplitTrim(line, ','), c.ToIntOrPanic)
	result := new(coordinate)
	result.x = split[0]
	result.y = split[1]
	return result
}

type coordinate struct {
	x, y int
}
