package aoc16

import (
	"fmt"
	"aoc16/problems/p01"
	"aoc16/problems/p02"
	"path"
	"io/ioutil"
	"common"
)

// Defines a puzzle.
type Puzzle struct {
	name      string
	filename  string
	solver    func(string) (string, string)
	solutionA string
	solutionB string
}

// Run all the AOC 2016 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []Puzzle{
		{"AOC 2016.01", "aoc/2016/day1.txt", p01.Solve, "161", "110"},
		{"AOC 2016.02", "aoc/2016/day2.txt", p02.Solve, "", ""},
	}

	// Go through all puzzles and run them.
	for _, puzzle := range puzzles {
		solutionA, solutionB, err := solve(dataDir, &puzzle)
		if err != nil {
			fmt.Printf("Error running puzzle solver: %s\n", err)
		} else {
			fmt.Printf(compareResults(solutionA, solutionB, &puzzle))
		}
	}
}

// Solves a given puzzle with the given data directory.
func solve(dataDir string, puzzle *Puzzle) (string, string, error) {
	filename := path.Join(dataDir, puzzle.filename)
	if !common.IsRegularFile(filename) {
		return "", "", fmt.Errorf("not a readable file '%s'", filename)
	}

	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return "", "", fmt.Errorf("cannot read file '%s'", filename)
	}
	solutionA, solutionB := puzzle.solver(string(data))
	return solutionA, solutionB, nil
}

// Creates a result string for puzzles results
func compareResults(solutionA string, solutionB string, puzzle *Puzzle) string {
	result := "[" + puzzle.name + "]\n"
	result += fmt.Sprintf("  --> Solution A: %s\n", compareSolution(solutionA, puzzle.solutionA))
	result += fmt.Sprintf("  --> Solution B: %s\n", compareSolution(solutionB, puzzle.solutionB))
	return result + "\n"
}

// Creates a result string for a single solution.
func compareSolution(actual string, expected string) string {
	if actual == expected {
		return fmt.Sprintf("OK '%s'", actual)
	} else {
		return fmt.Sprintf("FAIL - Was '%s' but expected '%s'", actual, expected)
	}
}
