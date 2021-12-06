package p05

import (
	"regexp"

	c "s13g.com/euler/common"
)

// --- Day 5: Hydrothermal Venture ---
// http://adventofcode.com/2021/day/5
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	r := regexp.MustCompile("([\\d+]+),([\\d+]+) -> ([\\d+]+),([\\d+]+)")
	count := counter{make(map[pos]int), make(map[pos]int)}

	for _, line := range lines {
		parsed := c.ParseIntArray(r.FindStringSubmatch(line)[1:])
		x1, y1, x2, y2 := parsed[0], parsed[1], parsed[2], parsed[3]

		xDir := c.Sign(x2 - x1)
		yDir := c.Sign(y2 - y1)
		goodForA := xDir == 0 || yDir == 0

		count.add(pos{x1, y1}, goodForA)
		for x1 != x2 || y1 != y2 {
			x1, y1 = x1+xDir, y1+yDir
			count.add(pos{x1, y1}, goodForA)
		}
	}
	resultA, resultB := countOverOne(count.dataA), countOverOne(count.dataB)
	return c.ToString(resultA), c.ToString(resultB)
}

type pos struct {
	x int
	y int
}

type counter struct {
	dataA map[pos]int
	dataB map[pos]int
}

func (c *counter) add(key pos, partA bool) {
	if _, exists := c.dataB[key]; !exists {
		c.dataA[key], c.dataB[key] = 0, 0
	}
	if partA {
		c.dataA[key]++
	}
	c.dataB[key]++
}

func countOverOne(m map[pos]int) int {
	result := 0
	for _, c := range m {
		if c > 1 {
			result++
		}
	}
	return result
}
