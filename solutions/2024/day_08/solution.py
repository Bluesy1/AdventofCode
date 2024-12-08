# puzzle prompt: https://adventofcode.com/2024/day/8

from ...base import StrSplitSolution, answer
from ...utils.tools import *


def manhattan_distance(a: tuple[int, int], b: tuple[int, int]) -> int:
    return abs(a[0] - b[0]) + abs(a[1] - b[1])

class Solution(StrSplitSolution):
    _year = 2024
    _day = 8

    @answer((256, 1005))
    def solve(self) -> tuple[int, int]:
        grid = {(i,j): v for j, row in enumerate(self.input) for i, v in enumerate(row)}
        vals = set(grid.values()) - {'.'}
        p1_nodes = set()
        p2_nodes = set()
        for v in vals:
            coords = {k for k, val in grid.items() if val == v}
            for c1, c2 in itertools.permutations(coords, 2):
                di = abs(c1[0] - c2[0])
                dj = abs(c1[1] - c2[1])

                if c1[0] <= c2[0] and c1[1] <= c2[1]:
                    antinode = (c1[0] - di, c1[1] - dj)
                    if antinode in grid:
                        p1_nodes.add(antinode)
                    antinode = (c2[0] + di, c2[1] + dj)
                    if antinode in grid:
                        p1_nodes.add(antinode)
                    i1, j1 = c1
                    while (i1, j1) in grid:
                        p2_nodes.add((i1, j1))
                        i1 -= di
                        j1 -= dj
                    i2, j2 = c2
                    while (i2, j2) in grid:
                        p2_nodes.add((i2, j2))
                        i2 += di
                        j2 += dj
                elif c1[0] < c2[0] and c1[1] > c2[1]:
                    antinode = (c1[0] - di, c1[1] + dj)
                    if antinode in grid:
                        p1_nodes.add(antinode)
                    antinode = (c2[0] + di, c2[1] - dj)
                    if antinode in grid:
                        p1_nodes.add(antinode)
                    i1, j1 = c1
                    while (i1, j1) in grid:
                        p2_nodes.add((i1, j1))
                        i1 -= di
                        j1 += dj
                    i2, j2 = c2
                    while (i2, j2) in grid:
                        p2_nodes.add((i2, j2))
                        i2 += di
                        j2 -= dj

        return len(p1_nodes), len(p2_nodes)
