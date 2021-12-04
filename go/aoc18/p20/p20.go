package p20

import (
	"fmt"
	"image"
	"image/color"
	"image/draw"
	"image/png"
	"math"
	"os"

	c "s13g.com/euler/common"
)

// --- Day 20: A Regular Map ---
// http://adventofcode.com/2018/day/20
func Solve(input string) (string, string) {
	// Remove the 'regex' characters.
	input = input[1 : len(input)-1]
	// All rooms will be added here. Init with 0,0 starting room.
	rooms := make(map[xy]*room, 0)
	rooms[xy{x: 0, y: 0}] = &room{}

	// The history we came to the current point through tracing.
	history := make([]xy, 1)
	history[0] = xy{x: 0, y: 0}

	// Trace all the rooms recursively.
	trace(history, input, rooms)
	saveAsPng(rooms)

	// Now let's find the longest shortest paths through breadth-first.
	paths := make(map[xy]int)
	nextNodes := make([]xy, 1)
	nextNodes[0] = xy{x: 0, y: 0}
	for i := 0; len(nextNodes) > 0; i++ {
		for _, node := range nextNodes {
			if l, ok := paths[node]; ok {
				paths[node] = c.Min(l, i)
			} else {
				paths[node] = i
			}
		}
		// Remove all nodes we've already been to.
		nextNodes = filter(next(nextNodes, rooms), paths)
	}

	// Get the results for both parts.
	longest, count1k := math.MinInt32, 0
	for _, length := range paths {
		longest = c.Max(longest, length)
		if length >= 1000 {
			count1k++
		}
	}
	return c.ToString(longest), c.ToString(count1k)
}

func filter(candidates []xy, paths map[xy]int) []xy {
	nextNodes := make([]xy, 0)
	for _, c := range candidates {
		if _, ok := paths[c]; !ok {
			nextNodes = append(nextNodes, c)
		}
	}
	return nextNodes
}

// Return a list of all rooms that can be reached from the list of given room locations.
func next(nodes []xy, rooms map[xy]*room) []xy {
	next := make([]xy, 0)
	for _, node := range nodes {
		r := rooms[node]
		if r.up != nil {
			next = append(next, xy{x: node.x, y: node.y - 1})
		}
		if r.right != nil {
			next = append(next, xy{x: node.x + 1, y: node.y})
		}
		if r.down != nil {
			next = append(next, xy{x: node.x, y: node.y + 1})
		}
		if r.left != nil {
			next = append(next, xy{x: node.x - 1, y: node.y})
		}
	}
	return next
}

// Trace all rooms recursively given the instructions.
func trace(hist []xy, instructions string, rooms map[xy]*room) int {
	pos := hist[len(hist)-1]
	firstParenClose := math.MaxInt32
	for i := 0; i < len(instructions); i++ {
		currentRoom := rooms[xy{x: pos.x, y: pos.y}]
		instr := instructions[i]
		switch instr {
		case '(':
			// Start a new sub-trace from here and end this trace.
			newHist := append(hist, pos)
			trace(newHist, instructions[i+1:], rooms)
			return math.MaxInt32
		case ')':
			if firstParenClose < 0 {
				firstParenClose = i
			}
			// Pop history and continue.
			hist = hist[:len(hist)-1]
		case '|':
			i = i + trace(hist, instructions[i+1:], rooms)
		case 'N':
			pos.y--
			room := ensureRoomAt(pos, rooms)
			currentRoom.up = room
			room.down = currentRoom
		case 'E':
			pos.x++
			room := ensureRoomAt(pos, rooms)
			currentRoom.right = room
			room.left = currentRoom
		case 'S':
			pos.y++
			room := ensureRoomAt(pos, rooms)
			currentRoom.down = room
			room.up = currentRoom
		case 'W':
			pos.x--
			room := ensureRoomAt(pos, rooms)
			currentRoom.left = room
			room.right = currentRoom
		}
	}
	return firstParenClose
}

func ensureRoomAt(pos xy, rooms map[xy]*room) *room {
	if _, ok := rooms[pos]; ok {
		return rooms[pos]
	}
	newRoom := &room{}
	rooms[pos] = newRoom
	return newRoom
}

func saveAsPng(rooms map[xy]*room) error {
	minX, maxX, minY, maxY := math.MaxInt32, math.MinInt32, math.MaxInt32, math.MinInt32
	for k := range rooms {
		minX = c.Min(minX, k.x)
		maxX = c.Max(maxX, k.x)
		minY = c.Min(minY, k.y)
		maxY = c.Max(maxY, k.y)
	}
	width := maxX - minX + 1
	height := maxY - minY + 1

	img := image.NewRGBA(image.Rect(0, 0, width*3, height*3))
	draw.Draw(img, img.Bounds(), &image.Uniform{color.White}, image.ZP, draw.Src)
	wallColor := color.RGBA{0, 0, 0, 255}      // black
	roomColor := color.RGBA{0, 0, 200, 255}    // dark blue
	centralColor := color.RGBA{255, 0, 0, 255} // red
	doorColor := color.RGBA{0, 255, 0, 255}    // green

	for k, room := range rooms {
		x, y := (k.x-minX)*3+1, (k.y-minY)*3+1
		// Center
		if k.x == 0 && k.y == 0 {
			img.Set(x, y, centralColor)
		} else {
			img.Set(x, y, roomColor)
		}
		// Corners
		img.Set(x+1, y+1, wallColor)
		img.Set(x+1, y-1, wallColor)
		img.Set(x-1, y+1, wallColor)
		img.Set(x-1, y-1, wallColor)
		// Doors
		if room.up != nil {
			img.Set(x, y-1, doorColor)
		} else {
			img.Set(x, y-1, wallColor)
		}
		if room.right != nil {
			img.Set(x+1, y, doorColor)
		} else {
			img.Set(x+1, y, wallColor)
		}
		if room.down != nil {
			img.Set(x, y+1, doorColor)
		} else {
			img.Set(x, y+1, wallColor)
		}
		if room.left != nil {
			img.Set(x-1, y, doorColor)
		} else {
			img.Set(x-1, y, wallColor)
		}
	}

	// Encode as PNG.
	f, err := os.Create("src/aoc18/problems/p20/visualization.png")
	if err != nil {
		return fmt.Errorf("cannot create file %s", err)
	}
	if err := png.Encode(f, img); err != nil {
		return fmt.Errorf("cannot encode PNG %s", err)
	}
	return nil
}

type xy struct{ x, y int }
type room struct{ up, right, down, left *room }
