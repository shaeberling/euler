package p17

import (
	c "common"
)

func Solve(input string) (string, string) {
	return c.ToString(solveA(c.ToIntOrPanic(input))), c.ToString(solveB(c.ToIntOrPanic(input)))
}

func solveA(offset int) int {
	buffer := make([]int, 1)
	buffer[0] = 0

	pos := 0
	for n := 1; n <= 2017; n++ {
		pos = (pos + offset) % len(buffer)
		// TODO: Should look into https://golang.org/pkg/container/ring/
		buffer = append(buffer[:pos+1], append(itemSlice(n), buffer[pos+1:]...)...)
		pos++
	}
	return buffer[(pos+1)%len(buffer)]
}

func solveB(offset int) int {
	// Why this works: Since the new position cannot be negative and we always
	// insert AFTER the new position, this leads to '0' always staying at the
	// beginning of the array. This also means that the once after '0' will be
	// index '1' which only ever changes if the new insert position index is 0.
	// Hence, it's sufficient to loop and calculate the new insert positions
	// only. We need to know what the last insert position after 0 was and this
	// will be the result.
	pos, result := 0, -1
	for n := 1; n <= 50000000; n++ {
		pos = (pos + offset) % n
		if pos == 0 {
			result = n
		}
		pos++
	}
	return result
}

func itemSlice(n int) []int {
	result := make([]int, 1)
	result[0] = n
	return result
}
