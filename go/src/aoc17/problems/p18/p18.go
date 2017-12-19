package p18

import (
	c "common"
	"strconv"
)

var queue []*simpleQueue

// --- Day 18: Duet ---
// http://adventofcode.com/2017/day/18
func Solve(input string) (string, string) {
	return c.ToString(solveA(c.SplitByNewline(input))), c.ToString(solveB(c.SplitByNewline(input)))
}

func solveB(insts []string) int {
	queue = make([]*simpleQueue, 2)
	queue[0], queue[1] = new(simpleQueue), new(simpleQueue)
	vmA, vmB := newVm(0), newVm(1)
	for {
		stoppedA, _ := vmA.step(insts[vmA.i], false)
		stoppedB, _ := vmB.step(insts[vmB.i], false)
		if stoppedA && stoppedB {
			// Size of queue '0' tells us how much '1' sent.
			return len(queue[0].content)
		}
	}
}

func solveA(insts []string) int {
	queue = make([]*simpleQueue, 2)
	queue[0] = new(simpleQueue)
	queue[1] = new(simpleQueue)
	vm := newVm(0)
	for {
		_, received := vm.step(insts[vm.i], true)
		if received > 0 {
			return received
		}
	}
}

// A simple FIFO queue that never frees memory.
type simpleQueue struct {
	i       int
	content []int
}

func (q *simpleQueue) put(v int) {
	q.content = append(q.content, v)
}
func (q *simpleQueue) popFront() int {
	result := q.content[q.i]
	q.i++
	return result
}
func (q *simpleQueue) hasItems() bool {
	return q.i < len(q.content)
}

// A virtual machine with its own program counter and registers.
type vm struct {
	i    int
	p    int
	sreg int
	regs map[string]int
}

func newVm(p int) *vm {
	v := new(vm)
	v.regs = make(map[string]int, 1)
	v.p = p
	v.regs["p"] = p
	v.p = p
	return v
}

func (v *vm) val(x string) int {
	n, err := strconv.ParseInt(x, 10, 64)
	if err != nil {
		return v.regs[x]
	}
	return int(n)
}

func (v *vm) step(instr string, isA bool) (blocked bool, received int) {
	//v.printRegisters(instr)
	parts := c.SplitByWhitespaceTrim(instr)
	x := parts[1]

	switch parts[0] {
	case "snd":
		other := (v.p + 1) % 2
		queue[other].put(v.val(x))
		// For solution 'A'.
		v.sreg = v.val(x)
	case "set":
		v.regs[x] = v.val(parts[2])
	case "add":
		v.regs[x] += v.val(parts[2])
	case "mul":
		v.regs[x] *= v.val(parts[2])
	case "mod":
		v.regs[x] %= v.val(parts[2])
	case "rcv":
		if isA {
			if v.regs[x] != 0 {
				received = v.sreg
			}
		} else {
			if !queue[v.p].hasItems() {
				return true, 0
			}
			v.regs[x] = queue[v.p].popFront()
		}
	case "jgz":
		if v.val(x) > 0 {
			v.i += v.val(parts[2]) - 1
		}
	}
	v.i++
	return false, received
}
