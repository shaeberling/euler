package p07

import (
	c "common"
	"sort"
)

// --- Day 7: The Sum of Its Parts ---
// http://adventofcode.com/2018/day/7
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return solveA(parse(lines)), solveB(parse(lines))
}

func parse(lines []string) map[string][]string {
	order := make(map[string][]string)
	for _, l := range lines {
		splitLine := c.SplitByWhitespaceTrim(l)
		preReq := splitLine[1]
		step := splitLine[7]

		if _, ok := order[step]; !ok {
			order[step] = make([]string, 0)
		}
		if _, ok := order[preReq]; !ok {
			order[preReq] = make([]string, 0)
		}
		order[step] = append(order[step], preReq)
	}
	return order
}

func solveA(order map[string][]string) string {
	result := ""
	for len(order) > 0 {
		ready := make([]string, 0)
		for step, pre := range order {
			if len(pre) == 0 {
				ready = append(ready, step)
			}
		}
		// Only the first alphabetically is removed.
		sort.Strings(ready)
		delete(order, ready[0])

		// Remove all ready steps from prereqs, since they're done.
		for step, pre := range order {
			newPre := make([]string, 0)
			for _, p := range pre {
				if p == ready[0] {
					continue
				}
				newPre = append(newPre, p)
			}
			order[step] = newPre
		}
		result += ready[0]
	}
	return result
}

// Parameters for part B (different from example).
const numWorkers = 5
const addTime = 60

func solveB(order map[string][]string) string {
	// How many seconds is each worker busy for.
	workers := [numWorkers]worker{}
	time := 0

	for len(order) > 0 {
		ready := make([]string, 0)
		for step, pre := range order {
			if len(pre) == 0 {
				ready = append(ready, step)
			}
		}
		// Remove ready tasks alphabetically.
		sort.Strings(ready)

		for _, r := range ready {
			for j, w := range workers {
				// Worker is not busy right now
				if w.tRemain == 0 {
					workers[j].task = r
					workers[j].tRemain = addTime + int(r[0]-'A'+1)
					delete(order, r)
					break
				}
			}
		}
		readyB := make([]string, 0)
		// How much time will pass until the next task is done.
		for len(readyB) == 0 {
			time++
			for i := range workers {
				workers[i].tRemain--
				if workers[i].tRemain == 0 {
					// Task is done
					readyB = append(readyB, workers[i].task)
				}
				workers[i].tRemain = c.Max(workers[i].tRemain, 0)
			}
		}

		// Remove all ready steps from prereqs, since they're done.
		for step, pre := range order {
			newPre := make([]string, 0)
			for _, p := range pre {
				skip := false
				for _, r := range readyB {
					if p == r {
						skip = true
						break
					}
				}
				if !skip {
					newPre = append(newPre, p)
				}
			}
			order[step] = newPre
		}
	}
	return c.ToString(time)
}

type worker struct {
	task    string
	tRemain int
}
