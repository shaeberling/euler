package p13

import (
	c "common"
)

// --- Day 13: Packet Scanners ---
// http://adventofcode.com/2017/day/13
func Solve(input string) (string, string) {
	severity, _ := solveA(parseData(input), 0, false)
	return c.ToString(severity), c.ToString(solveB(parseData(input)))
}

func solveA(input map[int]int, delay int, returnIfCaught bool) (severity int, caught bool) {
	for k, v := range input {
		if (k+delay)%((v-1)*2) == 0 {
			severity += k * v
			if returnIfCaught {
				return severity, true
			}
		}
	}
	return
}

func solveB(input map[int]int) int {
	for i := 0; ; i++ {
		_, caught := solveA(input, i, true)
		if !caught {
			return i
		}
	}
	return -1
}

func parseData(input string) map[int]int {
	data := make(map[int]int)
	for _, line := range c.SplitByNewline(input) {
		parsed := c.SplitTrim(line, ':')
		data[c.ToIntOrPanic(parsed[0])] = c.ToIntOrPanic(parsed[1])
	}
	return data
}
