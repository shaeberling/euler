package p01

import (
	"strconv"
	"fmt"
)

func Solve(input string) (string, string) {
	return solve(input, 1), solve(input, len(input)/2)
}

func solve(input string, lookahead int) string {
	sum := 0

	for i := 0; i < len(input); i += 1 {
		if input[i] == input[(i+lookahead)%len(input)] {
			v, err := strconv.ParseInt(string(input[i]), 10, 64)
			if err != nil {
				panic("Cannot parse " + err.Error())
			}
			sum += int(v)
		}
	}
	return fmt.Sprintf("%d", sum)
}
