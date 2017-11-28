package aoc17

import (
	"runner"
	"aoc17/problems"
)

// Run all the AOC 2017 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2017.01", "aoc/2017/day1.txt", problems.Solve01, "", ""},
		//{"AOC 2017.02", "aoc/2017/day2.txt", problems.Solve02, "", ""},
		//{"AOC 2017.03", "aoc/2017/day3.txt", problems.Solve03, "", ""},
		//{"AOC 2017.04", "aoc/2017/day4.txt", problems.Solve04, "", ""},
		//{"AOC 2017.05", "aoc/2017/day5.txt", problems.Solve05, "", ""},
		//{"AOC 2017.06", "aoc/2017/day6.txt", problems.Solve06, "", ""},
		//{"AOC 2017.07", "aoc/2017/day7.txt", problems.Solve07, "", ""},
		//{"AOC 2017.08", "aoc/2017/day8.txt", problems.Solve08, "", ""},
		//{"AOC 2017.09", "aoc/2017/day9.txt", problems.Solve09, "", ""},
		//{"AOC 2017.10", "aoc/2017/day10.txt", problems.Solve10, "", ""},
		//{"AOC 2017.11", "aoc/2017/day11.txt", problems.Solve11, "", ""},
		//{"AOC 2017.12", "aoc/2017/day12.txt", problems.Solve12, "", ""},
		//{"AOC 2017.13", "aoc/2017/day13.txt", problems.Solve13, "", ""},
		//{"AOC 2017.14", "aoc/2017/day14.txt", problems.Solve14, "", ""},
		//{"AOC 2017.15", "aoc/2017/day15.txt", problems.Solve15, "", ""},
		//{"AOC 2017.16", "aoc/2017/day16.txt", problems.Solve16, "", ""},
		//{"AOC 2017.17", "aoc/2017/day17.txt", problems.Solve17, "", ""},
		//{"AOC 2017.18", "aoc/2017/day18.txt", problems.Solve18, "", ""},
		//{"AOC 2017.19", "aoc/2017/day19.txt", problems.Solve19, "", ""},
		//{"AOC 2017.20", "aoc/2017/day20.txt", problems.Solve20, "", ""},
		//{"AOC 2017.21", "aoc/2017/day21.txt", problems.Solve21, "", ""},
		//{"AOC 2017.22", "aoc/2017/day22.txt", problems.Solve22, "", ""},
		//{"AOC 2017.23", "aoc/2017/day23.txt", problems.Solve23, "", ""},
		//{"AOC 2017.24", "aoc/2017/day24.txt", problems.Solve24, "", ""},
		//{"AOC 2017.25", "aoc/2017/day25.txt", problems.Solve25, "", ""},
	}
	runner.Run(dataDir, puzzles)
}
