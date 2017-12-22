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
}

// --- Day 20: Particle Swarm ---
// http://adventofcode.com/2017/day/20
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	particles := make([]particle, len(lines))
	for i, line := range lines {
		particles[i] = parse(strings.Split(line, ", "))
	}
	return c.ToString(solveA(particles)), c.ToString(solveB(particles))
}

func solveA(particles []particle) int {
	minAccellIdx, minAccel := -1, math.MaxInt64
	for i, p := range particles {
		accel := c.Abs(p.acc[0]) + c.Abs(p.acc[1]) + c.Abs(p.acc[2])
		if accel < minAccel {
			minAccel, minAccellIdx = accel, i
		}
	}
	return minAccellIdx
}

func solveB(particles []particle) int {
	destructions := make(byTime, 0)
	for i := 0; i < len(particles)-1; i++ {
		pi := particles[i]
		for j := i + 1; j < len(particles); j++ {
			hits := make([]map[int]bool, 3)
			matchesAll := make([]bool, 3)
			markIfValid := func(d int, x float64) {
				if x > 0 && math.Floor(x) == x {
					hits[d][int(x)] = true
				}
			}
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
						markIfValid(d, -cf/bf)
					}
				} else {
					x1, x2 := quadratic(af, bf, cf)
					markIfValid(d, x1)
					markIfValid(d, x2)
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
			destroyedWhen[v.pA], destroyedWhen[v.pB] = v.time, v.time
		}
	}
	// Count the survivors which haven't been destroyed.
	return c.CountMappedIntsIf(destroyedWhen, math.MaxInt64)
}

// Solving quadratic equation with a*x^2 + b*x + c = 0
func quadratic(a, b, c float64) (float64, float64) {
	discriminant := b*b - 4*(a*c)
	if discriminant < 0 {
		// In this case there is no solution. Return negative numbers so we ignore them.
		return -42, -42
	}
	return (-b - math.Sqrt(discriminant)) / (a + a), (-b + math.Sqrt(discriminant)) / (a + a)
}

func parse(parts [] string) (result particle) {
	set := func(arr *[3]int, i int) {
		array := c.ParseIntArray(strings.Split(parts[i][3:len(parts[i])-1], ","))
		arr[0], arr[1], arr[2] = array[0], array[1], array[2]
	}
	set(&result.pos, 0)
	set(&result.vel, 1)
	set(&result.acc, 2)
	return
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
