package p01

import (
	"fmt"
	"common"
)

func Solve(input string) (string, string) {
	return solve(input, 1), solve(input, len(input)/2)
}

func solve(input string, lookahead int) string {
	sum := 0
	for i := 0; i < len(input); i += 1 {
		if input[i] == input[(i+lookahead)%len(input)] {
			sum += int(common.ToIntOrPanic(string(input[i])))
		}
	}
	return fmt.Sprintf("%d", sum)
}
