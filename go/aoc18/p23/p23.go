package p23

import (
	"math"
	"regexp"

	c "s13g.com/euler/common"
)

// --- Day 23: Experimental Emergency Teleportation ---
// http://adventofcode.com/2018/day/23
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	bots := make([]*nanobot, len(lines))
	largestRadius, largestIndex := math.MinInt32, 0
	for i, l := range lines {
		bots[i] = newNanobot(l)
		if bots[i].radius > largestRadius {
			largestRadius = bots[i].radius
			largestIndex = i
		}
	}
	return c.ToString(solveA(bots, largestIndex)), c.ToString(solveB(bots))
}

func solveB(bots []*nanobot) int {
	// // Calculcate overlapping areas.
	// overlaps := make(map[int][]int, len(bots))

	// minX, maxX, minY, maxY := math.MaxInt32, math.MinInt32, math.MaxInt32, math.MinInt32
	// for i := 0; i < len(bots)-1; i++ {
	// 	minX = c.Min(minX, bots[i].pos[0])

	// 	for j := i + 1; j < len(bots); j++ {
	// 		if distance3d(bots[i].pos, bots[j].pos) <= bots[i].radius+bots[j].radius {
	// 			overlaps[i] = append(overlaps[i], j)
	// 			overlaps[j] = append(overlaps[j], i)
	// 		}
	// 	}
	// }

	// fmt.Printf("Overlaps: %+v\n", overlaps)

	return 42

}

func solveA(bots []*nanobot, largestIndex int) int {
	xl := bots[largestIndex]
	numInRange := 0
	for _, b := range bots {
		if distance3d(b.pos, xl.pos) <= xl.radius {
			numInRange++
		}
	}
	return numInRange
}

func distance3d(a []int, b []int) int {
	return c.Abs(a[0]-b[0]) + c.Abs(a[1]-b[1]) + c.Abs(a[2]-b[2])
}

func newNanobot(line string) *nanobot {
	m := regexp.MustCompile("^pos=<(.*?),(.*?),(.*?)>, r=(.*?)$").FindStringSubmatch(line)
	return &nanobot{
		pos:    []int{c.ToIntOrPanic(m[1]), c.ToIntOrPanic(m[2]), c.ToIntOrPanic(m[3])},
		radius: c.ToIntOrPanic(m[4]),
	}
}

type nanobot struct {
	pos    []int
	radius int
}
