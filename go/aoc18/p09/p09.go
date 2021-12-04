package p09

import (
	c "s13g.com/euler/common"
)

// --- Day 9: Marble Mania ---
// http://adventofcode.com/2018/day/9
func Solve(input string) (string, string) {
	split := c.SplitByWhitespaceTrim(input)
	players, lastMarble := c.ToIntOrPanic(split[0]), c.ToIntOrPanic(split[6])
	return c.ToString(solveFor(players, lastMarble)), c.ToString(solveFor(players, lastMarble*100))
}

func solveFor(players, lastMarble int) int {
	scores := make([]int, players)
	curr := &marble{id: 0}
	curr.next, curr.prev = curr, curr

	for i, currPlayer := 1, 0; i <= lastMarble; i++ {
		if i%23 == 0 {
			scores[currPlayer] += i
			remove := curr.prev.prev.prev.prev.prev.prev.prev
			scores[currPlayer] += remove.id
			remove.prev.next = remove.next
			remove.next.prev = remove.prev
			curr = remove.next
		} else {
			newMarble := &marble{id: i, next: curr.next.next, prev: curr.next}
			curr.next.next.prev, curr.next.next = newMarble, newMarble
			curr = newMarble
		}
		currPlayer = (currPlayer + 1) % players
	}
	_, max := c.MinMaxArr(scores)
	return max
}

type marble struct {
	id         int
	next, prev *marble
}
