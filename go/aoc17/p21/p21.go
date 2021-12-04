package p21

import (
	"log"
	"strings"

	c "s13g.com/euler/common"
)

// --- Day 21: Fractal Art ---
// http://adventofcode.com/2017/day/21
func Solve(input string) (string, string) {
	return c.ToString(solve(c.SplitByNewline(input), 5)), c.ToString(solve(c.SplitByNewline(input), 18))
}

func solve(input []string, numIterations int) int {
	// Parse input rules.
	rules := make([]*rule, len(input))
	for i, line := range input {
		rules[i] = newRule(line)
	}

	// Creating starting picture.
	p := newPicture(3)
	p.set(1, 0, true)
	p.set(2, 1, true)
	p.set(0, 2, true)
	p.set(1, 2, true)
	p.set(2, 2, true)

	for iter := 0; iter < numIterations; iter++ {
		if p.size%2 == 0 {
			p = transform(p, rules, 2)
		} else {
			p = transform(p, rules, 3)
		}
	}
	return p.getNumOn()
}

func transform(p *picture, rules []*rule, size int) *picture {
	resultList := make([]*rule, 0)
	for y := 0; y < p.size; y += size {
		for x := 0; x < p.size; x += size {
			for _, r := range rules {
				if r.size == size && r.matches(x, y, p) {
					resultList = append(resultList, r)
					break
				}
			}
		}
	}
	if (p.size/size)*(p.size/size) != len(resultList) {
		log.Fatalf("Unexpected matched rule size of %d ", len(resultList))
	}
	ruleOutSize := size + 1
	result := newPicture((p.size / size) * ruleOutSize)

	// Re-assemble the outputs of all the matched rules to the new image.
	for i, r := range resultList {
		x := (i * ruleOutSize) % result.size
		y := ((i * ruleOutSize) / result.size) * ruleOutSize
		for rx := 0; rx < ruleOutSize; rx++ {
			for ry := 0; ry < ruleOutSize; ry++ {
				result.set(x+rx, y+ry, r.getOutput(rx, ry))
			}
		}
	}
	return result
}

type rule struct {
	size          int
	input         []bool
	output        []bool
	inputVariants [][]bool
}

func (r *rule) toString() string {
	result := ""
	for y := 0; y < r.size; y++ {
		for x := 0; x < r.size; x++ {
			if r.getInput(x, y) {
				result += "#"
			} else {
				result += "."
			}
		}
		result += "\n"
	}
	return result
}

func (r *rule) getInput(x, y int) bool {
	return r.input[(r.size*y)+x]
}
func (r *rule) getOutput(x, y int) bool {
	return r.output[((r.size+1)*y)+x]
}

// Check if the sub-area starting at (px,py) matches any input variant of this rule.
func (r *rule) matches(px, py int, p *picture) bool {
	for _, variant := range r.inputVariants {
		if matchesVariant(variant, r.size, px, py, p) {
			return true
		}
	}
	return false
}

// Check if the sub-area starting at (px,py) matches the given variant.
func matchesVariant(variant []bool, size int, px, py int, p *picture) bool {
	for y := 0; y < size; y++ {
		for x := 0; x < size; x++ {
			if variant[(size*y)+x] != p.get(px+x, py+y) {
				return false
			}
		}
	}
	return true
}

// Calculate all the permutations for rotation and flipping.
func (r *rule) calcVariants() {
	mapFlipHor := func(x, y, size int) (int, int) { return size - (x + 1), y }
	mapFlipVert := func(x, y, size int) (int, int) { return x, size - (y + 1) }
	map180 := func(x, y, size int) (int, int) { return size - (x + 1), size - (y + 1) }
	r.inputVariants = make([][]bool, 0)
	r.inputVariants = append(r.inputVariants, r.input)
	r.inputVariants = append(r.inputVariants, flip(r.input, r.size, mapFlipHor))
	r.inputVariants = append(r.inputVariants, flip(r.input, r.size, mapFlipVert))
	r.inputVariants = append(r.inputVariants, flip(r.input, r.size, map180))

	rotated := rotate(r.input, r.size)
	r.inputVariants = append(r.inputVariants, rotated)
	r.inputVariants = append(r.inputVariants, flip(rotated, r.size, mapFlipHor))
	r.inputVariants = append(r.inputVariants, flip(rotated, r.size, mapFlipVert))
	r.inputVariants = append(r.inputVariants, flip(rotated, r.size, map180))
}

// Rotate 90-degrees clock-wise.
func rotate(input []bool, size int) []bool {
	idx := func(x, y int) int { return (size * y) + x }
	result := make([]bool, len(input))
	for y1, x2 := 0, size-1; y1 < size; y1, x2 = y1+1, x2-1 {
		for x1, y2 := 0, 0; x1 < size; x1, y2 = x1+1, y2+1 {
			result[idx(x2, y2)] = input[idx(size-(x1+1), y1)]
		}
	}
	return result
}

// Flip either horizontally or vertically.
func flip(input []bool, size int, mapCoords func(x, y, size int) (int, int)) []bool {
	idx := func(x, y int) int { return (size * y) + x }
	result := make([]bool, len(input))
	for x := 0; x < size; x++ {
		for y := 0; y < size; y++ {
			result[idx(x, y)] = input[idx(mapCoords(x, y, size))]
		}
	}
	return result
}

// Parse the rule-input.
func newRule(input string) *rule {
	parts := strings.Split(input, " => ")
	if len(parts) != 2 {
		panic("Line doesn't have two parts")
	}

	r := new(rule)
	lhs, rhs := c.SplitTrim(parts[0], '/'), c.SplitTrim(parts[1], '/')
	if len(parts[0]) == 5 {
		r.size = 2
		r.input = make([]bool, 2*2)
		r.output = make([]bool, 3*3)
		if len(lhs) != 2 || len(rhs) != 3 {
			panic("Invalid LHS or RHS split length")
		}
		for i := 0; i < 2*2; i++ {
			r.input[i] = lhs[i/2][i%2] == '#'
		}
		for i := 0; i < 3*3; i++ {
			r.output[i] = rhs[i/3][i%3] == '#'
		}

	} else if len(parts[0]) == 11 {
		r.size = 3
		r.input = make([]bool, 3*3)
		r.output = make([]bool, 4*4)
		if len(lhs) != 3 || len(rhs) != 4 {
			panic("Invalid LHS or RHS split length")
		}
		for i := 0; i < 3*3; i++ {
			r.input[i] = lhs[i/3][i%3] == '#'
		}
		for i := 0; i < 4*4; i++ {
			r.output[i] = rhs[i/4][i%4] == '#'
		}
	} else {
		panic("Invalid left-hand side.")
	}
	r.calcVariants()
	return r
}

type picture struct {
	size int
	data []bool
}

func newPicture(size int) *picture {
	p := new(picture)
	p.size = size
	p.data = make([]bool, size*size)
	return p
}

func (p *picture) get(x, y int) bool {
	return p.data[(p.size*y)+x]
}
func (p *picture) set(x, y int, value bool) {
	p.data[(p.size*y)+x] = value
}

func (p *picture) getNumOn() (sum int) {
	for _, pixel := range p.data {
		if pixel {
			sum++
		}
	}
	return
}

func (p *picture) toString() string {
	result := ""
	for y := 0; y < p.size; y++ {
		for x := 0; x < p.size; x++ {
			if p.get(x, y) {
				result += "#"
			} else {
				result += "."
			}
		}
		result += "\n"
	}
	return result
}
