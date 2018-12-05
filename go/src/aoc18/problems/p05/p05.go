package p05

import (
	c "common"
	"strings"
)

// --- Day 5: Alchemical Reduction ---
// http://adventofcode.com/2018/day/5
func Solve(input string) (string, string) {
	return c.ToString(solveA(input)), c.ToString(solveB(input))
}

func solveA(input string) int {
	lower := strings.ToLower(input)
	for i := 0; i < len(input)-2; i = c.Max(i+1, 0) {
		if (lower[i] == lower[i+1]) && (input[i] != input[i+1]) {
			input = input[:i] + input[i+2:]
			lower = lower[:i] + lower[i+2:]
			i = i - 2
		}
	}
	return len(input)
}

func solveB(input string) int {
	smallest := len(input)
	lower := strings.ToLower(input)
	for _, r := range "abcdefghijklmnopqrstuvwxyz" {
		newInput := ""
		for i, r2 := range lower {
			if r != r2 {
				newInput += string(input[i])
			}
		}
		smallest = c.Min(smallest, solveA(newInput))
	}
	return smallest
}
