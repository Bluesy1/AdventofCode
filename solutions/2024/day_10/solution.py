# puzzle prompt: https://adventofcode.com/2024/day/10

from ...base import StrSplitSolution, answer
from ...utils.tools import *


def reachable(grid, start):
    current = grid[start]
    for delta in (-1, 1, -1j, 1j):
        new = start + delta
        if new in grid:
            val = grid[new]
            if val - current == 1:
                if val == 9:
                    yield new
                else:
                    yield from reachable(grid, new)

class Solution(StrSplitSolution):
    _year = 2024
    _day = 10

    @answer((482, 1094))
    def solve(self) -> tuple[int, int]:
        p1 = p2 = 0
        grid = {complex(x, y): int(c) for y, row in enumerate(self.input) for x, c in enumerate(row)}
        # find all 0s
        for point in (k for k, v in grid.items() if v == 0):
            routes = list(reachable(grid, point))
            p1 += len(set(routes))
            p2 += len(routes)
        return p1, p2
