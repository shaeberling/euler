package aoc17

import (
	"runner"
	"aoc17/problems/p01"
	"aoc17/problems/p02"
)

// Run all the AOC 2017 puzzles
func Run(dataDir string) {
	// Note: Add new puzzles here.
	puzzles := []runner.Puzzle{
		{"AOC 2017.01", "aoc/2017/day1.txt", p01.Solve, "995", "1130"},
		{"AOC 2017.02", "aoc/2017/day2.txt", p02.Solve, "48357", "351"},
		{"AOC 2017.02.hs", "aoc/2017/day2.txt", p02.SolveHs, "48357", "351"},
		//{"AOC 2017.03", "aoc/2017/day3.txt", p03.Solve, "", ""},
		//{"AOC 2017.04", "aoc/2017/day4.txt", p04.Solve, "", ""},
		//{"AOC 2017.05", "aoc/2017/day5.txt", p05.Solve, "", ""},
		//{"AOC 2017.06", "aoc/2017/day6.txt", p06.Solve, "", ""},
		//{"AOC 2017.07", "aoc/2017/day7.txt", p07.Solve, "", ""},
		//{"AOC 2017.08", "aoc/2017/day8.txt", p08.Solve, "", ""},
		//{"AOC 2017.09", "aoc/2017/day9.txt", p09.Solve, "", ""},
		//{"AOC 2017.10", "aoc/2017/day10.txt", p10.Solve, "", ""},
		//{"AOC 2017.11", "aoc/2017/day11.txt", p11.Solve, "", ""},
		//{"AOC 2017.12", "aoc/2017/day12.txt", p12.Solve, "", ""},
		//{"AOC 2017.13", "aoc/2017/day13.txt", p13.Solve, "", ""},
		//{"AOC 2017.14", "aoc/2017/day14.txt", p14.Solve, "", ""},
		//{"AOC 2017.15", "aoc/2017/day15.txt", p15.Solve, "", ""},
		//{"AOC 2017.16", "aoc/2017/day16.txt", p16.Solve, "", ""},
		//{"AOC 2017.17", "aoc/2017/day17.txt", p17.Solve, "", ""},
		//{"AOC 2017.18", "aoc/2017/day18.txt", p18.Solve, "", ""},
		//{"AOC 2017.19", "aoc/2017/day19.txt", p19.Solve, "", ""},
		//{"AOC 2017.20", "aoc/2017/day20.txt", p20.Solve, "", ""},
		//{"AOC 2017.21", "aoc/2017/day21.txt", p21.Solve, "", ""},
		//{"AOC 2017.22", "aoc/2017/day22.txt", p22.Solve, "", ""},
		//{"AOC 2017.23", "aoc/2017/day23.txt", p23.Solve, "", ""},
		//{"AOC 2017.24", "aoc/2017/day24.txt", p24.Solve, "", ""},
		//{"AOC 2017.25", "aoc/2017/day25.txt", p25.Solve, "", ""},
	}
	runner.Run(dataDir, puzzles)
}
