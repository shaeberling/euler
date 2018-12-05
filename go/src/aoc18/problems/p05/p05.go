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
	lower := strings.ToLower(input)
	abc := "abcdefghijklmnopqrstuvwxyz"
	ch := make(chan int)
	// Parallelize solving the problem for each letter.
	for _, r := range abc {
		go solveBForLetter(&input, &lower, r, ch)
	}

	// Wait for results from goroutines and find the smallest.
	smallest := len(input)
	for i := 0; i < len(abc); i++ {
		smallest = c.Min(smallest, <-ch)
	}
	return smallest
}

func solveBForLetter(input *string, lower *string, r rune, ch chan<- int) {
	newInput := ""
	for i, r2 := range *lower {
		if r != r2 {
			newInput += string((*input)[i])
		}
	}
	ch <- solveA(newInput)
}
