# puzzle prompt: https://adventofcode.com/2024/day/8

from ...base import StrSplitSolution, answer
from ...utils.tools import *


class Solution(StrSplitSolution):
    _year = 2024
    _day = 8

    @answer((256, 1005))
    def solve(self) -> tuple[int, int]:
        grid = {complex(i,j): v for j, row in enumerate(self.input) for i, v in enumerate(row)}
        vals = set(grid.values()) - {'.'}
        p1_nodes = set()
        p2_nodes = set()
        for v in vals:
            coords = {k for k, val in grid.items() if val == v}
            for c1, c2 in itertools.permutations(coords, 2):
                delta = c2 - c1

                if c1 - delta in grid:
                    p1_nodes.add(c1 - delta)
                if c2 + delta in grid:
                    p1_nodes.add(c2 + delta)
                
                antinode = c1
                while antinode in grid:
                    p2_nodes.add(antinode)
                    antinode -= delta
                antinode = c2
                while antinode in grid:
                    p2_nodes.add(antinode)
                    antinode += delta

        return len(p1_nodes), len(p2_nodes)
