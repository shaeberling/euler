package p25

import (
	c "common"
)

// --- Day 25: Four-Dimensional Adventure ---
// http://adventofcode.com/2018/day/25
func Solve(input string) (string, string) {
	parsed := parseInput(c.SplitByNewline(input))
	return c.ToString(solveA(parsed)), ""
}

func solveA(lines [][4]int) int {
	// Maps a coordinate to its neightbors.
	within := make([][]int, len(lines))
	for i := 0; i < len(lines); i++ {
		within[i] = make([]int, 0)
	}
	for i := 0; i < len(lines); i++ {
		for j := 0; j < len(lines); j++ {
			if distance(lines[i], lines[j]) <= 3 {
				within[i] = append(within[i], j)
				within[j] = append(within[j], i)
			}
		}
	}
	// Creates a list of constellations.
	constellations := make([][]int, 0)
	for i := 0; i >= 0; i = findNextSolo(&constellations, len(within)) {
		constellations = append(constellations, buildConstellation(within, i))
	}
	return len(constellations)
}

// Build a constellation by starting with the coordinate with the given index 'i'.
func buildConstellation(within [][]int, i int) []int {
	toAdd := make([]int, 1)
	toAdd[0] = i

	resultM := make(map[int]bool)
	resultM[i] = true
	for len(toAdd) > 0 {
		// New list of all coordinates within reach.
		newToAdd := make([]int, 0)
		for _, d := range toAdd {
			newToAdd = append(newToAdd, within[d]...)
		}
		// Filter our the ones we have already added.
		toAdd = make([]int, 0)
		for _, a := range newToAdd {
			if _, found := resultM[a]; !found {
				toAdd = append(toAdd, a)
			}
			resultM[a] = true
		}
	}
	// Create resulting list of coordinate index which belong to this constellation.
	result := make([]int, 0)
	for dep := range resultM {
		result = append(result, dep)
	}
	return result
}

// Finds the next coordinate index which is not yet in any constellation.
func findNextSolo(constellations *[][]int, len int) int {
	for i := 0; i < len; i++ {
		if !contains(constellations, i) {
			return i
		}
	}
	return -1
}

// Whether the given coordinate index is part of any constellation.
func contains(constellations *[][]int, i int) bool {
	for c := 0; c < len(*constellations); c++ {
		for j := 0; j < len((*constellations)[c]); j++ {
			if (*constellations)[c][j] == i {
				return true
			}
		}
	}
	return false
}

// Manhatten distance in 4D.
func distance(a [4]int, b [4]int) int {
	return c.Abs(a[0]-b[0]) + c.Abs(a[1]-b[1]) + c.Abs(a[2]-b[2]) + c.Abs(a[3]-b[3])
}

func parseInput(lines []string) [][4]int {
	result := make([][4]int, len(lines))
	for i, l := range lines {
		split := c.ParseIntArray(c.SplitByCommaTrim(l))
		for j := 0; j < 4; j++ {
			result[i][j] = split[j]
		}
	}
	return result
}
