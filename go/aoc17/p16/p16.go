package p16

import (
	c "s13g.com/euler/common"
)

func Solve(input string) (string, string) {
	resultA, resultB := solve(input, 1000000000)
	return resultA, resultB
}

func solve(input string, loops int) (string, string) {
	// Parse input into functions for speed.
	funcs := parse(input)
	// Perform one dance.
	_, resultA := loopIt(1, "", funcs)
	// See how many iterations until we hit resultA again.
	numLoops, _ := loopIt(loops, resultA, funcs)
	// Only perform the remaining operations
	_, resultB := loopIt(loops%numLoops, "", funcs)
	return resultA, resultB
}

func loopIt(num int, match string, funcs []func(arr []rune) []rune) (int, string) {
	arr := []rune{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'}
	i := 0
	for ; i < num; i++ {
		for _, f := range funcs {
			arr = f(arr)
		}
		if i > 0 && toString(arr) == match {
			break
		}
	}
	return i, toString(arr)
}

func toString(arr []rune) (result string) {
	for _, c := range arr {
		result += string(c)
	}
	return
}

func partner(arr []rune, a rune, b rune) []rune {
	for i, v := range arr {
		if v == a {
			arr[i] = b
		} else if v == b {
			arr[i] = a
		}
	}
	return arr
}

func exchange(arr []rune, a int, b int) []rune {
	arr[a], arr[b] = arr[b], arr[a]
	return arr
}

func spin(arr []rune, num int) []rune {
	partA := arr[len(arr)-num:]
	partB := arr[0 : len(arr)-num]
	return append(partA, partB...)
}

func parse(input string) []func(arr []rune) []rune {
	ops := c.SplitByCommaTrim(input)
	result := make([]func(arr []rune) []rune, len(ops))

	for i, move := range ops {
		switch move[0] {
		case 's':
			x := c.ToIntOrPanic(move[1:])
			result[i] = func(arr []rune) []rune {
				return spin(arr, x)
			}
		case 'p':
			parts := c.SplitTrim(move[1:], '/')
			result[i] = func(arr []rune) []rune {
				return partner(arr, rune(parts[0][0]), rune(parts[1][0]))
			}
		case 'x':
			parts := c.SplitTrim(move[1:], '/')
			a, b := c.ToIntOrPanic(parts[0]), c.ToIntOrPanic(parts[1])
			result[i] = func(arr []rune) []rune {
				return exchange(arr, a, b)
			}
		}
	}
	return result
}
