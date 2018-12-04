package p04

import (
	c "common"
	"sort"
	"strings"
	"time"
)

const dateLayout = "2006-01-02 15:04"

// --- Day 4: Repose Record ---
// http://adventofcode.com/2018/day/4
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	// Sorting should work since the day prefixes are in higher-bit-first order.
	sort.Strings(lines)

	// Maps guard -> minute histogram.
	sleepTimes := make(map[int][]int)
	guardNo, lastAsleep := 0, 0
	for _, line := range lines {
		dateStr := line[1:17]
		t, _ := time.Parse(dateLayout, dateStr)

		// Parse the action.
		action := line[19:]
		if strings.HasPrefix(action, "Guard #") {
			guardNo = c.ToIntOrPanic(c.SplitByWhitespaceTrim(action)[1][1:])
			if sleepTimes[guardNo] == nil {
				sleepTimes[guardNo] = make([]int, 60)
			}
		} else if action == "falls asleep" {
			lastAsleep = t.Minute()
		} else {
			// Must be waking up. Increment all minutes.
			for i := lastAsleep; i < t.Minute(); i++ {
				sleepTimes[guardNo][i]++
			}
		}
	}
	return c.ToString(solveA(sleepTimes)), c.ToString(solveB(sleepTimes))
}

func solveA(sleepTimes map[int][]int) int {
	// Find guard with most sleep.
	winningGuard, mostSleep, mostSleepMinute := 0, 0, 0
	for guard, minutes := range sleepTimes {
		sleep, sleepMinute, sleepMinuteAmount := 0, 0, 0
		for m, amount := range minutes {
			sleep += amount
			if amount > sleepMinuteAmount {
				sleepMinuteAmount = amount
				sleepMinute = m
			}
		}
		if sleep > mostSleep {
			mostSleep = sleep
			winningGuard = guard
			mostSleepMinute = sleepMinute
		}
	}
	return winningGuard * mostSleepMinute
}

func solveB(sleepTimes map[int][]int) int {
	// Find guard with most sleep at any given minute.
	winningGuard, winningMinute, mostMinutes := 0, 0, 0
	for guard, minutes := range sleepTimes {
		for m, amount := range minutes {
			if amount > mostMinutes {
				mostMinutes = amount
				winningGuard = guard
				winningMinute = m
			}
		}
	}
	return winningGuard * winningMinute
}
