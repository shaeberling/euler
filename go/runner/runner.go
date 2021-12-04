package runner

import (
	"fmt"
	"io/ioutil"
	"path"
	"time"

	"s13g.com/euler/common"
)

type SolverFunc = func(string) (string, string)

type Runner struct {
	year    int
	dataDir string
	puzzles []puzzle
}

// Initialize this runner for the given year.
func InitRunner(year int, dataDir string) Runner {
	return Runner{year, dataDir, make([]puzzle, 0)}
}

// Add a new Puzzle to run later.
func (r *Runner) AddPuzzle(day int, solver SolverFunc, solA string, solB string) {
	name := fmt.Sprintf("AOC %d.%d", r.year, day)
	filename := fmt.Sprintf("aoc/%d/day%d.txt", r.year, day)
	r.puzzles = append(r.puzzles, puzzle{name, filename, solver, solA, solB})
}

func (r Runner) Run() {
	fmt.Printf("Will run %d puzzles\n", len(r.puzzles))
	// Go through all puzzles and run them.
	for _, puzzle := range r.puzzles {
		solutionA, solutionB, elapsed, err := solve(r.dataDir, &puzzle)
		if err != nil {
			fmt.Printf("Error running puzzle solver: %s\n", err)
		} else {
			fmt.Printf(compareResults(solutionA, solutionB, &puzzle, elapsed))
		}
	}
}

// Defines a puzzle.
type puzzle struct {
	name      string
	filename  string
	solver    SolverFunc
	solutionA string
	solutionB string
}

// Solves a given puzzle with the given data directory.
func solve(dataDir string, puzzle *puzzle) (string, string, time.Duration, error) {
	filename := path.Join(dataDir, puzzle.filename)
	if !common.IsRegularFile(filename) {
		return "", "", 0, fmt.Errorf("not a readable file '%s'", filename)
	}

	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return "", "", 0, fmt.Errorf("cannot read file '%s'", filename)
	}
	start := time.Now()
	solutionA, solutionB := puzzle.solver(string(data))
	elapsed := time.Now().Sub(start)
	return solutionA, solutionB, elapsed, nil
}

// Creates a result string for puzzles results
func compareResults(solutionA string, solutionB string, puzzle *puzzle, elapsed time.Duration) string {
	result := fmt.Sprintf("[%s] - %v\n", puzzle.name, elapsed)
	result += fmt.Sprintf("  --> Solution A: %s\n", compareSolution(solutionA, puzzle.solutionA))
	result += fmt.Sprintf("  --> Solution B: %s\n\n", compareSolution(solutionB, puzzle.solutionB))
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
