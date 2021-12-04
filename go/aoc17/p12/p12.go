package p12

import (
	"strings"

	c "s13g.com/euler/common"
)

// --- Day 12: Digital Plumber ---
// http://adventofcode.com/2017/day/12
func Solve(input string) (string, string) {
	inputArr := c.SplitByNewline(input)
	return c.ToString(solveA(inputArr)), c.ToString(solveB(inputArr))
}

func solveA(input []string) int {
	conn, all := parse(input)
	return group(0, make(map[int]bool), conn, all)
}

func solveB(input []string) int {
	conn, all := parse(input)
	// Get the next number we haven't associated with an existing group yet.
	// Starting with the first number we have in the system, go through its
	// dependency and build a complete group. Once done, go to the next item
	// that is not yet in any group and repeat until all numbers are accounted
	// for.
	var i int
	for nextNum := 0; nextNum >= 0; i, nextNum = i+1, c.FindFirstTrue(all) {
		group(nextNum, make(map[int]bool), conn, all)
	}
	return i
}

// Returns 1) a map from number to list of numbers that have a connection to the number.
//         2) A map of all the numbers in the system, set to true.
func parse(input []string) (map[int][]int, map[int]bool) {
	conn, all := make(map[int][]int), make(map[int]bool)
	for _, line := range input {
		parts := strings.Split(line, "<->")
		v0 := c.ToIntOrPanic(strings.TrimSpace(parts[0]))
		rhs := c.ParseIntArray(c.SplitByCommaTrim(parts[1]))
		for _, n := range rhs {
			conn[n], all[n] = append(conn[n], v0), true
		}
		conn[v0], all[v0] = append(conn[v0], rhs...), true
	}
	return conn, all
}

// Recursively go through all numbers that are in the same group as 'start'.
func group(start int, attached map[int]bool, conn map[int][]int, all map[int]bool) (count int) {
	if attached[start] {
		return
	}
	attached[start] = true
	all[start] = false
	for _, n := range conn[start] {
		count += group(n, attached, conn, all)
	}
	return count + 1
}
