package p02

import (
	h "aoc17/hs"
	"fmt"
)

// Trying to solve it like a haskell programmer. (not really ;-) )
func SolveHs(input string) (string, string) {
	// parseInput = map (map read . words) . lines
	parseInput := func(str string) [][]string { return h.Mps(h.Mp(input)) }
	return solveAhs(parseInput(input)), solveBhs(parseInput(input))
}

func solveAhs(input [][]string) string {
	// run1 input = sum $ map (\xs -> abs (minimum xs - maximum xs)) input
	diffs := h.Sum(h.Mpii(input, func(s []string) int { return h.Max(s) - h.Min(s) }))
	return fmt.Sprintf("%d", diffs)
}

func solveBhs(input [][]string) string {
	diffs := h.Sum(h.Mpii(input, func(s []string) int { return h.Sum(h.Xmpi(s, divResult)) }))
	return fmt.Sprintf("%d", diffs)
}

func divResult(x, y int) int {
	if x%y == 0 {
		return x / y
	}
	return 0
}
