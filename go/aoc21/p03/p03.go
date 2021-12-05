package p03

import (
	"strconv"

	"s13g.com/euler/common"
)

// --- Day 3: Binary Diagnostic ---
// http://adventofcode.com/2021/day/3
func Solve(input string) (string, string) {
	lines := common.SplitByNewline(input)
	width := len(lines[0])

	countOnes := make([]int, width)
	countZeros := make([]int, width)

	for _, line := range lines {
		for i, c := range line {
			if c == '0' {
				countZeros[i]++
			} else {
				countOnes[i]++
			}
		}
	}

	g := 0
	e := 0
	for i := 0; i < width; i++ {
		if countOnes[i] > countZeros[i] {
			g += (1 << (width - 1 - i))
		} else {
			e += (1 << (width - 1 - i))
		}
	}
	partA := g * e

	oxy := filterPartB(lines, width, false)
	co2 := filterPartB(lines, width, true)
	partB := int(oxy * co2)

	return common.ToString(partA), common.ToString(partB)
}

func filterPartB(lines []string, width int, isCo2 bool) int {
	candidates := append([]string{}, lines...)
	for i := 0; i < width && len(candidates) > 1; i++ {
		ones, zeros := countOnesAndZeros(candidates, i)
		survivors := make([]string, 0)
		winner, looser := '0', '0'
		if ones >= zeros {
			winner = '1'
		}
		if ones < zeros {
			looser = '1'
		}
		for _, line := range candidates {
			if (!isCo2 && line[i] == byte(winner)) ||
				(isCo2 && line[i] == byte(looser)) {
				survivors = append(survivors, line)
			}
		}
		candidates = survivors
	}
	value, _ := strconv.ParseInt(candidates[0], 2, 32)
	return int(value)
}

func countOnesAndZeros(lines []string, idx int) (int, int) {
	ones, zeros := 0, 0
	for _, line := range lines {
		if line[idx] == '0' {
			zeros++
		} else {
			ones++
		}
	}
	return ones, zeros
}
