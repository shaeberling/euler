package p07

import (
	"math"
	"sort"

	"s13g.com/euler/common"
)

// --- Day 7: The Treachery of Whales ---
// http://adventofcode.com/2021/day/7
func Solve(input string) (string, string) {
	crabs := common.ParseIntArray(common.SplitByCommaTrim(input))
	sort.Ints(crabs)
	medianCrab := crabs[len(crabs)/2]

	costA := 0
	for _, c := range crabs {
		dist := common.Abs(c - medianCrab)
		costA += dist
	}

	minCostB := math.MaxInt
	min, max := common.MinMaxArr(crabs)
	for i := min; i <= max; i++ {
		costB := 0
		for _, c := range crabs {
			dist := common.Abs(c - i)
			// 1+2+3+4+...+n => (n*(n+1))/2
			costB += (dist * (dist + 1)) / 2
		}
		minCostB = common.Min(minCostB, costB)
	}

	return common.ToString(costA), common.ToString(minCostB)
}
