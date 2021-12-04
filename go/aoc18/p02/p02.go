package p02

import (
	c "s13g.com/euler/common"
)

// --- Day 2: Inventory Management System ---
// http://adventofcode.com/2018/day/2
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solveA(lines)), solveB(lines)
}

func solveA(lines []string) int {
	numTwos, numThrees := 0, 0
	for _, line := range lines {
		counts := make(map[rune]int)
		for _, c := range line {
			counts[c]++
		}
		numTwos += retOneIfMapContainsValue(counts, 2)
		numThrees += retOneIfMapContainsValue(counts, 3)
	}
	return numTwos * numThrees
}

func retOneIfMapContainsValue(counts map[rune]int, value int) int {
	for _, num := range counts {
		if num == value {
			return 1
		}
	}
	return 0
}

func solveB(lines []string) string {
	for x := 0; x < len(lines)-1; x++ {
		for y := x + 1; y < len(lines); y++ {
			diff, result := countDiffChars(lines[x], lines[y])
			if diff == 1 {
				return result
			}
		}
	}
	return "not found"
}

func countDiffChars(a string, b string) (int, string) {
	diff, result := 0, ""
	for i := 0; i < len(a); i++ {
		if a[i] == b[i] {
			result += string(a[i])
		} else {
			diff++
		}
	}
	return diff, result
}
