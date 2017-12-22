package p20

import (
	c "common"
	"strings"
	"math"
	"sort"
)

type particle struct {
	pos [3]int
	vel [3]int
	acc [3]int
	off bool
}

func (p *particle) distance() int {
	return c.Abs(p.pos[0]) + c.Abs(p.pos[1]) + c.Abs(p.pos[2])
}
func (p *particle) accel() int {
	return c.Abs(p.acc[0]) + c.Abs(p.acc[1]) + c.Abs(p.acc[2])
}

func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	particles := make([]particle, len(lines))
	for i, line := range lines {
		particles[i] = parse(line)
	}
	return c.ToString(solveA(particles)), c.ToString(solveB(particles))
}

func solveA(particles []particle) int {
	minAccellIdx := -1
	minAccel := math.MaxInt64
	for i, p := range particles {
		if p.accel() < minAccel {
			minAccel = p.accel()
			minAccellIdx = i
		}
	}
	return minAccellIdx
}

type destruction struct {
	time int
	pA   int
	pB   int
}

type byTime []destruction

func (a byTime) Len() int           { return len(a) }
func (a byTime) Swap(i, j int)      { a[i], a[j] = a[j], a[i] }
func (a byTime) Less(i, j int) bool { return a[i].time < a[j].time }

func solveB(particles []particle) int {
	destructions := make(byTime, 0)
	for i := 0; i < len(particles)-1; i++ {
		pi := particles[i]
		for j := i; j < len(particles); j++ {
			if i == j {
				continue
			}
			hits := make([]map[int]bool, 3)
			matchesAll := make([]bool, 3)
			// 3D hit means all three axes hit at the same x point. So loop through all 3 axes.
			for d := 0; d < 3; d++ {
				hits[d] = make(map[int]bool)
				a := pi.acc[d] - particles[j].acc[d]
				b := pi.vel[d] - particles[j].vel[d]
				c := pi.pos[d] - particles[j].pos[d]

				// See https://www.reddit.com/r/adventofcode/comments/7l42yy/day_20_with_calculus/
				af := float64(a) / 2
				bf := float64(b) + af
				cf := float64(c)

				// If 'a' is zero, this ends up to be a linear equation.
				if af == 0 {
					// if a, b, and c are zero, the two lines are identical.
					if bf == 0 && cf == 0 {
						matchesAll[d] = true
					} else {
						x1 := -cf / bf
						if x1 > 0 && math.Floor(x1) == x1 {
							hits[d][int(x1)] = true
						}
					}
				} else {
					x1, x2 := quadratic(af, bf, cf)
					if x1 > 0 && math.Floor(x1) == x1 {
						hits[d][int(x1)] = true
					}
					if x2 > 0 && math.Floor(x2) == x2 {
						hits[d][int(x2)] = true
					}
				}
			}
			// Check if there are intersection points in all three dimensions.
			for k, v := range hits[0] {
				if v && (hits[1][k] || matchesAll[1]) && (hits[2][k] || matchesAll[2]) {
					destructions = append(destructions, destruction{k, i, j})
				}
			}
		}
	}
	// Sort by time since we need to account for particles already destroyed.
	sort.Sort(destructions)

	// Build a map of when a particle is destroyed first. Init with maxInt.
	destroyedWhen := make(map[int]int)
	for i := 0; i < len(particles); i++ {
		destroyedWhen[i] = math.MaxInt64
	}

	// Go through all destructions in time-order.
	for _, v := range destructions {
		// Only if both particles are still alive can they destroy each other.
		if destroyedWhen[v.pA] >= v.time && destroyedWhen[v.pB] >= v.time {
			destroyedWhen[v.pA] = v.time
			destroyedWhen[v.pB] = v.time
		}
	}
	// Count the survivors which haven't been destroyed.
	survivors := 0
	for _, v := range destroyedWhen {
		if v == math.MaxInt64 {
			survivors++
		}
	}
	return survivors
}

// Solving quadratic equation with a*x^2 + b*x + c = 0
func quadratic(a, b, c float64) (float64, float64) {
	discriminant := b*b - 4*(a*c)
	if discriminant < 0 {
		// In this case there is no solution. Return negative numbers so we ignore them.
		return -42, -42
	}
	x1 := (-b - math.Sqrt(discriminant)) / (a + a)
	x2 := (-b + math.Sqrt(discriminant)) / (a + a)
	return x1, x2
}

func parse(line string) particle {
	parts := strings.Split(line, ", ")
	var result particle
	pos := c.ParseIntArray(strings.Split(parts[0][3:len(parts[0])-1], ","))
	result.pos[0], result.pos[1], result.pos[2] = pos[0], pos[1], pos[2]

	vel := c.ParseIntArray(strings.Split(parts[1][3:len(parts[1])-1], ","))
	result.vel[0], result.vel[1], result.vel[2] = vel[0], vel[1], vel[2]

	acc := c.ParseIntArray(strings.Split(parts[2][3:len(parts[2])-1], ","))
	result.acc[0], result.acc[1], result.acc[2] = acc[0], acc[1], acc[2]
	return result
}
