package stairs

import (
	"common"
	"fmt"
)

// https://careercup.com/question?id=5755066370752512
func Stairs() {
	// The number of steps allowed to be taken on odd and even turns.
	odd, even := []int{1, 2, 4}, []int{1, 3, 4}

	// Essentially a set storing all ways we can reach the bottom.
	numWays := make(map[string]bool)

	// Each digit in this 5-digit integer represent an index into the step
	// options above. Only numbers that consists exclusively of number 0,1,2
	// will be considered. This way we walk every possible combination and check.
	for i := 0; i < 22222; i += 1 {
		// Convert the number to its 5 digits and check if it's valid.
		d := digits(i)
		if !isValid(d) {
			continue
		}

		// We start on stair number 5
		position := 5
		seq := ""

		// Since we start on 5 and the smallest step is 1, five is the most we would ever walk.
		for s := 0; s < 5; s++ {
			// Depending on odd or even turn, apply the step.
			change := odd[d[s]]
			if (s+1)%2 == 0 {
				change = even[d[s]]
			}
			seq += common.ToString(change)
			position -= change

			// If we reached the bottom, mark the sequence, if it hasn't already.
			if position == 0 {
				numWays[seq] = true
				break
			}
		}
	}
	fmt.Printf("The %d ways to get down are:\n", len(numWays))
	for k := range numWays {
		fmt.Println(k)
	}
}

// Check whether no digit exceeds two (the max index in the turn arrays).
func isValid(i []int) bool {
	for _, v := range i {
		if v > 2 {
			return false
		}
	}
	return true
}

// Split a number into its digits. LSB first.
func digits(num int) []int {
	result := make([]int, 5)
	for i := 0; num > 0; i += 1 {
		digit := num % 10
		num /= 10
		result[i] = digit
	}
	return result
}
