package p06

import (
	c "s13g.com/euler/common"
)

// --- Day 6: Lanternfish ---
// http://adventofcode.com/2021/day/6
func Solve(input string) (string, string) {
	fish := c.ParseIntArray(c.SplitByCommaTrim(input))
	fishDays := make(map[int]int64)
	for i := 0; i <= 9; i++ {
		fishDays[i] = 0
	}
	for _, f := range fish {
		fishDays[f]++
	}
	return c.ToString64(simulate(fishDays, 80)), c.ToString64(simulate(fishDays, 256))
}

func simulate(fishDays map[int]int64, days int) int64 {
	for i := 0; i < days; i++ {
		copy := make(map[int]int64)
		for daysLeft, num := range fishDays {
			if daysLeft > 0 {
				copy[daysLeft-1] += num
			} else {
				copy[6] += num
				copy[8] += num
			}
		}
		fishDays = copy
	}

	var count int64 = 0
	for _, num := range fishDays {
		count += num
	}
	return count
}
