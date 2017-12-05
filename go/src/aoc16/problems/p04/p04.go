package p04

import (
	c "common"
	"strings"
	"sort"
)

// --- Day 4: Security Through Obscurity ---
// http://adventofcode.com/2016/day/4
func Solve(input string) (string, string) {
	inputArr := c.SplitByNewline(input)
	return c.ToString(solveA(inputArr)), c.ToString(solveB(inputArr, "northpole object storage"))
}

func solveA(input []string) int {
	sum := 0
	for _, line := range input {
		counts, checksum, sectorId := countsAndChecksum(line)
		sort.Sort(counts)

		actualChecksum := ""
		for _, cc := range counts.counts {
			actualChecksum += string(cc.char)
		}
		if actualChecksum[0:5] == checksum {
			sum += sectorId
		}
	}
	return sum
}

func solveB(input []string, roomName string) int {
	for _, line := range input {
		line = strings.Split(line, "[")[0]
		parts := strings.Split(line, "-")

		sectorId := c.ToIntOrPanic(parts[len(parts)-1])
		decodedName := ""
		for i, part := range parts {
			if i == len(parts)-1 {
				break
			}
			if i > 0 {
				decodedName += " "
			}
			decodedName += decode(part, sectorId)
		}
		if decodedName == roomName {
			return sectorId
		}
	}
	return 0
}

// Decodes the string with the given sectorID, to the rules of Part Two.
func decode(str string, sid int) (result string) {
	const numChars = 'z' - 'a' + 1
	for _, char := range str {
		newChar := ((int(char) - 'a' + sid) % numChars) + 'a'
		result += string(newChar)
	}
	return
}

// Get character counts and the checksum of the given line, plus checksum.
func countsAndChecksum(line string) (*charCounts, string, int) {
	counts := new(charCounts)
	checksum := ""
	sectorId := 0

	split := strings.Split(line, "-")
	for i, code := range split {
		if i == len(split)-1 {
			cs := strings.FieldsFunc(code, func(r rune) bool { return r == '[' || r == ']' })
			sectorId = c.ToIntOrPanic(cs[0])
			checksum = cs[1]
			break
		}
		for _, char := range code {
			if char != '-' {
				counts.Add(char)
			}
		}
	}
	return counts, checksum, sectorId
}

// Stores counts of a char.
type charCount struct {
	char  rune
	count int
}

// A collection of counts of a char that can be sorted.
type charCounts struct {
	counts []*charCount
}

// Add a new char to the counts.
func (cc *charCounts) Add(c rune) {
	for _, count := range cc.counts {
		if count.char == c {
			count.count++
			return
		}
	}
	newItem := new(charCount)
	newItem.char, newItem.count = c, 1
	cc.counts = append(cc.counts, newItem)
}

// Len to satisfy the sort.Interface
func (cc *charCounts) Len() int {
	return len(cc.counts)
}

// Less to satisfy the sort.Interface
func (cc *charCounts) Less(i, j int) bool {
	a, b := cc.counts[i], cc.counts[j]
	if a.count == b.count {
		return a.char < b.char
	}
	return a.count > b.count
}

// Swap to satisfy the sort.Interface
func (cc *charCounts) Swap(i, j int) {
	cc.counts[i], cc.counts[j] = cc.counts[j], cc.counts[i]
}
