package p07

import (
	"log"
	"strings"

	c "s13g.com/euler/common"
)

type Node struct {
	name        string
	ownWeight   int
	totalWeight int
	subNodes    []string
}

// --- Day 6: Memory Reallocation ---
// http://adventofcode.com/2017/day/6
func Solve(input string) (string, string) {
	inputArr := c.SplitByNewline(input)
	rootName := solveA(inputArr)
	return rootName, solveB(inputArr, rootName)
}

func solveA(input []string) string {
	nonRoots := make(map[string]bool)
	names := make(map[string]bool)
	for _, line := range input {
		name, _, deps := parseLine(line)
		names[name] = true
		for _, dep := range deps {
			nonRoots[dep] = true
		}
	}
	for name := range names {
		if !nonRoots[name] {
			return name
		}
	}
	return "na"
}

func solveB(input []string, rootName string) string {
	nodes := make(map[string]*Node)
	for _, line := range input {
		name, weight, deps := parseLine(line)
		nodes[name] = &Node{name, weight, 0, deps}
	}
	// Calculate all the weights recursively, starting at the root.
	return c.ToString(calculateWeights(nodes, rootName))
}

func calculateWeights(nodes map[string]*Node, nodeName string) int {
	node := nodes[nodeName]
	if node.totalWeight > 0 {
		return -1
	}

	for _, subNodeName := range node.subNodes {
		// If the return value is >0 it means we exit since we found the outlier.
		solution := calculateWeights(nodes, subNodeName)
		if solution > 0 {
			return solution
		}
	}

	// Try to find an outlier. If there is one, return the weight it should have.
	nodeWeight := func(n int) int { return nodes[node.subNodes[n]].totalWeight }
	for i := 2; i < len(node.subNodes); i++ {
		if nodeWeight(i-1) != nodeWeight(i) {
			log.Printf("There is an issue here.")
			for _, nn := range node.subNodes {
				log.Printf("%v", nodes[nn])
			}
			// TODO: We don't solve this case since it wasn't needed for the question.
			// We cannot find an outlier that's not the first element.
			return -1
		}
	}
	// If we got here, it means all values except the first one tested are
	// the same. So we need to only check the first one.
	if len(node.subNodes) > 1 {
		diff := nodeWeight(0) - nodeWeight(1)
		if diff != 0 {
			return nodes[node.subNodes[0]].ownWeight - diff
		}
	}

	// Calculate the node's total weight.
	for i := range node.subNodes {
		node.totalWeight += nodes[node.subNodes[i]].totalWeight
	}
	node.totalWeight += node.ownWeight
	return -1
}

func parseLine(line string) (string, int, []string) {
	parts := strings.Split(line, "->")
	firstHalf := c.SplitByWhitespaceTrim(parts[0])
	weightStr := strings.FieldsFunc(firstHalf[1], func(s rune) bool { return s == '(' || s == ')' })[0]
	deps := make([]string, 0)
	if len(parts) > 1 {
		deps = c.SplitByCommaTrim(parts[1])
	}
	return firstHalf[0], c.ToIntOrPanic(weightStr), deps
}
