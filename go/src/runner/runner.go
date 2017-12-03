package runner

import (
	"fmt"
	"path"
	"io/ioutil"
	"common"
	"time"
)

// Defines a puzzle.
type Puzzle struct {
	Name      string
	Filename  string
	Solver    func(string) (string, string)
	SolutionA string
	SolutionB string
}

// Run all the AOC 2016 puzzles
func Run(dataDir string, puzzles []Puzzle) {

	// Go through all puzzles and run them.
	for _, puzzle := range puzzles {
		solutionA, solutionB, elapsed, err := solve(dataDir, &puzzle)
		if err != nil {
			fmt.Printf("Error running puzzle solver: %s\n", err)
		} else {
			fmt.Printf(compareResults(solutionA, solutionB, &puzzle, elapsed))
		}
	}
}

// Solves a given puzzle with the given data directory.
func solve(dataDir string, puzzle *Puzzle) (string, string, time.Duration, error) {
	filename := path.Join(dataDir, puzzle.Filename)
	if !common.IsRegularFile(filename) {
		return "", "", 0, fmt.Errorf("not a readable file '%s'", filename)
	}

	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return "", "", 0, fmt.Errorf("cannot read file '%s'", filename)
	}
	start := time.Now()
	solutionA, solutionB := puzzle.Solver(string(data))
	elapsed := time.Now().Sub(start)
	return solutionA, solutionB, elapsed, nil
}

// Creates a result string for puzzles results
func compareResults(solutionA string, solutionB string, puzzle *Puzzle, elapsed time.Duration) string {
	result := fmt.Sprintf("[%s] - %v\n", puzzle.Name, elapsed)
	result += fmt.Sprintf("  --> Solution A: %s\n", compareSolution(solutionA, puzzle.SolutionA))
	result += fmt.Sprintf("  --> Solution B: %s\n\n", compareSolution(solutionB, puzzle.SolutionB))
	return result
}

// Creates a result string for a single solution.
func compareSolution(actual string, expected string) string {
	if actual == expected {
		return fmt.Sprintf("OK '%s'", actual)
	} else {
		return fmt.Sprintf("FAIL - Was '%s' but expected '%s'", actual, expected)
	}
}
