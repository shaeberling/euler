package p04

import (
	c "common"
)

// --- Day 4: High-Entropy Passphrases ---
// http://adventofcode.com/2017/day/4
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return c.ToString(solve(lines, func(s string) string { return s })),
		c.ToString(solve(lines, func(s string) string { return c.SortChars(s) }))
}

func solve(input []string, f func(string) string) (valid int) {
	for _, line := range input {
		seen := make(map[string]bool)
		for _, word := range c.SplitByWhitespaceTrim(line) {
			word = f(word)
			if seen[word] {
				valid--
				break
			}
			seen[word] = true
		}
		valid++
	}
	return
}
