package p17

import (
	"math"
	"strings"

	"s13g.com/euler/common"
)

// --- Day 17: Trick Shot ---
// http://adventofcode.com/2021/day/17
func Solve(input string) (string, string) {
	cropped := common.SplitByCommaTrim(input[13:])
	xRange := strings.Split(cropped[0][2:], "..")
	yRange := strings.Split(cropped[1][2:], "..")
	x1, x2 := common.ToIntOrPanic(xRange[0]), common.ToIntOrPanic(xRange[1])
	y1, y2 := common.ToIntOrPanic(yRange[0]), common.ToIntOrPanic(yRange[1])
	partA, partB := runSimulation(x1, x2, y1, y2)
	return common.ToString(partA), common.ToString(partB)
}

func runSimulation(x1 int, x2 int, y1 int, y2 int) (int, int) {
	highestY := 0
	numHittingVelocities := 0
	// Based on our input, both x values are positive.
	for dX := 0; dX <= 300; dX++ {
		for dY := -200; dY <= 200; dY++ {
			highY := simulate(dX, dY, x1, x2, y1, y2)
			if highY != math.MinInt {
				numHittingVelocities++
			}
			highestY = common.Max(highestY, highY)
		}
	}
	return highestY, numHittingVelocities
}

func simulate(dX int, dY int, x1 int, x2 int, y1 int, y2 int) int {
	x, y, maxY := 0, 0, math.MinInt
	for x <= common.Max(x1, x2) && y >= common.Min(y1, y2) {
		x += dX
		y += dY
		maxY = common.Max(maxY, y)
		if x1 <= x && x <= x2 && y1 <= y && y <= y2 {
			return maxY
		}
		dX += -common.Sign(dX)
		dY -= 1
	}
	return math.MinInt
}
