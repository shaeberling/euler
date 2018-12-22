package p15

import (
	c "common"
	"fmt"
	"math"
	"sort"
)

// --- Day 15: Beverage Bandits ---
// http://adventofcode.com/2018/day/15
func Solve(input string) (string, string) {
	lines := c.SplitByNewline(input)
	return "", solveB(lines)
}

func solveA(lines []string) string {
	score, _ := runFight(lines, 0)
	return c.ToString(score)
}

func solveB(lines []string) string {
	// Binary search for the right elve boost
	upper := 1000
	lower := 0
	lastScore := 0
	for {
		toTry := ((upper - lower) / 2) + lower
		fmt.Printf("Trying: (%d-%d) %d\n", lower, upper, toTry)
		score, elvesWin := runFight(lines, toTry)
		if elvesWin {
			lastScore = score
			upper = toTry
		} else {
			lower = toTry
		}

		if upper-lower == 1 {
			if !elvesWin {
				score, elvesWin := runFight(lines, toTry+1)
				lastScore = score
				if !elvesWin {
					panic("Binary search failed.")
				}
			}
			break
		}
	}
	return c.ToString(lastScore)
}

func runFight(lines []string, elvePower int) (score int, elvesWin bool) {
	w := parse(lines)

	// Give the elves a boost.
	numElves := 0
	for i := range w.characters {
		if w.characters[i].t == 0 {
			w.characters[i].power = elvePower
			numElves++
		}
	}

	round := 0
	for ; !w.fightEnded(); round++ {
		for i := range w.characters {
			if w.fightEnded() {
				fmt.Println("Fight ended early")
				round--
				break
			}
			w.characters[i].moveOneStep(w)
			// character needs to decide whether to attack or now
			w.characters[i].maybeAttack(w)
		}
		sort.Sort(w.characters)
		if w.countType(0) == 0 || w.countType(1) == 0 {
			break
		}
	}
	w.print(round + 1)

	result := 0
	for _, ch := range w.characters {
		if ch.health > 0 {
			result += ch.health
		}
	}

	fmt.Printf("Elves: Beg: %d, End: %d\n", numElves, w.countType(0))
	return (round + 1) * result, w.countType(0) == numElves
}

func parse(lines []string) *world {
	grid := make([]bool, len(lines)*len(lines[0]))
	characters := make(chars, 0)

	width, height := len(lines[0]), len(lines)
	for y, l := range lines {
		for x, c := range l {
			pos := y*width + x
			grid[pos] = c == '#'
			switch c {
			case 'E':
				characters = append(characters, newCharacter(0, x, y))
			case 'G':
				characters = append(characters, newCharacter(1, x, y))
			}
		}
	}
	sort.Sort(characters)
	return &world{width: width, height: height, grid: grid, characters: characters}
}

// ====== WORLD ======
type world struct {
	width, height int
	grid          []bool
	characters    chars
}

func (w world) fightEnded() bool {
	return w.countType(0) == 0 || w.countType(1) == 0
}

func (w world) print(round int) {
	fmt.Printf("\n===== Round %d ====\n", round)
	for y := 0; y < w.height; y++ {
		for x := 0; x < w.width; x++ {
			c := w.charAt(x, y)
			if c != nil {
				fmt.Print(c.getBadge())
			} else {
				if w.grid[y*w.width+x] {
					fmt.Print("#")
				} else {
					fmt.Print(".")
				}
			}
		}
		fmt.Print("\n")
	}
	for _, ch := range w.characters {
		if ch.health > 0 {
			fmt.Printf("%s(%d), ", ch.getBadge(), ch.health)
		}
	}
	fmt.Println("\n=====================")
}

func (w world) wallAt(x, y int) bool {
	return w.grid[y*w.width+x]
}
func (w world) hasCharAt(x, y int) bool {
	return w.charAt(x, y) != nil
}

func (w world) charAt(x, y int) *character {
	for i := range w.characters {
		c := &w.characters[i]
		if c.health > 0 && c.x == x && c.y == y {
			return c
		}
	}
	return nil
}

func (w world) hasCharAtT(x, y, t int) bool {
	return w.charAtT(x, y, t) != nil
}

func (w world) charAtT(x, y, t int) *character {
	for i := range w.characters {
		c := &w.characters[i]
		if c.health > 0 && c.x == x && c.y == y && c.t == t {
			return c
		}
	}
	return nil
}

func (w world) countType(t int) int {
	num := 0
	for _, ch := range w.characters {
		if ch.health > 0 && ch.t == t {
			num++
		}
	}
	return num
}

func (w world) isValidSquare(x, y int) bool {
	if x < 0 || y < 0 || x >= w.width || y >= w.height {
		return false
	}
	if w.wallAt(x, y) || w.hasCharAt(x, y) {
		return false
	}
	return true
}

// ====== CHARACTER ======
func newCharacter(t, x, y int) character {
	return character{
		t:      t,
		x:      x,
		y:      y,
		power:  3,
		health: 200,
	}
}

type character struct {
	// 0 = Elf, 1=Goblin
	t      int
	x, y   int
	power  int
	health int
}

type searchNode struct {
	pos    xy
	target xy
	prev   *searchNode
	next   []*searchNode
}

func (ch *character) getBadge() string {
	if ch.t == 0 {
		return "E"
	} else if ch.t == 1 {
		return "G"
	}
	return "X"
}

// Will only attack, if it can.
func (ch *character) maybeAttack(w *world) {
	// No zombies here!.
	if ch.health <= 0 {
		return
	}

	// Select enemy with the lowest health, or if tied, first reading order applies.
	var attacked *character

	enemyT := (ch.t + 1) % 2
	if enemy := w.charAtT(ch.x, ch.y-1, enemyT); enemy != nil {
		if attacked == nil || enemy.health < attacked.health {
			attacked = enemy
		}
	}
	if enemy := w.charAtT(ch.x-1, ch.y, enemyT); enemy != nil {
		if attacked == nil || enemy.health < attacked.health {
			attacked = enemy
		}
	}
	if enemy := w.charAtT(ch.x+1, ch.y, enemyT); enemy != nil {
		if attacked == nil || enemy.health < attacked.health {
			attacked = enemy
		}
	}
	if enemy := w.charAtT(ch.x, ch.y+1, enemyT); enemy != nil {
		if attacked == nil || enemy.health < attacked.health {
			attacked = enemy
		}
	}
	if attacked == nil {
		return
	}
	attacked.health -= ch.power
}

// Will only iterate, if it can.
func (ch *character) moveOneStep(w *world) {
	targets := ch.targets(w.characters)
	targetSqs := make(xys, 0)
	for _, t := range targets {
		targetSqs = append(targetSqs, t.squares(w)...)
	}

	otherT := (ch.t + 1) % 2
	// Check whether we're already next to an enemy. If so do not move.
	if w.hasCharAtT(ch.x, ch.y-1, otherT) || w.hasCharAtT(ch.x-1, ch.y, otherT) ||
		w.hasCharAtT(ch.x+1, ch.y, otherT) || w.hasCharAtT(ch.x, ch.y+1, otherT) {
		return
	}

	shortestPath, shortestNodes := math.MaxInt32, make([]searchNode, 0)
	for _, s := range targetSqs {
		// fmt.Printf("\n[%d,%d] Sourcing to %v\n", ch.x, ch.y, s)
		nodes := ch.findShPath(s.x, s.y, w)
		if nodes == nil || len(nodes) == 0 {
			continue
		}
		// fmt.Printf("\n[%d,%d] Found %d shortest paths of length %d.\n", ch.x, ch.y, len(nodes), nodes[0].length())
		// First sanity check that all nodes have the same length
		pathLength := nodes[0].length()
		for i := 1; i < len(nodes); i++ {
			if nodes[i].length() != pathLength {
				panic("Nodes do not have the same length!")
			}
		}

		if pathLength == shortestPath {
			// Note, there can be dups, but all paths should be in the right order.
			shortestNodes = append(shortestNodes, nodes...)
		}
		if pathLength < shortestPath {
			shortestPath = pathLength
			shortestNodes = nodes
		}
	}
	// fmt.Printf("[%d,%d] Shortest path is %d with %d nodes.\n", ch.x, ch.y, shortestPath, len(shortestNodes))

	// for _, n := range shortestNodes {
	// 	n.printPath()
	// }

	// Move! (These should be in the right order)
	if len(shortestNodes) > 0 {
		moveTo := shortestNodes[0].root()
		ch.x = moveTo.pos.x
		ch.y = moveTo.pos.y
	}
}

func (ch *character) findShPath(x, y int, w *world) []searchNode {
	startingPoints := ch.squares(w)
	searchPaths := make([]searchNode, len(startingPoints))
	for i, point := range startingPoints {
		searchPaths[i] = searchNode{pos: xy{x: point.x, y: point.y}, target: xy{x: x, y: y}}
	}

	visited := make(map[xy]bool)
	addedLeafs := true
	for addedLeafs {
		addedLeafs = false
		for i := range searchPaths {
			nodes, added := searchPaths[i].advance(w, visited)
			if nodes != nil && len(nodes) > 0 {
				return nodes
			}
			if added {
				addedLeafs = true
			}
		}
	}
	return nil
}

func (n *searchNode) length() int {
	if n.prev == nil {
		return 1
	}
	return n.prev.length() + 1
}
func (n *searchNode) printPath() {
	if n.prev == nil {
		fmt.Printf("[%v]->", n.pos)
		return
	}
	n.prev.printPath()
	fmt.Printf("[%v]->", n.pos)

	if n.next == nil {
		fmt.Println("||")
	}
}
func (n *searchNode) root() *searchNode {
	if n.prev == nil {
		return n
	}
	return n.prev.root()
}

// Returns whether it continued.
func (n *searchNode) advance(w *world, visited map[xy]bool) ([]searchNode, bool) {
	// fmt.Printf("[%v] Advancing\n", n.pos)

	// It's the same!
	if n.pos == n.target {
		result := make([]searchNode, 1)
		result[0] = *n
		return result, false
	}

	if n.next != nil {
		if len(n.next) == 0 {
			return nil, false
		}
		foundTargets := make([]searchNode, 0)
		addedLeafs := false
		for i := range n.next {
			endNodes, added := n.next[i].advance(w, visited)
			if foundTargets != nil {
				foundTargets = append(foundTargets, endNodes...)
			}
			if added {
				addedLeafs = true
			}
		}
		// Didn't find it going down any of the leafs.
		return foundTargets, addedLeafs
	}

	// If we got here, we are at a leaf and will add new leafes to it.
	if n.next != nil {
		panic("Should only advance leafs.")
	}

	if visited[n.pos] {
		// Another strand was here already. Quit.
		return nil, false
	}
	// We're here now!
	visited[n.pos] = true

	next := emptySqs(n.pos.x, n.pos.y, w)
	n.next = make([]*searchNode, len(next))
	for i, ne := range next {
		start := xy{x: ne.x, y: ne.y}
		n.next[i] = &searchNode{pos: start, target: n.target, prev: n}
	}
	return nil, true
}

func (ch character) targets(allChars chars) chars {
	result := make(chars, 0)
	for _, c := range allChars {
		if !(c.x == ch.x && c.y == ch.y) && c.t != ch.t {
			if c.health > 0 {
				result = append(result, c)
			}
		}
	}
	return result
}

func (ch character) squares(w *world) xys {
	return emptySqs(ch.x, ch.y, w)
}
func emptySqs(x, y int, w *world) xys {
	// These are created in the game-order.
	result := make(xys, 0)
	if w.isValidSquare(x, y-1) {
		result = append(result, xy{x: x, y: y - 1})
	}
	if w.isValidSquare(x-1, y) {
		result = append(result, xy{x: x - 1, y: y})
	}
	if w.isValidSquare(x+1, y) {
		result = append(result, xy{x: x + 1, y: y})
	}
	if w.isValidSquare(x, y+1) {
		result = append(result, xy{x: x, y: y + 1})
	}
	return result
}

type chars []character

// Sort interface for characters, since an exact order needs to be followed.
func (cs chars) Len() int      { return len(cs) }
func (cs chars) Swap(i, j int) { cs[i], cs[j] = cs[j], cs[i] }
func (cs chars) Less(i, j int) bool {
	if cs[i].y == cs[j].y {
		return cs[i].x < cs[j].x
	}
	return cs[i].y < cs[j].y
}

// ====== XY ======
type xy struct {
	x, y int
}
type xys []xy

// Sort interface for characters, since an exact order needs to be followed.
func (o xys) Len() int      { return len(o) }
func (o xys) Swap(i, j int) { o[i], o[j] = o[j], o[i] }
func (o xys) Less(i, j int) bool {
	if o[i].y == o[j].y {
		return o[i].x < o[j].x
	}
	return o[i].y < o[j].y
}
