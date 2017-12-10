package p06

import (
	c "common"
	"fmt"
	"log"
)

// --- Day 6: Memory Reallocation ---
// http://adventofcode.com/2017/day/6
func Solve(input string) (string, string) {
	inputArr := c.ParseIntArray(c.SplitByWhitespaceTrim(input))
	return c.ToString(solveA(inputArr)), c.ToString(solveB(inputArr))
}

func solveA(input []int) int {
	seen := make(map[string]bool)
	for {
		key := fmt.Sprintf("%v", input)
		if seen[key] {
			return len(seen)
		}
		seen[key] = true
		redistribute(input)
	}
}

// Takes a slice that was already processed by solveA and figures out the loop
// length.
func solveB(input []int) int {
	// We found a loop if we found the first occurrence of this first pattern.
	first := fmt.Sprintf("%v", input)
	for n := 0; ; n++ {
		redistribute(input)
		key := fmt.Sprintf("%v", input)
		if key == first {
			return n + 1
		}
	}
}

// Finds the first bucket with the max blocks inside and redistributes the
// contents.
func redistribute(input []int) {
	_, max := c.MinMaxArr(input)
	i, blocks := emptyBucket(input, max)

	// Redistribute
	for blocks > 0 {
		i = (i + 1) % len(input)
		input[i]++
		blocks--
	}
}

// Emptied the first bucket that has 'value' amount of blocks.
// Returns the index of the bucket that was emptied and the number of blocks.
func emptyBucket(input []int, value int) (int, int) {
	for i, v := range input {
		if v == value {
			blocks := input[i]
			input[i] = 0
			return i, blocks
		}
	}
	log.Fatalf("Cannot find memory bank with value %d", value)
	return -1, -1
}
