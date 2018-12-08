package p08

import (
	c "common"
)

// --- Day 8: Memory Maneuver ---
// http://adventofcode.com/2018/day/8
func Solve(input string) (string, string) {
	nums := c.MapStrI(c.SplitByWhitespaceTrim(input), c.ToIntOrPanic)
	root, _ := newNode(nums, 'A')
	return c.ToString(solveA(root)), c.ToString(solveB(root))
}

func solveA(root node) int {
	return countMeta(&root)
}

// Simply sum up all metadata nodes for Part A.
func countMeta(n *node) int {
	result := c.Sum(n.metadata)
	for _, ch := range n.children {
		result += countMeta(&ch)
	}
	return result
}

func solveB(root node) int {
	return countForB(&root)
}

// Special index counting for Part B.
func countForB(n *node) int {
	if len(n.children) == 0 {
		return c.Sum(n.metadata)
	}
	result := 0
	for _, m := range n.metadata {
		if len(n.children) > m-1 {
			result += countForB(&n.children[m-1])
		}
	}
	return result
}

// Recursively create nodes.
func newNode(data []int, id rune) (node, int) {
	numChildren, numMeta := data[0], data[1]
	result := new(node)
	result.id = id
	result.children = make([]node, numChildren)
	// Start of data after the current node. Ether another node or metadata.
	nextStart := 2
	for i := 0; i < numChildren && nextStart < len(data); i++ {
		node, nextIdx := newNode(data[nextStart:], id+rune(i+1))
		result.children[i] = node
		nextStart += nextIdx
	}
	if numMeta > 0 {
		result.metadata = data[nextStart : nextStart+numMeta]
	}
	return *result, nextStart + numMeta
}

type node struct {
	id       rune
	children []node
	metadata []int
}
