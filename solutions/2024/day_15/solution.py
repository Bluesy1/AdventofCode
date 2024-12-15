# puzzle prompt: https://adventofcode.com/2024/day/15

from ...base import TextSolution, answer
from ...utils.tools import *


def addt(x, y):
    if len(x) == 2:
        return (x[0] + y[0], x[1] + y[1])
    return tuple(map(sum, zip(x, y)))

class Solution(TextSolution):
    _year = 2024
    _day = 15

    @answer(1552463)
    def part_1(self) -> int:

        def do_move(grid: dict[complex, str], pos: complex, delta: complex):
            grid = grid.copy()
            new_pos = pos + delta
            if new_pos in grid:
                if grid[new_pos] == "#":
                    return grid
                if grid[new_pos] == ".":
                    grid[new_pos] = grid[pos]
                    grid[pos] = "."
                    return grid
                if grid[new_pos] == "O":
                    # we can move it, as long as the next spot is empty, or it is empty/movable
                    new_grid = do_move(grid, new_pos, delta)
                    if new_grid[new_pos] == ".":
                        new_grid[new_pos] = grid[pos]
                        new_grid[pos] = "."
                    return new_grid
            return grid

        grid_str, dirs = self.input.split("\n\n")
        grid = {complex(x, y): c for y, row in enumerate(grid_str.splitlines()) for x, c in enumerate(row)}
        dir_mapping: dict[str, complex] = {">": 1, "<": -1, "^": -1j, "v": 1j}
        # print(dir)
        dirs = dirs.replace("\n", "")
        for dr in dirs:
            pos = next(k for k, v in grid.items() if v == "@")
            grid = do_move(grid, pos, dir_mapping[dr])

        ans = 0
        for pos, c in grid.items():
            if c == "O":
                x, y = pos.real, pos.imag
                self.debug(f"found O at {x}, {y}: {int(100 * x + y)}")
                ans += int(100 * y + x)
        
        return ans

    @answer(1554058)
    def part_2(self) -> int:

        a=self.input.strip().split('\n\n')
        ll = list(a[0].split("\n"))
        moves = a[1]

        DIRS = [(0, 1), (0, -1), (1, 0), (-1, 0)]
        D = [">", "<", "v", "^"]

        moves = [DIRS[D.index(m)] for m in moves.replace("\n", "")]
        # print(moves)

        def left(pos):
            return (pos[0], pos[1]-1)
        def right(pos):
            return (pos[0], pos[1]+1)

        walls = set()
        boxes = set()
        for i, l in enumerate(ll):
            for j, ch in enumerate(l):
                j *= 2
                if ch == "#":
                    walls.add((i,j))
                    walls.add((i,j+1))
                if ch == "O":
                    boxes.add((i, j))
                if ch == "@":
                    robot = (i, j)

        def push(box, d):
            assert box in boxes
            nxt = addt(box, d)
            if nxt in walls or right(nxt) in walls:
                return None
            if d[0]:
                # we are moving up/down
                if nxt in boxes:
                    r = push(nxt, d)
                    if r is None:
                        return None
                if left(nxt) in boxes:
                    r = push(left(nxt), d)
                    if r is None:
                        return None
                if right(nxt) in boxes:
                    r = push(right(nxt), d)
                    if r is None:
                        return None
            if d[1] == 1 and right(nxt) in boxes:
                r = push(right(nxt), d)
                if r is None:
                    return None
            if d[1] == -1 and left(nxt) in boxes:
                r = push(left(nxt), d)
                if r is None:
                    return None
            boxes.remove(box)
            boxes.add(nxt)
            return True

        for move in moves:
            nxt = addt(robot, move)
            if nxt in walls:
                continue

            if nxt in boxes:
                #print(move)
                copy = set(boxes)
                r = push(nxt, move)
                if r is None:
                    boxes = copy
                    continue
            elif left(nxt) in boxes:
                #print(move)
                copy = set(boxes)
                r = push(left(nxt), move)
                if r is None:
                    boxes = copy
                    continue
            robot = nxt

        c = 0
        for box in boxes:
            c += 100 * box[0] + box[1]
        print(c)
        return c
