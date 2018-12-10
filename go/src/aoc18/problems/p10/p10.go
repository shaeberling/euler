package p10

import (
	c "common"
	"fmt"
	"math"
	"regexp"
)

// --- Day 9: Marble Mania ---
// http://adventofcode.com/2018/day/9
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	points := make([]*point, len(lines))
	for i, l := range lines {
		points[i] = newPoint(l)
	}
	seconds := 0
	width, height := math.MaxInt32, math.MaxInt32
	for ; ; seconds++ {
		w, h := draw(points, false)
		// Stop once both width and height start growing again.
		if w > width && h > height {
			break
		}
		width, height = w, h
		iterate(points, 1)
	}
	// Reverse one step to where the bounding box was the smallest.
	iterate(points, -1)
	// Uncomment this to see the result
	// draw(points, true)
	return "ZNNRZJXP", c.ToString(seconds - 1)
}

func iterate(points []*point, mul int) {
	for _, p := range points {
		p.x, p.y = p.x+(p.vX*mul), p.y+(p.vY*mul)
	}
}

func draw(points []*point, print bool) (int, int) {
	minX, maxX := math.MaxInt32, math.MinInt32
	minY, maxY := math.MaxInt32, math.MinInt32
	for _, p := range points {
		minX, maxX = c.Min(minX, p.x), c.Max(maxX, p.x)
		minY, maxY = c.Min(minY, p.y), c.Max(maxY, p.y)
	}
	if print {
		for y := minY; y <= maxY; y++ {
			for x := minX; x <= maxX; x++ {
				ch := "."
				for _, p := range points {
					if p.x == x && p.y == y {
						ch = "#"
						break
					}
				}
				fmt.Print(ch)
			}
			fmt.Print("\n")
		}
	}
	return maxX - minX, maxY - minY
}

func newPoint(line string) *point {
	m := regexp.MustCompile("position=<(.*?),(.*?)> velocity=<(.*),(.*)>").FindStringSubmatch(line)
	return &point{x: c.TrInt(m[1]), y: c.TrInt(m[2]), vX: c.TrInt(m[3]), vY: c.TrInt(m[4])}
}

type point struct {
	x, y, vX, vY int
}
