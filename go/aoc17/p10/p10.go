package p10

import (
	"fmt"

	c "s13g.com/euler/common"
)

// --- Day 10: Knot Hash ---
// http://adventofcode.com/2017/day/10
func Solve(input string) (string, string) {
	return c.ToString(solveA(c.ParseIntArray(c.SplitByCommaTrim(input)))), solveB(input)
}

func solveA(lengths []int) int {
	numbers := ProcessLengths(lengths, 1)
	return numbers[0] * numbers[1]
}

func solveB(input string) string {
	// Convert the string into lengths from ASCII codes.
	lengths := make([]int, len(input))
	for i, c := range input {
		lengths[i] = int(c)
	}
	lengths = append(lengths, 17, 31, 73, 47, 23)
	numbers := ProcessLengths(lengths, 64)

	// Now create the dense hash.
	denseHash := make([]int, 16)
	for block := 0; block < 16; block++ {
		for start, i := block*16, 0; i < 16; i++ {
			denseHash[block] ^= numbers[start+i]
		}
	}
	return c.MapIStr(denseHash, func(b int) string { return fmt.Sprintf("%02x", b) })
}

// Make this available for future questions that need it.
func KnotHash(input string) string {
	return solveB(input)
}

func ProcessLengths(lengths []int, numRounds int) []int {
	// Reverses the given sub-section, treating it as a circular list.
	reverseCircular := func(list []int, start int, length int) {
		for l, r := start, start+length-1; l < r; l, r = l+1, r-1 {
			ll, rr := l%len(list), r%len(list)
			list[ll], list[rr] = list[rr], list[ll]
		}
	}

	// The array we will be working on.
	numbers := make([]int, 256)
	for i := range numbers {
		numbers[i] = i
	}

	// Process the data according to the rules.
	currPos, skipSize := 0, 0
	for round := 0; round < numRounds; round++ {
		for _, length := range lengths {
			reverseCircular(numbers, currPos, length)
			currPos += length + skipSize
			skipSize++
		}
	}
	return numbers
}
