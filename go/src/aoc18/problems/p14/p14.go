package p14

import (
	c "common"
	"fmt"
)

// --- Day 14: Chocolate Charts ---
// http://adventofcode.com/2018/day/14
func Solve(input string) (string, string) {
	in := c.ToIntOrPanic(input)
	return solveA(in), solveB(in)
}

func solveA(in int) string {
	r := newRecipes()
	for {
		r.iterate()
		if len(r.state) > in+12 {
			return toIntString(r.state[in : in+10])
		}
	}
}

func solveB(in int) string {
	inStr := fmt.Sprintf("%d", in)
	query := make([]int, len(inStr))
	for i, s := range inStr {
		query[i] = int(s - '0')
	}
	r := newRecipes()
	for {
		r.iterate()

		if len(r.state) > len(query) {
			// Two tails since we might have added two items above.
			tail := r.state[len(r.state)-len(query):]
			tail2 := r.state[len(r.state)-len(query)-1 : len(r.state)-1]
			if eq(tail, query) {
				return c.ToString(len(r.state) - len(query))
			}
			if eq(tail2, query) {
				return c.ToString(len(r.state) - len(query) - 1)
			}
		}
	}
}

func newRecipes() *recipes {
	return &recipes{
		elf1:  0,
		elf2:  1,
		state: []int{3, 7},
	}
}

type recipes struct {
	elf1, elf2 int
	state      []int
}

func (r *recipes) iterate() {
	newRecipe := r.state[r.elf1] + r.state[r.elf2]
	if newRecipe < 10 {
		r.state = append(r.state, newRecipe)
	} else {
		p2 := newRecipe % 10
		p1 := (newRecipe - p2) / 10
		r.state = append(r.state, p1, p2)
	}
	r.elf1 = (r.elf1 + 1 + r.state[r.elf1]) % len(r.state)
	r.elf2 = (r.elf2 + 1 + r.state[r.elf2]) % len(r.state)
}

func eq(a []int, b []int) bool {
	if len(a) != len(b) {
		return false
	}
	for i := 0; i < len(a); i++ {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}

func toIntString(state []int) string {
	result := ""
	for _, s := range state {
		result += fmt.Sprintf("%d", s)
	}
	return result
}
