package p02

import (
	"common"
	"fmt"
)

// --- Day 2: Corruption Checksum ---
// http://adventofcode.com/2017/day/2
func Solve(input string) (string, string) {
	return solveA(input), solveB(input)
}

func solveA(input string) string {
	diffs := 0
	for _, line := range common.SplitByNewline(input) {
		min, max := common.MinMaxArr(common.ParseIntArray(common.SplitByWhitespaceTrim(line)))
		diffs += max - min
	}
	return fmt.Sprintf("%d", diffs)
}

func solveB(input string) string {
	diffs := 0
	for _, line := range common.SplitByNewline(input) {
		diffs += findDivs(common.ParseIntArray(common.SplitByWhitespaceTrim(line)))
	}
	return fmt.Sprintf("%d", diffs)
}

func findDivs(nums []int) int {
	for i := 0; i < len(nums); i++ {
		for j := 0; j < len(nums); j++ {
			if i != j && nums[i] > nums[j] && nums[i]%nums[j] == 0 {
				return nums[i] / nums[j]
			}
		}
	}
	return 0
}
