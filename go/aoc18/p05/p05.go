package p05

import (
	"strings"
	"unicode"

	c "s13g.com/euler/common"
)

// --- Day 5: Alchemical Reduction ---
// http://adventofcode.com/2018/day/5
func Solve(input string) (string, string) {
	return c.ToString(solveA(input)), c.ToString(solveB(input))
}

func solveA(inputStr string) int {
	input := []rune(inputStr)
	for i := 0; i < len(input)-2; i = c.Max(i+1, 0) {
		//if (unicode.ToLower(input[i]) == unicode.ToLower(input[i+1])) && (input[i] != input[i+1]) {
		if c.Abs(int(input[i])-int(input[i+1])) == 32 {
			input = append(input[:i], input[i+2:]...)
			i = i - 2
		}
	}
	return len(input)
}

func solveB(input string) int {
	abc := "abcdefghijklmnopqrstuvwxyz"
	ch := make(chan int)
	// Parallelize solving the problem for each letter.
	for _, r := range abc {
		go solveBForLetter(&input, r, ch)
	}

	// Wait for results from goroutines and find the smallest.
	smallest := len(input)
	for i := 0; i < len(abc); i++ {
		smallest = c.Min(smallest, <-ch)
	}
	return smallest
}

func solveBForLetter(input *string, r rune, ch chan<- int) {
	var replacer = strings.NewReplacer(string(r), "", string(unicode.ToUpper(r)), "")
	ch <- solveA(replacer.Replace(*input))
}
