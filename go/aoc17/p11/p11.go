package p11

import (
	"sort"
	"strings"

	c "s13g.com/euler/common"
)

func Solve(input string) (string, string) {
	solutionA, solutionB := solve(strings.Split(input, ","))
	return c.ToString(solutionA), c.ToString(solutionB)
}

func solve(dirs []string) (int, int) {
	// y0 and y1 represent the two diagonal directions.
	var x, y0, y1, maxDistance int

	// Since we have 3 axis to move on, the min distance is the two smallest deltas added.
	distance := func() int {
		pos := []int{c.Abs(x), c.Abs(y0), c.Abs(y1)}
		sort.Ints(pos)
		return pos[0] + pos[1]
	}
	for _, dir := range dirs {
		switch dir {
		case "n":
			y0, y1 = y0+1, y1+1
		case "s":
			y0, y1 = y0-1, y1-1
		case "ne":
			x, y1 = x+1, y1+1
		case "sw":
			x, y1 = x-1, y1-1
		case "se":
			x, y0 = x+1, y0-1
		case "nw":
			x, y0 = x-1, y0+1
		}
		maxDistance = c.Max(maxDistance, distance())
	}
	return distance(), maxDistance
}
