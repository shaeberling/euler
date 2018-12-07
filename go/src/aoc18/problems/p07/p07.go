package p07

import (
	c "common"
	"math"
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
		preReq, step := splitLine[1], splitLine[7]
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
		removePres(&order, []string{ready[0]})
		result += ready[0]
	}
	return result
}

// Parameters for part B (different from example).
const numWorkers, addTime = 5, 60

func solveB(order map[string][]string) string {
	workers, time := [numWorkers]worker{}, 0
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

		// How much time will pass until the next task is done.
		timeToNextEvent := math.MaxInt32
		for _, w := range workers {
			if w.tRemain > 0 {
				timeToNextEvent = c.Min(timeToNextEvent, w.tRemain)
			}
		}
		time += timeToNextEvent

		// Fast forward time to next event.
		for i := range workers {
			workers[i].tRemain -= timeToNextEvent
		}
		// Find ready tasks.
		readyB := make([]string, 0)
		for i, w := range workers {
			// Only tasks finishing right now have tRemain of exactly zero.
			if w.tRemain == 0 {
				readyB = append(readyB, w.task)
			}
			// Previously finished tasks will have a negative tRemain.
			workers[i].tRemain = c.Max(workers[i].tRemain, 0)
		}
		// Remove all ready steps from prereqs, since they're done.
		removePres(&order, readyB)
	}
	return c.ToString(time)
}

// Remove prerequisites.
func removePres(order *map[string][]string, presToRemove []string) {
	for step, pre := range *order {
		newPre := make([]string, 0)
		for _, p := range pre {
			if !c.Contains(presToRemove, p) {
				newPre = append(newPre, p)
			}
		}
		(*order)[step] = newPre
	}
}

type worker struct {
	task    string
	tRemain int
}
