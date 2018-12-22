package aocvm

// Operation is the common interface for all operations.
type Operation func(op []int, reg []int)

// GetOperations returns all known operations, keyed by their name.
func GetOperations() map[string]Operation {
	ops := make(map[string]Operation, 0)
	ops["addr"] = iAddr
	ops["addi"] = iAddi
	ops["mulr"] = iMulr
	ops["muli"] = iMuli
	ops["banr"] = iBanr
	ops["bani"] = iBani
	ops["borr"] = iBorr
	ops["bori"] = iBori
	ops["setr"] = iSetr
	ops["seti"] = iSeti
	ops["gtir"] = iGtir
	ops["gtri"] = iGtri
	ops["gtrr"] = iGtrr
	ops["eqir"] = iEqir
	ops["eqri"] = iEqri
	ops["eqrr"] = iEqrr
	return ops
}

func iAddr(op []int, reg []int) { reg[op[2]] = reg[op[0]] + reg[op[1]] }
func iAddi(op []int, reg []int) { reg[op[2]] = reg[op[0]] + op[1] }
func iMulr(op []int, reg []int) { reg[op[2]] = reg[op[0]] * reg[op[1]] }
func iMuli(op []int, reg []int) { reg[op[2]] = reg[op[0]] * op[1] }
func iBanr(op []int, reg []int) { reg[op[2]] = reg[op[0]] & reg[op[1]] }
func iBani(op []int, reg []int) { reg[op[2]] = reg[op[0]] & op[1] }
func iBorr(op []int, reg []int) { reg[op[2]] = reg[op[0]] | reg[op[1]] }
func iBori(op []int, reg []int) { reg[op[2]] = reg[op[0]] | op[1] }
func iSetr(op []int, reg []int) { reg[op[2]] = reg[op[0]] }
func iSeti(op []int, reg []int) { reg[op[2]] = op[0] }
func iGtir(op []int, reg []int) { reg[op[2]] = bin(op[0] > reg[op[1]]) }
func iGtri(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] > op[1]) }
func iGtrr(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] > reg[op[1]]) }
func iEqir(op []int, reg []int) { reg[op[2]] = bin(op[0] == reg[op[1]]) }
func iEqri(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] == op[1]) }
func iEqrr(op []int, reg []int) { reg[op[2]] = bin(reg[op[0]] == reg[op[1]]) }

func bin(a bool) int {
	if a {
		return 1
	} else {
		return 0
	}
}
