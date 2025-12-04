# puzzle prompt: https://adventofcode.com/2025/day/4

from ...base import StrSplitSolution, answer
from ...utils.tools import *

dirs = (
    complex(-1, -1),
    complex(-1, 0),
    complex(0, -1),
    complex(-1, 1),
    complex(1, -1),
    complex(1, 0),
    complex(0, 1),
    complex(1, 1),
)


class Solution(StrSplitSolution):
    _year = 2025
    _day = 4

    @answer((1474, 8910))
    def solve(self) -> tuple[int, int]:
        grid = {
            complex(i, j)
            for i, row in enumerate(self.input)
            for j, e in enumerate(row)
            if e == "@"
        }
        part2 = part1 = 0
        this_iter = 1
        to_remove = set()
        while this_iter:
            this_iter = 0
            for idx in grid:
                occupied = 0
                for delta in dirs:
                    occupied += (idx + delta) in grid

                if occupied < 4:
                    to_remove.add(idx)

            this_iter = len(to_remove)
            part2 += this_iter
            grid -= to_remove
            to_remove.clear()
            if part1 == 0:
                part1 = part2

        return part1, part2
