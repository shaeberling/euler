package p24

import (
	c "s13g.com/euler/common"
)

// --- Day 24: Electromagnetic Moat ---
// http://adventofcode.com/2017/day/24
func Solve(input string) (string, string) {
	solutionA, solutionB := solve(c.SplitByNewline(input))
	return c.ToString(solutionA), c.ToString(solutionB)
}
func solve(lines []string) (int, int) {
	input := make([][]int, len(lines))
	for i, line := range lines {
		input[i] = c.ParseIntArray(c.SplitTrim(line, '/'))
	}
	longestLength, longestStrength, strongest := 0, 0, 0
	onNew := func(strength, length int) {
		strongest = c.Max(strongest, strength)
		if length > longestLength {
			longestLength = length
			longestStrength = strength
		} else if length == longestLength {
			longestStrength = c.Max(longestStrength, strength)
		}
	}
	// Recursive depth-first search. Callback with results that reached the end.
	build(input, make([]int, 0), 0, onNew)
	return strongest, longestStrength
}

func build(input [][]int, links []int, nextConn int, onNew func(newSum, length int)) {
	endOfLine := true
	for i, link := range input {
		if !isIn(i, links) && (link[0] == nextConn || link[1] == nextConn) {
			endOfLine = false
			links2 := make([]int, len(links)+1)
			copy(links2, links)
			links2[len(links)] = i
			if link[0] == nextConn {
				build(input, links2, link[1], onNew)
			} else {
				build(input, links2, link[0], onNew)
			}
		}
	}
	if endOfLine {
		result := 0
		for _, x := range links {
			result += input[x][0] + input[x][1]
		}
		onNew(result, len(links))
	}
}

func isIn(i int, items []int) bool {
	for _, item := range items {
		if item == i {
			return true
		}
	}
	return false
}
